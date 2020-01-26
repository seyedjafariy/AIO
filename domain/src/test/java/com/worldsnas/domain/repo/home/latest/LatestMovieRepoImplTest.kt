package com.worldsnas.domain.repo.home.latest

import arrow.core.Either
import arrow.core.orNull
import com.worldsnas.core.ErrorHolder
import com.worldsnas.db.Movie
import com.worldsnas.db.MoviePersister
import com.worldsnas.domain.entity.MovieEntity
import com.worldsnas.domain.mappers.server.*
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import com.worldsnas.panther.Fetcher
import com.worldsnas.panther.Mapper
import com.worldsnas.panther.Persister
import com.worldsnas.panther.RFetcher
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import kotlin.random.Random

@ExperimentalCoroutinesApi
class LatestMovieRepoImplTest {

    val oldFetcher =
        mockk<RFetcher<LatestMovieRequestParam, ResultsServerModel<MovieServerModel>>>(relaxed = true)

    val movieFetcher =
        mockk<Fetcher<LatestMovieRequestParam, ResultsServerModel<MovieServerModel>>>(relaxed = true)

    val oldPersister = mockk<
            Persister<
                    LatestMoviePersisterKey,
                    List<@JvmSuppressWildcards MovieEntity>
                    >
            >()

    val movieServerEntityMapper = mockk<Mapper<MovieServerModel, MovieEntity>>()
    val movieServerRepoMapper = MovieServerRepoMapper(
        GenreServerRepoMapper(),
        CompanyServerRepoMapper(),
        CountryServerRepoMapper(),
        LanguageServerRepoMapper(),
        VideoServerRepoMapper(),
        ImageServerRepoMapper(),
        ReviewServerRepoMapper(),
        CastServerRepoMapper(),
        CrewServerRepoMapper(),
        TranslationServerRepoMapper()
    )
    val movieEntityRepoMapper = mockk<Mapper<MovieEntity, MovieRepoModel>>()
    val moviePersister = mockk<MoviePersister>(relaxed = true)

    lateinit var repo: LatestMovieRepoImpl


    @Before
    fun setUp() {
        clearMocks(
            oldFetcher,
            oldPersister,
            movieServerEntityMapper,
            moviePersister,
            movieEntityRepoMapper
        )
        repo = LatestMovieRepoImpl(
            oldFetcher,
            movieFetcher,
            oldPersister,
            movieServerEntityMapper,
            movieServerRepoMapper,
            movieEntityRepoMapper,
            moviePersister
        )
    }

    @Test
    fun `list is always empty at start`() {
        assertThat(repo.list).hasSize(0)
    }

    @Test
    fun `returns only db when network fails`() = runBlockingTest {
        val dbMovies = listOf(
            Movie.Impl(
                Random.nextLong(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString()
            ),
            Movie.Impl(
                Random.nextLong(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString()
            ),
            Movie.Impl(
                Random.nextLong(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString()
            )
        )

        every {
            moviePersister.observeMovies()
        } returns flowOf(dbMovies)

        coEvery {
            movieFetcher.fetch(any())
        } returns Response.error(400, byteArrayOf().toResponseBody("text/text".toMediaType()))

        val movieListEither = repo.receiveAndUpdate().first()

        val actualList = (movieListEither as Either.Right).b

        assertThat(actualList).isEqualTo(dbMovies.map {
            MovieRepoModel(
                it.id,
                backdropPath = it.backdropImage ?: "",
                posterPath = it.posterImage ?: "",
                title = it.title ?: ""
            )
        })
    }


    @Test
    fun `correct error message when network fails`() = runBlockingTest {
        every {
            moviePersister.observeMovies()
        } returns flowOf(emptyList())

        coEvery {
            movieFetcher.fetch(any())
        } returns Response.error(400, byteArrayOf().toResponseBody("text/text".toMediaType()))

        val movieListEither = repo.receiveAndUpdate().atIndex(1)

        val actualError = (movieListEither as Either.Left).a

        assertThat(actualError).isEqualTo(ErrorHolder.Message(EMPTY_MESSAGE_ERROR, 400))
    }

    @Test
    fun `clears db when network response has no overlap`() = runBlockingTest {
        val dbMovies = listOf(
            Movie.Impl(
                Random.nextLong(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString()
            )
        )

        val networkResponse = ResultsServerModel(
            listOf(
                MovieServerModel(Random.nextLong())
            )
        )

        every {
            moviePersister.observeMovies()
        } returns flowOf(dbMovies)

        coEvery {
            movieFetcher.fetch(any())
        } returns Response.success(networkResponse)

        repo.receiveAndUpdate().first()

        coVerify(atLeast = 1) {
            moviePersister.clearMovies()
        }
    }

    @Test
    fun `no overlap returns response only`() = runBlockingTest {
        val dbMovies = listOf(
            Movie.Impl(
                Random.nextLong(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString()
            )
        )

        val networkMovie = MovieServerModel(Random.nextLong())

        val networkResponse = ResultsServerModel(
            listOf(
                networkMovie
            )
        )

        every {
            moviePersister.observeMovies()
        } returns flowOf(dbMovies)

        coEvery {
            movieFetcher.fetch(any())
        } returns Response.success(networkResponse)

        val eitherList = repo.receiveAndUpdate().first()

        val actualList = eitherList.orNull()!!

        assertThat(actualList).isEqualTo(
            listOf(
                networkMovie.let { movieServerRepoMapper.map(it) }
            )
        )
    }

    @Test
    fun `when response overlaps adds response to the top of the list`() = runBlockingTest {
        val firstOverlappingId = Random.nextLong()
        val secondOverlappingId = Random.nextLong()
        val dbMovies = listOf(
            Movie.Impl(
                firstOverlappingId,
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString()
            ),
            Movie.Impl(
                secondOverlappingId,
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString()
            ),
            Movie.Impl(
                Random.nextLong(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString()
            ),
            Movie.Impl(
                Random.nextLong(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString()
            )
        )

        val serverList = listOf(
            MovieServerModel(
                Random.nextLong()
            ),
            MovieServerModel(
                firstOverlappingId
            ),
            MovieServerModel(
                secondOverlappingId
            )
        )

        every {
            moviePersister.observeMovies()
        } returns flowOf(dbMovies)

        coEvery {
            movieFetcher.fetch(any())
        } returns Response.success(ResultsServerModel(serverList))

        val movieListEither = repo.receiveAndUpdate().first()

        val actualList = (movieListEither as Either.Right).b

        assertThat(actualList).isEqualTo(serverList.map {
            movieServerRepoMapper.map(it)
        }.toMutableList().also {
            it.addAll(dbMovies.subList(2, dbMovies.size).map {
                MovieRepoModel(
                    it.id,
                    backdropPath = it.backdropImage ?: "",
                    posterPath = it.posterImage ?: "",
                    title = it.title ?: ""
                )
            })
        }
        )
    }

    @Test
    fun `inserts network movies in db`() = runBlockingTest {
        every {
            moviePersister.observeMovies()
        } returns flowOf(emptyList())

        val networkMovies = listOf(
            MovieServerModel(
                Random.nextLong()
            ),
            MovieServerModel(
                Random.nextLong()
            ),
            MovieServerModel(
                Random.nextLong()
            ),
            MovieServerModel(
                Random.nextLong()
            )
        )

        coEvery {
            movieFetcher.fetch(any())
        } returns Response.success(
            ResultsServerModel(
                networkMovies
            )
        )

        repo.receiveAndUpdate().toList()

        coVerify(atLeast = 1) {
            moviePersister.insertMovies(networkMovies.map {
                Movie.Impl(
                    it.id,
                    title = it.title,
                    backdropImage = it.backdropPath,
                    posterImage = it.posterPath
                )
            })
        }
    }

}

suspend fun <T> Flow<T>.atIndex(index: Int) =
    toList()[index]

const val EMPTY_MESSAGE_ERROR = "Response.error()"
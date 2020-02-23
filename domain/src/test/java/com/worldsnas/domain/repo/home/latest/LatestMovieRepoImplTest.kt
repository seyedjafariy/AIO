package com.worldsnas.domain.repo.home.latest

import arrow.core.Either
import arrow.core.orNull
import com.worldsnas.core.ErrorHolder
import com.worldsnas.db.LatestMoviePersister
import com.worldsnas.db.Movie
import com.worldsnas.domain.entity.MovieEntity
import com.worldsnas.domain.mappers.MovieDbRepoMapper
import com.worldsnas.domain.mappers.MovieRepoDbMapper
import com.worldsnas.domain.mappers.MovieServerDbMapper
import com.worldsnas.domain.mappers.server.*
import com.worldsnas.domain.model.PageModel
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import com.worldsnas.panther.Fetcher
import com.worldsnas.panther.Mapper
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
/*
@ExperimentalCoroutinesApi
class LatestMovieRepoImplTest {

    val oldFetcher =
        mockk<RFetcher<LatestMovieRequestParam, ResultsServerModel<MovieServerModel>>>(relaxed = true)

    val movieFetcher =
        mockk<Fetcher<LatestMovieRequestParam, ResultsServerModel<MovieServerModel>>>(relaxed = true)

    val movieRepoDBMapper = MovieRepoDbMapper()
    val movieDBRepoMapper = MovieDbRepoMapper()
    val movieServerDbMapper = MovieServerDbMapper()

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
    val moviePersister = mockk<LatestMoviePersister>(relaxed = true)

    lateinit var repo: LatestMovieRepoImpl

    @Before
    fun setUp() {
        clearMocks(
            oldFetcher,
            movieServerEntityMapper,
            moviePersister,
            movieEntityRepoMapper
        )
//        repo = LatestMovieRepoImpl(
//            oldFetcher,
//            movieFetcher,
//            movieServerRepoMapper,
//            moviePersister,
//            movieRepoDBMapper,
//            movieDBRepoMapper
//        )
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
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                false
            ),
            Movie.Impl(
                Random.nextLong(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                false
            ),
            Movie.Impl(
                Random.nextLong(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                false
            )
        )

        every {
            moviePersister.observeMovies()
        } returns flowOf(dbMovies)

        coEvery {
            movieFetcher.fetch(any())
        } returns Response.error(400, byteArrayOf().toResponseBody("text/text".toMediaType()))

        val movieListEither = repo.receiveAndUpdate(PageModel.First).atIndex(1)

        val actualList = (movieListEither as Either.Right).b

        assertThat(actualList).isEqualTo(dbMovies.map {
            movieDBRepoMapper.map(it)
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

        val movieListEither = repo.receiveAndUpdate(PageModel.First).atIndex(2)

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
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                false
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

        repo.receiveAndUpdate(PageModel.First).atIndex(1)

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
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                false
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

        val eitherList = repo.receiveAndUpdate(PageModel.First).atIndex(1)

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
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                false
            ),
            Movie.Impl(
                secondOverlappingId,
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                false
            ),
            Movie.Impl(
                Random.nextLong(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                false
            ),
            Movie.Impl(
                Random.nextLong(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                false
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

        val movieListEither = repo.receiveAndUpdate(PageModel.First).atIndex(1)

        val actualList = (movieListEither as Either.Right).b

        assertThat(actualList).isEqualTo(serverList.map {
            movieServerRepoMapper.map(it)
        }.toMutableList().also {
            it.addAll(dbMovies.subList(2, dbMovies.size).map {
                movieDBRepoMapper.map(it)
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

        repo.receiveAndUpdate(PageModel.First).toList()

        coVerify(atLeast = 1) {
            moviePersister.insertMovies(networkMovies.map {
                movieServerDbMapper.map(it)
            })
        }
    }

    @Test
    fun `load second page from server`() = runBlockingTest {
        every {
            moviePersister.movieCount()
        } returns flowOf(1)

        val networkMovies = listOf(
            MovieServerModel(id = Random.nextLong()),
            MovieServerModel(id = Random.nextLong()),
            MovieServerModel(id = Random.nextLong())
        )

        coEvery {
            movieFetcher.fetch(LatestMovieRequestParam(0))
        } returns Response.success(
            ResultsServerModel(
                networkMovies
            )
        )

        val items = repo.receiveAndUpdate(PageModel.NextPage.Next).first().orNull()

        assertThat(items)
            .isEqualTo(networkMovies.map { movieServerRepoMapper.map(it) })
    }

    @Test
    fun `next page returns error`() = runBlockingTest {
        every {
            moviePersister.movieCount()
        } returns flowOf(1)

        coEvery {
            movieFetcher.fetch(LatestMovieRequestParam(0))
        } returns Response.error(400, byteArrayOf().toResponseBody("text/text".toMediaType()))

        val items = repo.receiveAndUpdate(PageModel.NextPage.Next).first() as Either.Left

        assertThat(items.a)
            .isEqualTo(ErrorHolder.Message(EMPTY_MESSAGE_ERROR, 400))
    }

}

suspend fun <T> Flow<T>.atIndex(index: Int) =
    toList()[index]

const val EMPTY_MESSAGE_ERROR = "Response.error()"
*/
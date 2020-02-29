package com.worldsnas.db

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

interface LatestMoviePersister {
    fun observeSimpleMovies(): Flow<List<Movie>>
    fun observeMovies(
        genres: Boolean = false,
        similars: Boolean = false,
        recomendations: Boolean = false
    ): Flow<List<CompleteMovie>>

    suspend fun clearMovies()
    suspend fun insertMovie(movie: Movie, isLatest: Boolean = true)
    suspend fun insertMovies(movies: List<CompleteMovie>)
    fun movieCount(): Flow<Long>
}

class LatestMoviePersisterImpl(
    private val queries: LatestMovieQueries,
    private val genreQueries: GenreQueries,
    private val movieQueries: MovieQueries,
    private val moviePersister: MoviePersister
) : LatestMoviePersister {

    override fun observeSimpleMovies(): Flow<List<Movie>> =
        queries
            .getLatestMovies()
            .asFlow()
            .mapToList(Dispatchers.IO)

    override fun observeMovies(
        genres: Boolean,
        similars: Boolean,
        recomendations: Boolean
    ): Flow<List<CompleteMovie>> =
        moviePersister.addToMovies(
            observeSimpleMovies(),
            genres,
            similars,
            recomendations
        )

    override suspend fun clearMovies() =
        queries.clearLatestMovies()

    override suspend fun insertMovie(movie: Movie, isLatest: Boolean) =
        queries.insertMovie(
            movie.id,
            movie.title,
            movie.adult,
            movie.originalTitle,
            movie.originalLanguage,
            movie.budget,
            movie.homePage,
            movie.imdbId,
            movie.facebookId,
            movie.instagramId,
            movie.twitterId,
            movie.overview,
            movie.popularity,
            movie.backdropImage,
            movie.posterImage,
            movie.releaseDate,
            movie.revenue,
            movie.runtime,
            movie.status,
            movie.tagLine,
            movie.video,
            movie.voteAverage,
            movie.voteCount,
            isLatest
        )

    override suspend fun insertMovies(movies: List<CompleteMovie>) =
        withContext(Dispatchers.IO) {
            queries.transaction {
                movies.forEach { withDetails ->
                    runBlocking {
                        insertMovie(withDetails.movie)

                        moviePersister.insertMovieExtras(withDetails)
                    }
                }
            }
        }

    override fun movieCount(): Flow<Long> =
        queries
            .latestMovieCount()
            .asFlow()
            .mapToOne()
}

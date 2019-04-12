package com.worldsnas.domain.repo.home.latest

import com.worldsnas.domain.entity.LatestMovieEntity
import com.worldsnas.domain.entity.LatestMovieEntity_
import com.worldsnas.domain.entity.MovieEntity
import com.worldsnas.panther.Persister
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import io.objectbox.kotlin.query
import io.objectbox.rx.RxQuery
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LatestMoviePersister @Inject constructor(
    private val store: BoxStore
) : Persister<LatestMoviePersisterKey, LatestMovieEntity> {
    override fun observe(param: LatestMoviePersisterKey): Observable<LatestMovieEntity> {
        val movieBox = store.boxFor<MovieEntity>()
        val query = movieBox.query {
            backlink(LatestMovieEntity_.movies)
                .equal(LatestMovieEntity_.id, LatestMovieEntity.ENTITY_ID)
        }

        return RxQuery.observable(query)
            .doOnTerminate {
                query.close()
                movieBox.closeThreadResources()
            }
            .map { movies ->
                LatestMovieEntity()
                    .apply {
                        movies.addAll(movies)
                    }
            }
            .subscribeOn(Schedulers.io())
    }

    override fun read(param: LatestMoviePersisterKey): Single<LatestMovieEntity> =
        observe(param)
            .first(LatestMovieEntity())

    override fun write(item: LatestMovieEntity): Completable =
        Completable.create {
            val latestBox = store.boxFor<LatestMovieEntity>()

            val persisted = latestBox
                .get(LatestMovieEntity.ENTITY_ID)
                ?: LatestMovieEntity().apply {
                    latestBox.attach(this)
                }

            persisted.movies.addAll(item.movies)
            persisted.movies.applyChangesToDb()
            latestBox.put(persisted)

            latestBox.put(item)
            latestBox.closeThreadResources()

            if (!it.isDisposed) {
                it.onComplete()
            }
        }
}
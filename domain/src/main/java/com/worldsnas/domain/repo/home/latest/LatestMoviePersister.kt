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
import timber.log.Timber
import javax.inject.Inject

class LatestMoviePersister @Inject constructor(
    private val store: BoxStore
) : Persister<LatestMoviePersisterKey, List<@JvmSuppressWildcards MovieEntity>> {
    override fun observe(param: LatestMoviePersisterKey): Observable<List<MovieEntity>> {
        val movieBox = store.boxFor<MovieEntity>()
        val query = movieBox.query {
            backlink(LatestMovieEntity_.movies)
                .equal(LatestMovieEntity_.id, LatestMovieEntity.ENTITY_ID)
        }
        // return combineLatest(RxQuery.observable(query), RxQuery.observable(latestMovieQuery))
        //     .doOnTerminate {
        //         query.close()
        //         movieBox.closeThreadResources()
        //         latestMovieQuery.close()
        //         latestMovieBox.closeThreadResources()
        //     }
        //     .filter {
        //         it.second.isNotEmpty()
        //     }
        //     .map {
        //         it.second.first()
        //     }
        //     .subscribeOn(Schedulers.io())

        return RxQuery.observable(query)
            .doOnTerminate {
                query.close()
                movieBox.closeThreadResources()
            }
            .doOnNext{
                Timber.d("latest movie received size= ${it.size}")
            }
            .subscribeOn(Schedulers.io())
    }

    override fun read(param: LatestMoviePersisterKey): Single<List<MovieEntity>> =
        observe(param)
            .first(emptyList())

    override fun write(item: List<MovieEntity>): Completable =
        Completable.create {
            val latestBox = store.boxFor<LatestMovieEntity>()

            val persisted = latestBox
                .get(LatestMovieEntity.ENTITY_ID)
                ?: LatestMovieEntity().apply {
                    latestBox.attach(this)
                }

            persisted.movies.addAll(item)
            persisted.movies.applyChangesToDb()
            latestBox.put(persisted)

            latestBox.closeThreadResources()

            if (!it.isDisposed) {
                it.onComplete()
            }
        }
}
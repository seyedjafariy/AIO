package com.worldsnas.domain.repo.home.trending

import com.worldsnas.domain.entity.LatestMovieEntity
import com.worldsnas.domain.entity.MovieEntity
import com.worldsnas.domain.entity.TrendingEntity
import com.worldsnas.domain.entity.TrendingEntity_
import com.worldsnas.panther.Persister
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import io.objectbox.kotlin.equal
import io.objectbox.kotlin.query
import io.objectbox.rx.RxQuery
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TrendingPersister @Inject constructor(
    private val store: BoxStore
) : Persister<TrendingPersisterKey, TrendingEntity> {

    override fun observe(param: TrendingPersisterKey): Observable<TrendingEntity> {
        val movieBox = store.boxFor<MovieEntity>()
        val query = movieBox.query {
            backlink(TrendingEntity_.movies)
                .equal(TrendingEntity_.id, TrendingEntity.ENTITY_ID)
        }

        return RxQuery.observable(query)
            .doOnTerminate {
                query.close()
                movieBox.closeThreadResources()
            }
            .map { movies ->
                TrendingEntity()
                    .apply {
                        movies.addAll(movies)
                    }
            }
            .subscribeOn(Schedulers.io())
    }

    override fun read(param: TrendingPersisterKey): Single<TrendingEntity> =
        observe(param)
            .first(TrendingEntity())

    override fun write(item: TrendingEntity): Completable =
        Completable.create {
            val trendingBox = store.boxFor<TrendingEntity>()

            val persisted = trendingBox
                .get(LatestMovieEntity.ENTITY_ID)
                ?: TrendingEntity().apply {
                    trendingBox.attach(this)
                }

            persisted.movies.addAll(item.movies)
            persisted.movies.applyChangesToDb()
            trendingBox.put(persisted)

            trendingBox.put(item)
            trendingBox.closeThreadResources()

            if (!it.isDisposed) {
                it.onComplete()
            }
        }
}
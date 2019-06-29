package com.worldsnas.domain.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.NameInDb
import io.objectbox.relation.ToMany

@Entity
data class TrendingEntity(
    @Id(assignable = true)
    var id: Long = 0,
    @NameInDb("cache_time")
    val cacheTime: Long = System.currentTimeMillis()
) {
    companion object {
        const val ENTITY_ID = 1
    }

    lateinit var movies: ToMany<MovieEntity>
}
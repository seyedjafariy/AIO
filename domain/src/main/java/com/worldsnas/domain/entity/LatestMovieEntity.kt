package com.worldsnas.domain.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class LatestMovieEntity(
    @Id(assignable = true)
    var id : Long = ENTITY_ID,
    var cacheTime : Long = System.currentTimeMillis()
) {
    lateinit var movies : ToMany<MovieEntity>

    companion object {
        const val ENTITY_ID = 1L
    }

}
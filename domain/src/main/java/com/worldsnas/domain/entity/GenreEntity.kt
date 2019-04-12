package com.worldsnas.domain.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.NameInDb
import io.objectbox.relation.ToMany

@Entity
data class GenreEntity(
    @Id(assignable = true)
    var id: Long = 0,
    @NameInDb("name")
    val name: String = ""
){
    lateinit var movies : ToMany<MovieEntity>
}
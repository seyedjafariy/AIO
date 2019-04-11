package com.worldsnas.domain.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.NameInDb
import io.objectbox.relation.ToOne

@Entity
data class ReviewEntity(
    @Id(assignable = true)
    var id : Long = 0,
    @NameInDb("author")
    val author: String = "",
    @NameInDb("content")
    val content: String = "",
    @NameInDb("review_id")
    val reviewId: String = "",
    @NameInDb("url")
    val url: String = ""
){
    lateinit var movie : ToOne<MovieEntity>
}
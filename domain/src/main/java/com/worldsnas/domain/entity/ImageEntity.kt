package com.worldsnas.domain.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.NameInDb
import io.objectbox.relation.ToOne

@Entity
data class ImageEntity(
    @Id(assignable = true)
    var id : Long = 0,
    @NameInDb("aspect_ratio")
    val aspectRatio: Double = 0.0,
    @NameInDb("file_path")
    val filePath: String = "",
    @NameInDb("height")
    val height: Int = 0,
    @NameInDb("iso")
    val iso: String = "",
    @NameInDb("vote_average")
    val voteAverage: Double = 0.0,
    @NameInDb("vote_count")
    val voteCount: Int = 0,
    @NameInDb("width")
    val width: Int = 0
){
    lateinit var movies : ToOne<MovieEntity>
}
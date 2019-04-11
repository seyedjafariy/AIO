package com.worldsnas.domain.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.NameInDb
import io.objectbox.relation.ToOne

@Entity
data class VideoEntity(
    @Id(assignable = true)
    var id : Long = 0,
    @NameInDb("video_id")
    val videoId: String = "",
    @NameInDb("iso_939")
    val iso_639_1: String = "",
    @NameInDb("iso_3166")
    val iso_3166_1: String = "",
    @NameInDb("key")
    val key: String = "",
    @NameInDb("name")
    val name: String = "",
    @NameInDb("site")
    val site: String = "",
    @NameInDb("size")
    val size: Int = 0,
    @NameInDb("type")
    val type: String = ""
){
    lateinit var movie : ToOne<MovieEntity>
}
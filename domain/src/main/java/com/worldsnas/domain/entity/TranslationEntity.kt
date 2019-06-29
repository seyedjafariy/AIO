package com.worldsnas.domain.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.NameInDb
import io.objectbox.relation.ToMany

@Entity
data class TranslationEntity(
    @Id(assignable = true)
    var id : Long = 0,
    @NameInDb("iso_3166")
    val iso_3166_1: String = "",
    @NameInDb("iso_639")
    val iso_639_1: String = "",
    @NameInDb("name")
    val name: String = "",
    @NameInDb("english_name")
    val englishName: String = "",
    @NameInDb("title")
    val title: String = "",
    @NameInDb("overview")
    val overview: String = "",
    @NameInDb("home_page")
    val homePage: String = ""
){
    lateinit var movies : ToMany<MovieEntity>
}
package com.worldsnas.domain.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.NameInDb
import io.objectbox.relation.ToMany

@Entity
data class CompanyEntity(
    @Id(assignable = true)
    var id: Long = 0,
    @NameInDb("logo_path")
    val logoPath: String = "",
    @NameInDb("name")
    val name: String = "",
    @NameInDb("origin_country")
    val originCountry: String = ""
){
    lateinit var movies : ToMany<MovieEntity>
}
package com.worldsnas.daggercore

import android.app.Application
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.squareup.moshi.Moshi
import com.worldsnas.daggercore.modules.DatabaseModule
import com.worldsnas.domain.repo.genre.MovieGenreRepo
import com.worldsnas.domain.repo.home.latest.LatestMovieRepo
import com.worldsnas.domain.repo.home.trending.TrendingRepo
import com.worldsnas.domain.repo.moviedetail.MovieDetailRepo
import com.worldsnas.domain.repo.people.PeopleRepo
import com.worldsnas.domain.repo.search.keywords.SearchKeywordsRepo
import com.worldsnas.domain.repo.search.movie.MovieSearchRepo
import dagger.BindsInstance
import io.objectbox.BoxStore
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface BaseComponent {

    fun application() : Application

    fun retrofit(): Retrofit
    fun okHttp(): OkHttpClient
    fun moshi(): Moshi
    fun store(): BoxStore

    fun latestMovieRepo(): LatestMovieRepo
    fun trendingRepo(): TrendingRepo
    fun movieSearchRepo(): MovieSearchRepo
    fun movieDetailRepo(): MovieDetailRepo
    fun movieGenreRepo(): MovieGenreRepo
    fun peopleRepo(): PeopleRepo
    fun searchKeywordsRepo(): SearchKeywordsRepo

    fun frescoConfig(): ImagePipelineConfig

    interface Builder {

        @BindsInstance
        fun setApplication(app: Application): Builder

        fun setDatabaseModule(module: DatabaseModule): Builder

        fun build(): BaseComponent
    }
}
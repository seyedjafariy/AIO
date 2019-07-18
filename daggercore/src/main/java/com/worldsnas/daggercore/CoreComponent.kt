package com.worldsnas.daggercore

import android.app.Application
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.squareup.moshi.Moshi
import com.worldsnas.daggercore.modules.CoreModule
import com.worldsnas.daggercore.modules.DatabaseModule
import com.worldsnas.daggercore.modules.network.NetworkModule
import com.worldsnas.daggercore.scope.AppScope
import com.worldsnas.domain.di.DomainModule
import com.worldsnas.domain.repo.genre.MovieGenreRepo
import com.worldsnas.domain.repo.home.latest.LatestMovieRepo
import com.worldsnas.domain.repo.home.trending.TrendingRepo
import com.worldsnas.domain.repo.moviedetail.MovieDetailRepo
import com.worldsnas.domain.repo.people.PeopleRepo
import com.worldsnas.domain.repo.search.movie.MovieSearchRepo
import dagger.BindsInstance
import dagger.Component
import io.objectbox.BoxStore
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@AppScope
@Component(
    modules = [
        NetworkModule::class,
        DomainModule::class,
        DatabaseModule::class,
        CoreModule::class,
        FrescoModule::class]
)
interface CoreComponent {

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

    fun frescoConfig(): ImagePipelineConfig

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun setApplication(app: Application): Builder

        fun setDatabaseModule(module: DatabaseModule): Builder

        fun build(): CoreComponent
    }
}
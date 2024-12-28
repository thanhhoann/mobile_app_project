package com.mobile_app.project.data

import com.mobile_app.project.network.MovieApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// DI container at application level
interface AppContainer {
    val movieRepository: MovieRepository
}

// Implementation of the DI container at the application level
@Module
@InstallIn(SingletonComponent::class)
class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitService(retrofit: Retrofit): MovieApiService {
        return retrofit.create(MovieApiService::class.java)
    }

    // DI implementation for Movie Repository
    override val movieRepository: MovieRepository by lazy {
        DefaultMovieRepository(provideRetrofitService(provideRetrofit()))
    }
}


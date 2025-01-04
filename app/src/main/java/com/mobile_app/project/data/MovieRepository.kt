package com.mobile_app.project.data

import com.mobile_app.project.model.MovieDetailsResponse
import com.mobile_app.project.model.MovieListsApiResponse
import com.mobile_app.project.network.MovieApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository retrieves movie data
 */
interface MovieRepository {
    suspend fun getNowPlayingMovies(page: Int): Response<MovieListsApiResponse>
    suspend fun getPopularMovies(page: Int): Response<MovieListsApiResponse>
    suspend fun getTopRatedMovies(page: Int): Response<MovieListsApiResponse>
    suspend fun getUpcomingMovies(page: Int): Response<MovieListsApiResponse>
    suspend fun getMovieDetails(movieId: Int): Response<MovieDetailsResponse>
    suspend fun searchMovies(query: String): Response<MovieListsApiResponse>
}

/**
 * Network Implementation of repository
 */
@Singleton
class DefaultMovieRepository @Inject constructor(private val movieApiService: MovieApiService) :
    MovieRepository {
    private val tmdbApiKey = "6bbbb9334e0477b060153236eca7ae2e"

    override suspend fun getNowPlayingMovies(page: Int): Response<MovieListsApiResponse> =
        movieApiService.getNowPlayingMovies(
            apiKey = tmdbApiKey,
            page = page
        )

    override suspend fun getPopularMovies(page: Int): Response<MovieListsApiResponse> =
        movieApiService.getPopularMovies(
            apiKey = tmdbApiKey,
            page = page
        )

    override suspend fun getTopRatedMovies(page: Int): Response<MovieListsApiResponse> =
        movieApiService.getTopRatedMovies(
            apiKey = tmdbApiKey,
            page = page
        )

    override suspend fun getUpcomingMovies(page: Int): Response<MovieListsApiResponse> =
        movieApiService.getUpcomingMovies(
            apiKey = tmdbApiKey,
            page = page
        )

    override suspend fun getMovieDetails(movieId: Int): Response<MovieDetailsResponse> =
        movieApiService.getMovieDetails(
            movieId = movieId,
            apiKey = tmdbApiKey,
        )

    override suspend fun searchMovies(query: String): Response<MovieListsApiResponse> =
        movieApiService.searchMovies(
            apiKey = tmdbApiKey,
            query = query
        )
}

/**
 * Provide dependencies for [MovieRepository]
 */
@Module
@InstallIn(SingletonComponent::class)
object MovieDataModule {
    @Provides
    @Singleton
    fun provideMovieRepository(movieApiService: MovieApiService): MovieRepository {
        return DefaultMovieRepository(movieApiService)
    }
}
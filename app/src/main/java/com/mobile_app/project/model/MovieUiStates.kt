package com.mobile_app.project.model

/**
 * UI state for Popular Movies
 */
sealed interface PopularMoviesUiState {
    data class Success(val popularMovies: MovieListsApiResponse) : PopularMoviesUiState
    data object Error : PopularMoviesUiState
    data object Loading : PopularMoviesUiState
}

/**
 * UI state for Now Playing Movies
 */
sealed interface NowPlayingMoviesUiState {
    data class Success(val nowPlayingMovies: MovieListsApiResponse) : NowPlayingMoviesUiState
    data object Error : NowPlayingMoviesUiState
    data object Loading : NowPlayingMoviesUiState
}

/**
 * UI state for Top Rated Movies
 */
sealed interface TopRatedMoviesUiState {
    data class Success(val topRatedMovies: MovieListsApiResponse) : TopRatedMoviesUiState
    data object Error : TopRatedMoviesUiState
    data object Loading : TopRatedMoviesUiState
}

/**
 * UI state for Upcoming Movies
 */
sealed interface UpcomingMoviesUiState {
    data class Success(val upcomingMovies: MovieListsApiResponse) : UpcomingMoviesUiState
    data object Error : UpcomingMoviesUiState
    data object Loading : UpcomingMoviesUiState
}

/**
 * UI state for Movie Details
 */
sealed interface MovieDetailsUiState {
    data class Success(val movieDetails: MovieDetailsResponse) : MovieDetailsUiState
    data object Error : MovieDetailsUiState
    data object Loading : MovieDetailsUiState
}

/**
 * UI state for Search Movies
 */
sealed interface SearchMoviesUiState {
    data class Success(val searchMovies: MovieListsApiResponse) : SearchMoviesUiState
    data object Error : SearchMoviesUiState
    data object Loading : SearchMoviesUiState
    data object EmptySearch : SearchMoviesUiState
}

data class SelectedMovieIdUiState(
    val id: Int = 0
)
package com.mobile_app.project.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mobile_app.project.MovieApplication
import com.mobile_app.project.data.MovieRepository
import com.mobile_app.project.model.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI state for Popular Movies
 */
sealed interface PopularMoviesUiState {
    data class Success(val popularMovies: ApiResponse) : PopularMoviesUiState
    data object Error : PopularMoviesUiState
    data object Loading : PopularMoviesUiState
}

/**
 * UI state for Now Playing Movies
 */
sealed interface NowPlayingMoviesUiState {
    data class Success(val nowPlayingMovies: ApiResponse) : NowPlayingMoviesUiState
    data object Error : NowPlayingMoviesUiState
    data object Loading : NowPlayingMoviesUiState
}

/**
 * UI state for Top Rated Movies
 */
sealed interface TopRatedMoviesUiState {
    data class Success(val topRatedMovies: ApiResponse) : TopRatedMoviesUiState
    data object Error : TopRatedMoviesUiState
    data object Loading : TopRatedMoviesUiState
}

/**
 * UI state for Upcoming Movies
 */
sealed interface UpcomingMoviesUiState {
    data class Success(val upcomingMovies: ApiResponse) : UpcomingMoviesUiState
    data object Error : UpcomingMoviesUiState
    data object Loading : UpcomingMoviesUiState
}

/**
 * ViewModel containing the app data and method to retrieve the data
 */
@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {
    // UI state for Popular Movies
    private val _popularMoviesUiState =
        MutableStateFlow<PopularMoviesUiState>(PopularMoviesUiState.Loading)
    val popularMoviesUiState: StateFlow<PopularMoviesUiState> = _popularMoviesUiState

    // UI state for Now Playing Movies
    private val _nowPlayingMoviesUiState =
        MutableStateFlow<NowPlayingMoviesUiState>(NowPlayingMoviesUiState.Loading)
    val nowPlayingMoviesUiState: StateFlow<NowPlayingMoviesUiState> = _nowPlayingMoviesUiState

    // UI state for Top Rated Movies
    private val _topRatedMoviesUiState =
        MutableStateFlow<TopRatedMoviesUiState>(TopRatedMoviesUiState.Loading)
    val topRatedMoviesUiState: StateFlow<TopRatedMoviesUiState> = _topRatedMoviesUiState

    // UI state for Upcoming movies
    private val _upcomingMoviesUiState =
        MutableStateFlow<UpcomingMoviesUiState>(UpcomingMoviesUiState.Loading)
    val upcomingMoviesUiState: StateFlow<UpcomingMoviesUiState> = _upcomingMoviesUiState

    init {
        getPopularMoviesFromViewModel()
        getNowPlayingMoviesFromViewModel()
        getTopRatedMoviesFromViewModel()
        getUpcomingMoviesFromViewModel()
    }

    /**
     * Factory for [MovieViewModel] that takes [MovieRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MovieApplication)
                val movieRepository = application.container.movieRepository
                MovieViewModel(movieRepository = movieRepository)
            }
        }
    }


    private fun getPopularMoviesFromViewModel() {
        viewModelScope.launch {
            try {
                val response = movieRepository.getPopularMovies(page = 1)
                if (response.isSuccessful && response.body() != null) {
                    _popularMoviesUiState.value = PopularMoviesUiState.Success(response.body()!!)
                } else {
                    _popularMoviesUiState.value = PopularMoviesUiState.Error
                }
            } catch (e: Exception) {
                PopularMoviesUiState.Error
            }
        }
    }

    private fun getNowPlayingMoviesFromViewModel() {
        viewModelScope.launch {
            try {
                val response = movieRepository.getNowPlayingMovies(page = 1)
                if (response.isSuccessful && response.body() != null) {
                    _nowPlayingMoviesUiState.value =
                        NowPlayingMoviesUiState.Success(response.body()!!)
                } else {
                    _nowPlayingMoviesUiState.value = NowPlayingMoviesUiState.Error
                }
            } catch (e: Exception) {
                NowPlayingMoviesUiState.Error
            }
        }
    }

    private fun getTopRatedMoviesFromViewModel() {
        viewModelScope.launch {
            try {
                val response = movieRepository.getTopRatedMovies(page = 1)
                if (response.isSuccessful && response.body() != null) {
                    _topRatedMoviesUiState.value =
                        TopRatedMoviesUiState.Success(response.body()!!)
                } else {
                    _topRatedMoviesUiState.value = TopRatedMoviesUiState.Error
                }
            } catch (e: Exception) {
                TopRatedMoviesUiState.Error
            }
        }
    }

    private fun getUpcomingMoviesFromViewModel() {
        viewModelScope.launch {
            try {
                val response = movieRepository.getUpcomingMovies(page = 1)
                if (response.isSuccessful && response.body() != null) {
                    _upcomingMoviesUiState.value =
                        UpcomingMoviesUiState.Success(response.body()!!)
                } else {
                    _upcomingMoviesUiState.value = UpcomingMoviesUiState.Error
                }
            } catch (e: Exception) {
                UpcomingMoviesUiState.Error
            }
        }
    }
}

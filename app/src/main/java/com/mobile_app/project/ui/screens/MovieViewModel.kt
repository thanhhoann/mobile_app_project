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
 * UI state for the Home screen
 */
sealed interface MovieUiState {
    data class Success(val apiResponse: ApiResponse) : MovieUiState
    data object Error : MovieUiState
    data object Loading : MovieUiState
}

/**
 * ViewModel containing the app data and method to retrieve the data
 */
@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {
    private val _movieUiState = MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val movieUiState: StateFlow<MovieUiState> = _movieUiState

    init {
        getPopularMoviesFromViewModel()
    }

    private fun getPopularMoviesFromViewModel() {
        viewModelScope.launch {
            try {
                val response = movieRepository.getPopularMovies(page = 1)
                if (response.isSuccessful && response.body() != null) {
                    _movieUiState.value = MovieUiState.Success(response.body()!!)
                } else {
                    _movieUiState.value = MovieUiState.Error
                }
            } catch (e: Exception) {
                MovieUiState.Error
            }
        }
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

}

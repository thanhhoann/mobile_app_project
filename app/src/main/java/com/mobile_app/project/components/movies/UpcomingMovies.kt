package com.mobile_app.project.components.movies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mobile_app.project.ui.screens.MovieViewModel
import com.mobile_app.project.ui.screens.UpcomingMoviesUiState
import com.mobile_app.project.ui.theme.Typography
import com.mobile_app.project.ui.theme.on_background
import com.mobile_app.project.ui.theme.primary

@Composable
fun UpcomingMovies(movieViewModel: MovieViewModel = hiltViewModel()) {
    val upcomingMoviesUiState by movieViewModel.upcomingMoviesUiState.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Upcoming Movies",
                color = on_background,
                style = Typography.headlineMedium
            )
            Text(
                text = "See All >",
                color = primary, style = Typography.bodySmall
            )
        }

        when (upcomingMoviesUiState) {
            is UpcomingMoviesUiState.Loading -> Text(text = "Loading", color = Color.White)
            is UpcomingMoviesUiState.Error -> Text(text = "Error", color = Color.White)
            is UpcomingMoviesUiState.Success -> {
                val movies =
                    (upcomingMoviesUiState as UpcomingMoviesUiState.Success).upcomingMovies.results
                LazyRow(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(movies) { movie ->
                        MovieItem(movie)
                    }
                }
            }
        }
    }
}
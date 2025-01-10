package com.mobile_app.project.components.movies

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mobile_app.project.MovieScreens
import com.mobile_app.project.config.auth.AuthService
import com.mobile_app.project.model.TopRatedMoviesUiState
import com.mobile_app.project.ui.screens.MovieViewModel
import com.mobile_app.project.ui.theme.Typography
import com.mobile_app.project.ui.theme.on_background
import com.mobile_app.project.ui.theme.primary

@Composable
fun TopRatedMovies(
    authService: AuthService,
    movieViewModel: MovieViewModel = hiltViewModel(), navController: NavController) {
    val topRatedMoviesUiState by movieViewModel.topRatedMoviesUiState.collectAsState()
    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Top Rated Movies", color = on_background, style = Typography.headlineMedium
            )
            Text(
                text = "See All >", color = primary, style = Typography.bodySmall
            )
        }

        when (topRatedMoviesUiState) {
            is TopRatedMoviesUiState.Loading -> Text(text = "Loading", color = Color.White)
            is TopRatedMoviesUiState.Error -> Text(text = "Error", color = Color.White)
            is TopRatedMoviesUiState.Success -> {
                val movies =
                    (topRatedMoviesUiState as TopRatedMoviesUiState.Success).topRatedMovies.results
                LazyRow(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(movies) { movie ->
                        Box(
                            modifier = Modifier
                                .width(120.dp)
                                .height(180.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    if (!authService.isLoggedIn) {
                                        navController.navigate(MovieScreens.SignIn.name)
                                        Toast.makeText(context, "Please sign in to view details", Toast.LENGTH_SHORT).show()
                                    } else {
                                        movieViewModel.setSelectMovieId(movie.id)
                                        navController.navigate(MovieScreens.Detail.name)
                                    }
                                },
                        ) {
                            val imageUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"

                            AsyncImage(
                                model = ImageRequest.Builder(
                                    context = LocalContext.current
                                ).data(imageUrl).crossfade(true).build(),
                                contentDescription = movie.overview,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                tint = Color.White,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(24.dp)
                                    .background(
                                        Color.Black.copy(alpha = 0.6f),
                                        shape = RoundedCornerShape(50)
                                    )
                                    .padding(4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
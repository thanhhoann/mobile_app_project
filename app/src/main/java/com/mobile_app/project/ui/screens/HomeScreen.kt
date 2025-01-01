package com.mobile_app.project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mobile_app.project.MovieScreens
import com.mobile_app.project.components.movies.NowPlayingMovies
import com.mobile_app.project.components.movies.PopularMovies
import com.mobile_app.project.components.movies.TopRatedMovies
import com.mobile_app.project.components.movies.UpcomingMovies
import com.mobile_app.project.model.NowPlayingMoviesUiState
import com.mobile_app.project.ui.theme.Typography
import com.mobile_app.project.ui.theme.primary_background
import kotlin.random.Random

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController() // Mock NavController for preview
//    HomeScreen(navController = navController)
}

@Composable
fun HomeScreen(
    viewModel: MovieViewModel,
    navController: NavController,
    contentPadding: Any? = null,
    modifier: Modifier = Modifier,
) {

    Surface(
        modifier = modifier.fillMaxSize(),
        color = primary_background
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp)
        ) {
            item { FeaturedMovie(viewModel, navController) }
            item { NowPlayingMovies(viewModel, navController) }
            item { PopularMovies(viewModel, navController) }
            item { TopRatedMovies(viewModel, navController) }
            item { UpcomingMovies(viewModel, navController) }
        }
    }
}

@Composable
fun FeaturedMovie(movieViewModel: MovieViewModel = hiltViewModel(), navController: NavController) {
    val nowPlayingMoviesUiState = movieViewModel.nowPlayingMoviesUiState.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        when (val state = nowPlayingMoviesUiState.value) {
            is NowPlayingMoviesUiState.Loading -> Text(text = "Loading", color = Color.White)
            is NowPlayingMoviesUiState.Error -> Text(text = "Error", color = Color.White)
            is NowPlayingMoviesUiState.Success -> {
                val movies = state.nowPlayingMovies.results
                val randomMovie = movies[Random.nextInt(movies.size)]
                val imageUrl = "https://image.tmdb.org/t/p/w500${randomMovie.posterPath}"

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(
                            context = LocalContext.current
                        ).data(imageUrl).crossfade(true).build(),
                        contentDescription = randomMovie.overview,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                BottomOverlayBox(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = 48.dp)
                        .zIndex(1f),
                    navController = navController,
                    movieViewModel = movieViewModel,
                    movieId = randomMovie.id,
                    movieTitle = randomMovie.title,
                    voteCount = randomMovie.voteCount.toString(),
                    releaseDate = randomMovie.releaseDate
                )
            }
        }

//        WatchTrailerButton(
//            modifier = Modifier
//                .align(Alignment.TopEnd)
//                .offset(y = 150.dp)
//        )
    }

    Spacer(modifier = Modifier.height(40.dp))
}

@Composable
fun BottomOverlayBox(
    modifier: Modifier = Modifier,
    movieViewModel: MovieViewModel = hiltViewModel(),
    navController: NavController,
    movieId: Int,
    movieTitle: String,
    voteCount: String,
    releaseDate: String,
) {
    Box(
        modifier = modifier
            .fillMaxWidth(0.85f)
            .height(140.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.DarkGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "TRENDING",
                    color = Color.Red,
                    style = Typography.bodySmall
                )
                Text(
                    text = movieTitle,
                    color = Color.White,
                    style = Typography.headlineMedium
                )
                Text(
                    text = "$voteCount Votes",
                    color = Color.White,
                    style = Typography.bodySmall
                )
                Text(
                    text = "Released on $releaseDate",
                    color = Color.White,
                    style = Typography.bodySmall
                )
            }
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        movieViewModel.setSelectMovieId(movieId)
                        navController.navigate(MovieScreens.Detail.name)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    modifier = Modifier
                        .height(50.dp)
                        .width(90.dp)
                ) {
                    Text(
                        text = "View details",
                        color = Color.White,
                        style = Typography.labelMedium
                    )
                }
            }
        }
    }
}


@Composable
fun WatchTrailerButton(modifier: Modifier = Modifier) {
    Button(
        onClick = { /* Add trailer playback */ },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black.copy(alpha = 0.6f)),
        modifier = modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Watch Trailer",
                color = Color.White,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}




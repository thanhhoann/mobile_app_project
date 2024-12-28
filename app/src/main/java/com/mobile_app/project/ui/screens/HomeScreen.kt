package com.mobile_app.project.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mobile_app.project.MovieScreens
import com.mobile_app.project.R
import com.mobile_app.project.components.movies.NowPlayingMovies
import com.mobile_app.project.components.movies.PopularMovies
import com.mobile_app.project.components.movies.TopRatedMovies
import com.mobile_app.project.components.movies.UpcomingMovies
import com.mobile_app.project.ui.theme.Typography
import com.mobile_app.project.ui.theme.primary_background

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController() // Mock NavController for preview
    HomeScreen(navController = navController)
}

@Composable
fun HomeScreen(
    navController: NavController,
    contentPadding: Any? = null,
    modifier: Modifier = Modifier,
) {
    val movieViewModel: MovieViewModel = viewModel(factory = MovieViewModel.Factory)
    Surface(
        modifier = modifier.fillMaxSize(),
        color = primary_background
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp)
        ) {
            item { FeaturedMovie(navController) }
            item { PopularMovies(movieViewModel) }
            item { NowPlayingMovies(movieViewModel) }
            item { TopRatedMovies(movieViewModel) }
            item { UpcomingMovies(movieViewModel) }
        }
    }
}

@Composable
fun FeaturedMovie(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Image Box (Image should be behind the overlay)
        ImageBox()

        // Bottom Overlay Box moved lower but still overlays the image
        BottomOverlayBox(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = 48.dp)
                .zIndex(1f),
            navController = navController
        )

        // "Watch Trailer" Button (Positioned at the top-right of the image)
        WatchTrailerButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(y = 150.dp)
        )
    }
}

@Composable
fun ImageBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.evil_dead),
            contentDescription = "Evil Dead",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun BottomOverlayBox(modifier: Modifier = Modifier, navController: NavController) {
    Box(
        modifier = modifier
            .fillMaxWidth(0.85f)
            .height(120.dp)
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
                    text = "EVIL DEAD RISE",
                    color = Color.White,
                    style = Typography.headlineMedium
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "A",
                        color = Color.White,
                        style = Typography.bodySmall
                    )
                    Text(
                        text = "•",
                        color = Color.White,
                        style = Typography.bodySmall
                    )
                    Text(
                        text = "ENGLISH",
                        color = Color.White,
                        style = Typography.bodySmall
                    )
                }
                Text(
                    text = "HORROR",
                    color = Color.White,
                    style = Typography.bodySmall
                )
            }
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                //Go to movie detail screen
                Button(
                    onClick = { navController.navigate(MovieScreens.Detail.name) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    modifier = Modifier
                        .height(36.dp)
                        .width(80.dp)
                ) {
                    Text(
                        text = "Book",
                        color = Color.White,
                        style = Typography.labelMedium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "2D,3D,4DX",
                    color = Color.White,
                    style = Typography.bodySmall
                )
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




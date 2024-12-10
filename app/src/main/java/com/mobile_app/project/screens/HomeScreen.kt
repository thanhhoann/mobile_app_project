package com.mobile_app.project.screens

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.mobile_app.project.R
import com.mobile_app.project.ui.theme.Typography
import com.mobile_app.project.ui.theme.on_background
import com.mobile_app.project.ui.theme.primary
import com.mobile_app.project.ui.theme.primary_background

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = primary_background
    ) {
        Column {
            FeaturedMovie()
            Spacer(modifier = Modifier.height(32.dp))
            RecommendedMovies()
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun FeaturedMovie() {
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
                .zIndex(1f)
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
fun BottomOverlayBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth(0.85f) // Keeps the overlay smaller than the image for a cleaner effect
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
                Button(
                    onClick = { /* navigate to movie detail */ },
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
        onClick = { /* add later */ },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black.copy(alpha = 0.6f)),
        modifier = modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Text(
            text = "Watch Trailer",
            color = Color.White,
            style = Typography.bodySmall,
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

@Composable
fun RecommendedMovies() {
    val movies = listOf("salaar", "flash", "aquaman")

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recommended Movies",
                color = on_background,
                style = Typography.headlineMedium
            )
            Text(
                text = "See All >",
                color = primary,
                style = Typography.bodySmall
            )
        }

        LazyRow(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(movies) { movie ->
                MoviePoster(movie)
            }
        }
    }
}

@Composable
fun MoviePoster(movie: String) {
    Box(
        modifier = Modifier
            .width(120.dp)
            .height(180.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Image(
            painter = painterResource(
                id = when (movie) {
                    "salaar" -> R.drawable.salaar
                    "flash" -> R.drawable.flash
                    "aquaman" -> R.drawable.aquaman
                    else -> R.xml.placeholder // Placeholder image
                }
            ),
            contentDescription = movie,
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
                .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(50))
                .padding(4.dp)
        )
    }
}


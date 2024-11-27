package com.mobile_app.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mobile_app.project.ui.theme.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color

@Composable
fun HomeScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = primary_background // Using the color defined in color.kt
    ) {
        Column {
            TopBar()
            FeaturedMovie()
            RecommendedMovies()
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Search Bar
        TextField(
            value = "", // Replace with a state variable to handle user input
            onValueChange = {}, // Handle text input here
            placeholder = {
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Gray.copy(alpha = 0.2f),
                focusedIndicatorColor = Color.Transparent, // Removes underline when focused
                unfocusedIndicatorColor = Color.Transparent // Removes underline when not focused
            ),
            modifier = Modifier
                .weight(1f) // Ensures the search bar takes available width
                .height(32.dp)
                .clip(RoundedCornerShape(24.dp)) // Rounded corners for search bar
        )

        Spacer(modifier = Modifier.width(16.dp)) // Space between search bar and icons

        // Search Icon
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            tint = on_background, // From color.kt
            modifier = Modifier.size(16.dp)
        )

        Spacer(modifier = Modifier.width(16.dp)) // Space between icons

        // Profile Icon
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Profile",
            tint = on_background,
            modifier = Modifier.size(16.dp)
        )
    }
}


@Composable
fun FeaturedMovie() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.evil_dead),
            contentDescription = "Evil Dead",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = "TRENDING",
                color = on_background,
                style = Typography.bodySmall // Using typography from type.kt
            )
            Text(
                text = "EVIL DEAD RISE",
                color = on_primary,
                style = Typography.headlineMedium // Using typography from type.kt
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "ENGLISH",
                    color = on_background,
                    style = Typography.bodySmall,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = "HORROR",
                    color = on_background,
                    style = Typography.bodySmall
                )
            }
            Button(
                onClick = { /* navigate to movie detail */ },
                colors = ButtonDefaults.buttonColors(containerColor = primary),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(
                    text = "Book",
                    color = on_primary,
                    style = Typography.labelLarge
                )
            }
        }
        Button(
            onClick = { /* add later */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black.copy(alpha = 0.6f)),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = on_primary
            )
            Text(
                text = "Watch Trailer",
                color = on_primary,
                style = Typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
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
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(movies) { movie ->
                MoviePoster(movie)
            }
        }
    }
}

@Composable
fun MoviePoster(movie: String) {
    Column {
        Image(
            painter = painterResource(
                id = when (movie) {
                    "salaar" -> R.drawable.salaar
                    "flash" -> R.drawable.flash
                    "aquaman" -> R.drawable.aquaman
                    else -> R.xml.placeholder // Replace with actual placeholder drawable
                }
            ),
            contentDescription = movie,
            modifier = Modifier
                .width(140.dp)
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Text(
            text = when (movie) {
                "salaar" -> "SALAAR (PART 1)"
                "flash" -> "FLASH (2023)"
                "aquaman" -> "AQUAMAN"
                else -> movie
            },
            color = on_background,
            style = Typography.bodySmall,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
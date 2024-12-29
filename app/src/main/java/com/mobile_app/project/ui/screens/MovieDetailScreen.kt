package com.mobile_app.project.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mobile_app.project.R
import com.mobile_app.project.model.MovieDetailsUiState
import com.mobile_app.project.ui.theme.Typography

// Data class for cast members
data class CastMember(
    val id: Int, val name: String, val imageResId: Int
)

// Preview for MovieScreen
@Preview(showBackground = true)
@Composable
fun MovieScreenPreview() {
    val navController = rememberNavController() // Mock NavController for preview
//    MovieScreen(navController = navController)
}

@Composable
fun MovieScreen(viewModel: MovieViewModel, navController: NavController) {
    // Sample cast members - change later when database connect
    val castMembers = remember {
        listOf(
            CastMember(1, "Cast Member 1", R.drawable.cast_member_1),
            CastMember(2, "Cast Member 2", R.drawable.cast_member_2),
            CastMember(3, "Cast Member 3", R.drawable.cast_member_3),
            CastMember(4, "Cast Member 4", R.drawable.cast_member_4),
            CastMember(5, "Cast Member 5", R.drawable.cast_member_5)
        )
    }

    MovieDetail(viewModel, castMembers, navController)
}

@Composable
fun MovieDetail(
    viewModel: MovieViewModel, castMembers: List<CastMember>, navController: NavController
) {
    val selectedMovieIdUiState by viewModel.selectedMovieIdUiState.collectAsState()
    val selectedMovieId = selectedMovieIdUiState.id
    val movieDetailsUiState = viewModel.movieDetailsUiState.collectAsState()

    LaunchedEffect(selectedMovieId) {
        viewModel.getMovieDetailsFromViewModel(selectedMovieId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Movie Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        ) {
            when (val state = movieDetailsUiState.value) {
                is MovieDetailsUiState.Loading -> {
                    Text("Loading movie poster...")
                }

                is MovieDetailsUiState.Success -> {
                    val imageUrl = "https://image.tmdb.org/t/p/w500${state.movieDetails.posterPath}"
                    AsyncImage(
                        model = ImageRequest.Builder(
                            context = LocalContext.current
                        ).data(imageUrl).crossfade(true).build(),
                        contentDescription = state.movieDetails.overview,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                is MovieDetailsUiState.Error -> {
                    Text("Error fetching movie poster.")
                }
            }

            // Navigation Buttons
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp, vertical = 48.dp),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                // Back arrow
//                IconButton(
//                    onClick = { navController.popBackStack() },
//                    modifier = Modifier
//                        .size(40.dp)
//                        .background(Color.Black.copy(alpha = 0.5f), shape = CircleShape)
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.arrow_back_24dp_e8eaed_fill0_wght400_grad0_opsz24),
//                        contentDescription = "Back",
//                        tint = Color.White,
//                        modifier = Modifier.size(24.dp)
//                    )
//                }
//                // Menu button
//                IconButton(
//                    onClick = { /* Handle Menu */ },
//                    modifier = Modifier
//                        .size(40.dp)
//                        .background(Color.Black.copy(alpha = 0.5f), shape = CircleShape)
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.MoreVert,
//                        contentDescription = "Menu",
//                        tint = Color.White
//                    )
//                }
//            }
        }

        // Details Box that overlaps the banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 200.dp)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                color = Color(0xFF121212).copy(alpha = 0.98f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    /*
                    ** Movie Title
                    */
                    when (val state = movieDetailsUiState.value) {
                        is MovieDetailsUiState.Loading -> {
                            Text("Loading movie title...")
                        }

                        is MovieDetailsUiState.Success -> {
                            Text(
                                text = state.movieDetails.title,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                style = Typography.headlineMedium
                            )
                        }

                        is MovieDetailsUiState.Error -> {
                            Text("Error fetching movie title.")
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        /*
                        ** Movie Overview
                        */
                        when (val state = movieDetailsUiState.value) {
                            is MovieDetailsUiState.Loading -> {
                                Text("Loading movie title...")
                            }

                            is MovieDetailsUiState.Success -> {
                                state.movieDetails.overview?.let {
                                    Text(
                                        text = it, fontSize = 14.sp, color = Color.Gray
                                    )
                                }
                            }

                            is MovieDetailsUiState.Error -> {
                                Text("Error fetching movie title.")
                            }
                        }

//                        Button(
//                            onClick = { /* Handle trailer */ },
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = Color.DarkGray
//                            ),
//                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
//                        ) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.play_arrow_24dp_e8eaed_fill0_wght400_grad0_opsz24),
//                                contentDescription = null,
//                                modifier = Modifier.size(16.dp)
//                            )
//                            Spacer(modifier = Modifier.width(4.dp))
//                            Text("Watch Trailer", fontSize = 12.sp)
//                        }
                    }

                    // Movie Info Grid
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        /*
                        ** Vote count
                        */
                        when (val state = movieDetailsUiState.value) {
                            is MovieDetailsUiState.Loading -> {
                                Text("Getting vote count...")
                            }

                            is MovieDetailsUiState.Success -> {
                                MovieInfoItem("Vote Count", state.movieDetails.voteCount.toString())
                            }

                            is MovieDetailsUiState.Error -> {
                                Text("Error fetching vote count.")
                            }
                        }/*
                        ** Release Date
                        */
                        when (val state = movieDetailsUiState.value) {
                            is MovieDetailsUiState.Loading -> {
                                Text("Getting release date...")
                            }

                            is MovieDetailsUiState.Success -> {
                                MovieInfoItem("Release Date", state.movieDetails.releaseDate)
                            }

                            is MovieDetailsUiState.Error -> {
                                Text("Error fetching release date.")
                            }
                        }/*
                        ** Adult
                        */
                        when (val state = movieDetailsUiState.value) {
                            is MovieDetailsUiState.Loading -> {
                                Text("Getting censor rating...")
                            }

                            is MovieDetailsUiState.Success -> {
                                MovieInfoItem("Adult", state.movieDetails.adult.toString())
                            }

                            is MovieDetailsUiState.Error -> {
                                Text("Error fetching censor rating.")
                            }
                        }
                    }

                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = 0.5.dp,
                        color = Color.Gray.copy(alpha = 0.2f)
                    )

                    /*
                    ** Genres
                    */
                    Text(
                        text = "Genres",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    when (val state = movieDetailsUiState.value) {
                        is MovieDetailsUiState.Loading -> {
                            Text("Getting genres...")
                        }

                        is MovieDetailsUiState.Success -> {
                            val genres = state.movieDetails.genres
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                genres.forEach { genre ->
                                    Box(
                                        modifier = Modifier.padding(10.dp)
                                    ) {
                                        Text(
                                            text = genre.name,
                                            fontSize = 14.sp,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                        )
                                    }
                                }
                            }
                        }

                        is MovieDetailsUiState.Error -> {
                            Text("Error fetching censor rating.")
                        }
                    }

                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = 0.5.dp,
                        color = Color.Gray.copy(alpha = 0.2f)
                    )

                    /*
                    ** Language
                    */
                    Text(
                        text = "Available in languages",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    when (val state = movieDetailsUiState.value) {
                        is MovieDetailsUiState.Loading -> {
                            Text("Getting language...")
                        }

                        is MovieDetailsUiState.Success -> {
                            Text(
                                text = state.movieDetails.originalLanguage,
                                color = Color.White,
                                fontSize = 14.sp,
                            )
                        }

                        is MovieDetailsUiState.Error -> {
                            Text("Error fetching language.")
                        }
                    }

                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = 0.5.dp,
                        color = Color.Gray.copy(alpha = 0.2f)
                    )

                    /*
                    ** Production Companies
                    */
                    Text(
                        text = "Production Companies",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    when (val state = movieDetailsUiState.value) {
                        is MovieDetailsUiState.Loading -> {
                            Text("Getting language...")
                        }

                        is MovieDetailsUiState.Success -> {
                            val companies = state.movieDetails.productionCompanies
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.padding(vertical = 8.dp)
                            ) {
                                items(companies) { company ->
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.width(70.dp)
                                    ) {
                                        val imageUrl =
                                            "https://image.tmdb.org/t/p/w500${company.logoPath}"
                                        AsyncImage(
                                            model = ImageRequest.Builder(
                                                context = LocalContext.current
                                            ).data(imageUrl).crossfade(true).build(),
                                            contentDescription = company.name,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .size(70.dp)
                                                .clip(CircleShape),
                                        )
                                        Text(
                                            text = company.name,
                                            fontSize = 12.sp,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }

                        is MovieDetailsUiState.Error -> {
                            Text("Error fetching production companies.")
                        }
                    }

                    // Book Tickets Button
//                    StyledButton(
//                        color = ButtonVariants.Primary,
//                        onClick = { /* Handle booking */ },
//                        text = "Book Tickets",
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(vertical = 16.dp)
//                    )
                }
            }
        }
    }
}

@Composable
private fun MovieInfoItem(title: String, value: String) {
    Column {
        Text(
            text = title, color = Color.Gray, fontSize = 12.sp
        )
        Text(
            text = value, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun CastMemberItem(castMember: CastMember) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(70.dp)
    ) {
        Image(
            painter = painterResource(id = castMember.imageResId),
            contentDescription = "Cast member ${castMember.name}",
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

package com.mobile_app.project.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mobile_app.project.MovieScreens
import com.mobile_app.project.model.SearchMoviesUiState

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    movieViewModel: MovieViewModel = hiltViewModel(),
    navController: NavController
) {
    val interactionSource = remember { MutableInteractionSource() }
    var query by remember { mutableStateOf(TextFieldValue("")) }
    val searchMoviesUiState = movieViewModel.searchMoviesUiState.collectAsState()

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = query.text,
                onValueChange = { query = TextFieldValue(it) },
                placeholder = { Text(text = "Search...") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                singleLine = true
            )
            IconButton(
                onClick = {
                    movieViewModel.searchMoviesFromViewModel(query.text)
                }, interactionSource = interactionSource
            ) {
                Icon(
                    imageVector = Icons.Default.Search, contentDescription = "Search Icon"
                )
            }
        }

        Box {
            when (val state = searchMoviesUiState.value) {
                is SearchMoviesUiState.EmptySearch -> {
                    Text(text = "Search any movie you like :)")
                }

                is SearchMoviesUiState.Loading -> {
                    Text(text = "Searching for ${query.text}")
                }

                is SearchMoviesUiState.Success -> {
                    val results = state.searchMovies.results
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 150.dp),
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(results) { movie ->
                            Card(
                                modifier = Modifier.padding(4.dp),
                                onClick = {
                                    movieViewModel.setSelectMovieId(movie.id)
                                    navController.navigate(MovieScreens.Detail.name)
                                },
                                interactionSource = interactionSource
                            ) {
                                val imageUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
                                AsyncImage(
                                    model = ImageRequest.Builder(
                                        context = LocalContext.current
                                    ).data(imageUrl).crossfade(true).build(),
                                    contentDescription = movie.overview,
                                    contentScale = ContentScale.Crop,
                                )
                            }
                        }
                    }

                }

                is SearchMoviesUiState.Error -> {
                    Text("Error searching movies.")
                }
            }

        }
    }
}


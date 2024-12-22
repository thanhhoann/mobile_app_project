package com.mobile_app.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mobile_app.project.components.MovieAppBar
import com.mobile_app.project.screens.HomeScreen
import com.mobile_app.project.screens.SignUp
import com.mobile_app.project.screens.MovieScreen
import com.mobile_app.project.ui.theme.MobileAppProjectTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieApp()
        }
    }
}

enum class MovieScreens(@StringRes val title: Int) {
    Home(title = R.string.home),
    SignUp(title = R.string.signup),
    Detail(title = R.string.movie_detail)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = MovieScreens.valueOf(
        backStackEntry?.destination?.route ?: MovieScreens.Home.name
    )
    MobileAppProjectTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            topBar = {
                MovieAppBar(
                    navController = navController,
                    currentScreenTitle = currentScreen.title,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.popBackStack() }
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = MovieScreens.Home.name,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = MovieScreens.Home.name) {
                    HomeScreen(navController)
                }

                composable(route = MovieScreens.SignUp.name) {
                    SignUp()
                }

                composable(route = MovieScreens.Detail.name) {
                    MovieScreen(navController)
                }
            }
        }
    }
}
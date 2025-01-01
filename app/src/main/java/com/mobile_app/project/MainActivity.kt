package com.mobile_app.project

import android.os.Bundle
import android.util.Log
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mobile_app.project.components.MovieAppBar
import com.mobile_app.project.config.auth.AuthService
import com.mobile_app.project.ui.screens.HomeScreen
import com.mobile_app.project.ui.screens.MovieScreen
import com.mobile_app.project.ui.screens.MovieViewModel
import com.mobile_app.project.ui.screens.SignInScreen
import com.mobile_app.project.ui.screens.SignUp
import com.mobile_app.project.ui.theme.MobileAppProjectTheme


class MainActivity : ComponentActivity() {
    val authService = AuthService()

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieApp(authService)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

}

enum class MovieScreens(@StringRes val title: Int) {
    Home(title = R.string.home),
    SignUp(title = R.string.signup),
    SignIn(title = R.string.signin),
    Detail(title = R.string.movie_detail)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieApp(authService: AuthService) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = MovieScreens.valueOf(
        backStackEntry?.destination?.route ?: MovieScreens.Home.name
    )

    val viewModel: MovieViewModel = viewModel(factory = MovieViewModel.Factory)
    authService.init()
    MobileAppProjectTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            topBar = {
                MovieAppBar(
                    authService = authService,
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
                    HomeScreen(viewModel, navController)
                }

                composable(route = MovieScreens.SignUp.name) {
                    SignUp(authService = authService, navController)
                }

                composable(route = MovieScreens.Detail.name) {
                    MovieScreen(viewModel, navController)
                }

                composable(route = MovieScreens.SignIn.name) {
                    SignInScreen(
                        authService,
                        onSignInSuccess = {
                            navController.navigate(MovieScreens.Home.name) {
                                popUpTo(MovieScreens.Home.name) { inclusive = true }
                            }
                        },
                        navController
                    )
                }
            }
        }
    }
}
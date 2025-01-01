package com.mobile_app.project

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mobile_app.project.components.MovieAppBar
import com.mobile_app.project.config.auth.AuthService
import com.mobile_app.project.ui.screens.HomeScreen
import com.mobile_app.project.ui.screens.MovieScreen
import com.mobile_app.project.ui.screens.MovieViewModel
import com.mobile_app.project.ui.screens.ProfileScreen
import com.mobile_app.project.ui.screens.PromptNameScreen
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

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)

val topLevelRoutes = listOf(
    TopLevelRoute("Home", MovieScreens.Home.name, Icons.Filled.Home),
    TopLevelRoute("Profile", MovieScreens.Profile.name, Icons.Filled.Person)
)

enum class MovieScreens(@StringRes val title: Int) {
    Home(title = R.string.home),
    SignUp(title = R.string.signup),
    SignIn(title = R.string.signin),
    Detail(title = R.string.movie_detail),
    Profile(title = R.string.profile),
    PromptName(title = R.string.your_name)
}

@Composable
fun BottomBar(currentScreens: MovieScreens, navController: NavHostController) {
    BottomNavigation {
        topLevelRoutes.forEach { topLevelRoute ->
            BottomNavigationItem(
                modifier = Modifier.background(Color.Red),
                icon = {
                    Icon(
                        topLevelRoute.icon,
                        contentDescription = topLevelRoute.name,
                        tint = Color.Black,
                    )
                },
                label = { Text(topLevelRoute.name, color = Color.Black) },
                selected = topLevelRoute.name == currentScreens.name,
                onClick = {
                    navController.navigate(topLevelRoute.route) {
                        popUpTo(topLevelRoute.route)
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
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
            },
            bottomBar = {
                if (authService.isLoggedIn && currentScreen != MovieScreens.PromptName) {
                    BottomBar(currentScreen, navController)
                }
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
                            if (authService.getCurrentUser()?.displayName == null) {
                                navController.navigate(MovieScreens.PromptName.name) {
                                    popUpTo(MovieScreens.PromptName.name) { inclusive = true }
                                }
                            } else {
                                navController.navigate(MovieScreens.Home.name) {
                                    popUpTo(MovieScreens.Home.name) { inclusive = true }
                                }
                            }
                        },
                        navController
                    )
                }
                composable(route = MovieScreens.Profile.name) {
                    ProfileScreen(authService, onSignOut = {
                        navController.navigate(MovieScreens.Home.name) {
                            popUpTo(MovieScreens.Home.name) { inclusive = true }
                        }
                    })
                }
                composable(route = MovieScreens.PromptName.name) {
                    PromptNameScreen(authService, navController)
                }
            }
        }
    }
}
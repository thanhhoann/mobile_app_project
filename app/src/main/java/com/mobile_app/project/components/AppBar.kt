package com.mobile_app.project.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mobile_app.project.MovieScreens
import com.mobile_app.project.config.auth.AuthService
import com.mobile_app.project.ui.theme.on_background


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieAppBar(
    authService: AuthService,
    @StringRes currentScreenTitle: Int,
    navController: NavController,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val username = authService.getCurrentUser()?.email?.substringBefore("@")
    CenterAlignedTopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (authService.isLoggedIn && !canNavigateBack) {
                    Text(
                        textAlign = TextAlign.Left,
                        text = "Welcome, $username",
                    )
                } else {
                    Text(
                        textAlign = TextAlign.Left,
                        text = stringResource(id = currentScreenTitle),
                    )
                }
            }
        },
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
            ) {
                if (!canNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = on_background,
                        modifier = Modifier.size(24.dp)
                    )
                    IconButton(
                        onClick = {
                            if (authService.isLoggedIn) {
                                authService.signOut()
                                navController.navigate(MovieScreens.SignIn.name)
                            } else {
                                navController.navigate(MovieScreens.SignIn.name)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = on_background,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }

            }
        },
    )
}
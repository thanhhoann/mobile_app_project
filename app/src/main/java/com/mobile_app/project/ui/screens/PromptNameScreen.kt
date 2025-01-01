package com.mobile_app.project.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.userProfileChangeRequest
import com.mobile_app.project.MovieScreens
import com.mobile_app.project.components.ButtonVariants
import com.mobile_app.project.components.StyledButton
import com.mobile_app.project.components.StyledTextField
import com.mobile_app.project.config.auth.AuthService

@Composable
fun PromptNameScreen(authService: AuthService, navController: NavController) {
    var name by remember { mutableStateOf("") }
    val user = authService.getCurrentUser()
    Column (
        modifier = Modifier.fillMaxHeight().padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        StyledTextField(
            value = name,
            onValueChange = { name = it },
            label = "What shall we call you?"
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Row {
            StyledButton(
                text = "Let's start!",
                onClick = {
                    val profileUpdate = userProfileChangeRequest {
                        displayName = name
                    }

                    user?.updateProfile(profileUpdate)?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navController.navigate(MovieScreens.Home.name) {
                                popUpTo(MovieScreens.Home.name) { inclusive = true }
                            }
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            StyledButton(
                text = "Skip",
                onClick = {
                    navController.navigate(MovieScreens.Home.name) {
                        popUpTo(MovieScreens.Home.name) { inclusive = true }
                    }
                },
                color = ButtonVariants.Secondary
            )
        }
    }
}
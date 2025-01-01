package com.mobile_app.project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.mobile_app.project.config.auth.AuthService

@Composable
fun WhiteTextListItem(text: String, onClick: (() -> Unit), textStyle: TextStyle? = null) {
    TextButton(
        onClick =  onClick,
    ) {
        Text(text = text, style = textStyle ?: MaterialTheme.typography.body1, color = Color.White)
    }
}

@Composable
fun ProfileScreen(authService: AuthService, onSignOut: () -> Unit) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            WhiteTextListItem(
                text = "Account: ${authService.getCurrentUser()?.email}",
                textStyle = TextStyle(
                    color = Color.Red,
                    fontSize = MaterialTheme.typography.h6.fontSize
                ),
                onClick = {}
            )
            WhiteTextListItem(
                text = "Sign out",
                onClick = {
                    authService.signOut()
                    onSignOut()
                }
            )
        }
    }
}
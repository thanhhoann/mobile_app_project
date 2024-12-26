package com.mobile_app.project.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobile_app.project.R
import com.mobile_app.project.components.ButtonVariants
import com.mobile_app.project.components.StyledButton
import com.mobile_app.project.components.StyledTextField


@Composable
fun GoogleButton(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        StyledButton(
            color = ButtonVariants.Secondary,
            onClick = { /*TODO: Handle Google login */ },
            text = "Sign up with Google",
            icon = {
                Icon(
                    painter = painterResource(R.drawable.google_icon),
                    contentDescription = "Google logo"
                )
            },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignUp() {
    Surface {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "Sign Up", style = MaterialTheme.typography.headlineLarge
            )
            StyledTextField(
                value = "", onValueChange = {}, label = "Email"
            )
            StyledTextField(value = "", onValueChange = {}, label = "Password", trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.baseline_visibility_off_24),
                    contentDescription = "Toggle password visibility"
                )
            })
            StyledTextField(
                value = "",
                onValueChange = {},
                label = "Confirm Password",
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.baseline_visibility_off_24),
                        contentDescription = "Toggle password visibility"
                    )
                }
            )
            StyledButton(
                color = ButtonVariants.Primary,
                onClick = { /*TODO: Handle signup */  },
                text = "Sign up",
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Or", style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            GoogleButton(modifier = Modifier.fillMaxWidth())

            Text(
                text = "Already have an account? Login", style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
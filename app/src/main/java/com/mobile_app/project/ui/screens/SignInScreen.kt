package com.mobile_app.project.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mobile_app.project.MovieScreens
import com.mobile_app.project.R
import com.mobile_app.project.components.AnnotatedText
import com.mobile_app.project.components.ButtonVariants
import com.mobile_app.project.components.StyledButton
import com.mobile_app.project.components.StyledTextField
import com.mobile_app.project.components.TextAnnotation
import com.mobile_app.project.view_model.SignInViewModel

@Composable
fun SignInScreen(navController: NavController) {
    val signInViewModel: SignInViewModel = viewModel()
    val signInStates = signInViewModel.signInStates.collectAsState()
    var showPassword by remember { mutableStateOf(false) }

    val errors = signInViewModel.errorMessages.value
    Surface {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "Sign In", style = MaterialTheme.typography.headlineLarge
            )
            StyledTextField(
                value = signInStates.value.email,
                onValueChange = { signInViewModel.updateEmail(it) },
                label = "Email"
            )
            StyledTextField(
                value = signInStates.value.password ?: "",
                onValueChange = { signInViewModel.updatePassword(it) },
                keyboardType = if (showPassword) KeyboardType.Text else KeyboardType.Password,
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                label = "Password",
                trailingIcon = {
                    IconButton(
                        onClick = { showPassword = !showPassword }
                    ) {
                        if (showPassword) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_visibility_24),
                                contentDescription = "Toggle password visibility"
                            )
                        } else {
                            Icon(
                                painter = painterResource(R.drawable.baseline_visibility_off_24),
                                contentDescription = "Toggle password visibility"
                            )
                        }
                    }
                })

            StyledButton(
                color = ButtonVariants.Primary,
                onClick = {
                    signInViewModel.validateSignInFields()
                },
                text = "Sign in",
                modifier = Modifier.fillMaxWidth()
            )
            if (errors.isNotEmpty()) {
                errors.forEach {
                    Text(
                        text = it, style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            AnnotatedText(
                text = "Do not have an account? Sign up now",
                textStyle = MaterialTheme.typography.bodySmall,
                annotations = listOf(
                    TextAnnotation(
                        start = 0,
                        end = 23,
                        style = SpanStyle(
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            fontFamily = MaterialTheme.typography.bodySmall.fontFamily,
                            fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                            color = MaterialTheme.colorScheme.onTertiary,
                        ),
                    ),
                    TextAnnotation(
                        start = 24,
                        end = 35,
                        style = SpanStyle(
                            textDecoration = TextDecoration.Underline,
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            fontFamily = MaterialTheme.typography.bodySmall.fontFamily,
                            fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                            color = MaterialTheme.colorScheme.tertiary,
                        ),
                        onClick = {
                            navController.navigate(MovieScreens.SignUp.name)
                        }
                    )
                )
            )
        }
    }
}
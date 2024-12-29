package com.mobile_app.project.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobile_app.project.R
import com.mobile_app.project.components.ButtonVariants
import com.mobile_app.project.components.StyledButton
import com.mobile_app.project.components.StyledTextField
import com.mobile_app.project.view_model.SignUpViewModel


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
    val signUpViewModel: SignUpViewModel = viewModel()
    val signUpStates = signUpViewModel.signUpStates.collectAsState()
    val errors = signUpViewModel.errorMessages.value

    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }

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
                value = signUpStates.value.email,
                onValueChange = { signUpViewModel.updateEmail(it) },
                label = "Email"
            )
            StyledTextField(
                value = signUpStates.value.password ?: "",
                onValueChange = { signUpViewModel.updatePassword(it) },
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
            StyledTextField(
                value = signUpStates.value.confirmPassword ?: "",
                onValueChange = { signUpViewModel.updateConfirmPassword(it) },
                keyboardType = if (showConfirmPassword) KeyboardType.Text else KeyboardType.Password,
                visualTransformation = if (showConfirmPassword) VisualTransformation.None else PasswordVisualTransformation(),
                label = "Confirm Password",
                trailingIcon = {
                    IconButton(
                        onClick = { showConfirmPassword = !showConfirmPassword }
                    ) {
                        if (showConfirmPassword) {
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
                }
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
            StyledButton(
                color = ButtonVariants.Primary,
                onClick = {
                    signUpViewModel.validateSignUpFields()
                    if (errors.isEmpty()) {
                        signUpViewModel.onSignUp(
                            signUpStates.value.email,
                            signUpStates.value.password ?: ""
                        )
                    }
                },
                text = "Sign up",
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Or", style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )


            GoogleButton(
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Already have an account? Login", style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
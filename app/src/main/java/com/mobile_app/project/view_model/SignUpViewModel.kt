package com.mobile_app.project.view_model


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mobile_app.project.states.UserUiState
import kotlinx.coroutines.flow.MutableStateFlow

class SignUpViewModel : ViewModel() {
    val errorMessages = mutableStateOf<List<String>>(emptyList())

    private val _signUpStates = MutableStateFlow(UserUiState("", "", ""))
    val signUpStates = _signUpStates

    fun updateEmail(email: String) {
        _signUpStates.value = _signUpStates.value.copy(email = email)
    }

    fun updatePassword(password: String) {
        _signUpStates.value = _signUpStates.value.copy(password = password)
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _signUpStates.value = _signUpStates.value.copy(confirmPassword = confirmPassword)
    }

    fun validateSignUpFields() {
        val signUpState = _signUpStates.value
        val errorMessages = mutableListOf<String>()
        if (signUpState.email.isEmpty()) {
            errorMessages.add("Email is required")
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(signUpState.email).matches()) {
            errorMessages.add("Invalid email")
        }
        if (signUpState.password?.isEmpty() == true) {
            errorMessages.add("Password is required")
        }

        if (signUpState.confirmPassword?.isEmpty() == true) {
            errorMessages.add("Confirm password is required")
        } else if (signUpState.password != signUpState.confirmPassword) {
            errorMessages.add("Passwords do not match")
        }
        this.errorMessages.value = errorMessages
    }
}
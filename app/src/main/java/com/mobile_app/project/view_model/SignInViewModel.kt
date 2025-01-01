package com.mobile_app.project.view_model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mobile_app.project.states.UserUiState
import kotlinx.coroutines.flow.MutableStateFlow

class SignInViewModel: ViewModel() {
    val errorMessages = mutableStateOf<List<String>>(emptyList())

    private val _signInStates = MutableStateFlow(UserUiState("", ""))
    val signInStates = _signInStates

    fun updateEmail(email: String) {
        _signInStates.value = _signInStates.value.copy(email = email)
    }

    fun updatePassword(password: String) {
        _signInStates.value = _signInStates.value.copy(password = password)
    }

    fun validateSignInFields() {
        val signInState = _signInStates.value
        val errorMessages = mutableListOf<String>()
        if (signInState.email.isEmpty()) {
            errorMessages.add("Email is required")
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(signInState.email).matches()) {
            errorMessages.add("Invalid email")
        }

        if (signInState.password?.isEmpty() == true) {
            errorMessages.add("Password is required")
        }
        this.errorMessages.value = errorMessages
    }
}
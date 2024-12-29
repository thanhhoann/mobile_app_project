package com.mobile_app.project.view_model


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.mobile_app.project.states.UserUiState
import kotlinx.coroutines.flow.MutableStateFlow

class SignUpViewModel : ViewModel() {
    private lateinit var auth: FirebaseAuth
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

    fun onSignUp(email: String, password: String) {
        auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    Log.d("SignUpViewModel", "createUserWithEmail:success")
                } else {
                    // If sign in fails, display a message to the user.
                    errorMessages.value = listOf("Authentication failed.")
                    Log.w("SignUpViewModel", "createUserWithEmail:failure", task.exception)
                }
            }
    }
}
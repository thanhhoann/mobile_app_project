package com.mobile_app.project.states

data class UserUiState(
    var email: String,
    var password: String? = null,
    var confirmPassword: String? = null
)
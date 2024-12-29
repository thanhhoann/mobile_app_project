package com.mobile_app.project.config.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthService {
    private lateinit var firebaseAuth: FirebaseAuth
    private var currentUser: FirebaseUser? = null

    fun init() {
        firebaseAuth = FirebaseAuth.getInstance()
        currentUser = firebaseAuth.currentUser
    }

    val isLoggedIn: Boolean
        get() = currentUser != null

    val currentUserId: String?
        get() = currentUser?.uid

    fun getCurrentUser(): FirebaseUser? {
        return currentUser
    }

    fun signOut() {
        firebaseAuth.signOut()
        currentUser = null
    }

    fun signUp(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(email, password)
    }
}
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
    }

    val isLoggedIn: Boolean
        get() = currentUser != null

    fun getCurrentUser(): FirebaseUser? {
        currentUser = firebaseAuth.currentUser
        return currentUser
    }

    fun setCurrentUser(user: FirebaseUser?) {
        currentUser = user
    }

    fun signOut() {
        firebaseAuth.signOut()
        currentUser = null
    }

    fun signUp(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(email, password)
    }
    fun signIn(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, password)
    }
}
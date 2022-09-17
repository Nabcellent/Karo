package com.example.karo.pages.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.karo.MainActivity
import com.example.karo.R
import com.example.karo.utils.Helpers
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private var emailInput: EditText? = null
    private var passwordInput: EditText? = null
    private var loginBtn: Button? = null
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailInput = findViewById(R.id.email)
        passwordInput = findViewById(R.id.password)
        loginBtn = findViewById(R.id.login_btn)
        progressBar = findViewById(R.id.progress_bar)

        loginBtn!!.setOnClickListener { login() }
    }

    private fun login() {
        val email = emailInput!!.text.toString()
        val password = passwordInput!!.text.toString()
        val isValidated = validate(email, password)
        if (!isValidated) return

        //  Sign In to Firebase
        changeInProgress(true)
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult?> ->
                changeInProgress(false)
                if (task.isSuccessful) {
                    if (firebaseAuth.currentUser!!.isEmailVerified) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Helpers.showToast(this, "Email not verified.")
                    }
                } else {
                    Helpers.showToast(this, task.exception!!.localizedMessage)
                }
            }
    }

    private fun changeInProgress(inProgress: Boolean) {
        if (inProgress) {
            loginBtn!!.visibility = View.GONE
            progressBar!!.visibility = View.VISIBLE
        } else {
            progressBar!!.visibility = View.GONE
            loginBtn!!.visibility = View.VISIBLE
        }
    }

    private fun validate(email: String, password: String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput!!.error = "Invalid email."
            return false
        }
        if (password.length < 6) {
            passwordInput!!.error = "Password must be more than 6 characters."
            return false
        }
        return true
    }
}
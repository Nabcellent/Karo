package com.example.karo.pages.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.karo.MainActivity;
import com.example.karo.R;
import com.example.karo.utils.Helpers;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText emailInput, passwordInput;
    Button loginBtn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login_btn);
        progressBar = findViewById(R.id.progress_bar);

        loginBtn.setOnClickListener(view -> login());
    }

    void login() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        boolean isValidated = validate(email, password);

        if (!isValidated) return;

        //  Sign In to Firebase
        changeInProgress(true);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            changeInProgress(false);

            if (task.isSuccessful()) {
                if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } else {
                    Helpers.showToast(this, "Email not verified.");
                }
            } else {
                Helpers.showToast(this, task.getException().getLocalizedMessage());
            }
        });
    }

    void changeInProgress(boolean inProgress) {
        if (inProgress) {
            loginBtn.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
            loginBtn.setVisibility(View.VISIBLE);
        }
    }

    boolean validate(String email, String password) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Invalid email.");
            return false;
        }

        if (password.length() < 6) {
            passwordInput.setError("Password must be more than 6 characters.");
            return false;
        }

        return true;
    }
}
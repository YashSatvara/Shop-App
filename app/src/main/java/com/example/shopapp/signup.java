package com.example.shopapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {

    EditText editTextemail, editTextpassword, editTextconfirmpassword;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    Button buttonReg;
    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI elements
        editTextemail = findViewById(R.id.email);
        editTextpassword = findViewById(R.id.password);
        editTextconfirmpassword = findViewById(R.id.confirm_button);
        progressBar = findViewById(R.id.progressBar);
        buttonReg = findViewById(R.id.button_reg);
        text1 = findViewById(R.id.login_text_view1);

        // Register button click listener
        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        // Login text click listener (redirect to login activity)
        text1.setOnClickListener(v -> {
            Intent intent = new Intent(signup.this, login.class);
            startActivity(intent);
            finish();
        });


        // Handle window insets for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void registerUser() {
        // Show progress bar
        progressBar.setVisibility(View.VISIBLE);

        String email = editTextemail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();
        String confirmPassword = editTextconfirmpassword.getText().toString().trim();

        // Validate email
        if (TextUtils.isEmpty(email)) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(signup.this, "Enter a valid email", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate password
        if (TextUtils.isEmpty(password)) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(signup.this, "Enter a valid password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(signup.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new user in Firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE); // Hide progress bar after task completion

                        if (task.isSuccessful()) {
                            // Account successfully created
                            Toast.makeText(signup.this, "Account Created. Please log in.", Toast.LENGTH_SHORT).show();
                            // Redirect to login page
                            Intent intent = new Intent(signup.this, login.class);
                            startActivity(intent);
                            finish(); // Close this activity
                        } else {
                            // Show error message if registration fails
                            String errorMessage = task.getException() != null ? task.getException().getMessage() : "Registration failed.";
                            Toast.makeText(signup.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(signup.this, login.class);
        startActivity(intent);
        finish(); // Close the current activity
    }
}

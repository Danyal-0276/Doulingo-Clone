package com.example.projectmaddoulingoclone;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity11 extends AppCompatActivity {

    private EditText firstName, lastName, email, password, confirmPassword;
    private ImageView passwordToggle1, passwordToggle2;
    private Button signupButton;
    private DatabaseReference databaseReference;
    private boolean isPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main11);

        // Initialize Firebase Database Reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Initialize UI elements
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.Confirmpassword);
        passwordToggle1 = findViewById(R.id.passwordToggle1);
        passwordToggle2 = findViewById(R.id.passwordToggle);
        signupButton = findViewById(R.id.signupButton);

        ImageView backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(v -> onBackPressed());

        // Set up toggle for password visibility
        setupPasswordToggle(password, passwordToggle1, true);
        setupPasswordToggle(confirmPassword, passwordToggle2, false);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });
    }

    private void setupPasswordToggle(EditText passwordField, ImageView toggleButton, boolean isPrimaryPassword) {
        toggleButton.setOnClickListener(v -> {
            ObjectAnimator fadeOut = ObjectAnimator.ofFloat(toggleButton, "alpha", 1f, 0f);
            ObjectAnimator fadeIn = ObjectAnimator.ofFloat(toggleButton, "alpha", 0f, 1f);

            fadeOut.setDuration(200);
            fadeIn.setDuration(200);

            fadeOut.start();
            fadeOut.addListener(new android.animation.AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(android.animation.Animator animation) {
                    if ((isPrimaryPassword && isPasswordVisible) || (!isPrimaryPassword && isConfirmPasswordVisible)) {
                        passwordField.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        toggleButton.setImageResource(R.drawable.vision);
                    } else {
                        passwordField.setTransformationMethod(null);
                        toggleButton.setImageResource(R.drawable.vision_off);
                    }
                    if (isPrimaryPassword) {
                        isPasswordVisible = !isPasswordVisible;
                    } else {
                        isConfirmPasswordVisible = !isConfirmPasswordVisible;
                    }
                    passwordField.setSelection(passwordField.getText().length());
                    fadeIn.start();
                }
            });
        });
    }

    private void saveUserData() {
        String firstNameText = firstName.getText().toString().trim();
        String lastNameText = lastName.getText().toString().trim();
        String emailText = email.getText().toString().trim();
        String passwordText = password.getText().toString().trim();
        String confirmPasswordText = confirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(firstNameText)) {
            Toast.makeText(this, "Please enter your first name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(lastNameText)) {
            Toast.makeText(this, "Please enter your last name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(emailText)) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(passwordText)) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(confirmPasswordText)) {
            Toast.makeText(this, "Please confirm your password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validation for matching passwords
        if (!passwordText.equals(confirmPasswordText)) {
            Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save user data to Firebase
        String userId = databaseReference.push().getKey();
        User user = new User(userId, firstNameText, lastNameText, emailText, passwordText);

        databaseReference.child(userId).setValue(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(MainActivity11.this, "Sign-up successful!", Toast.LENGTH_SHORT).show();
                clearFields();
                Intent intent = new Intent(MainActivity11.this, MainActivity3.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity11.this, "Sign-up failed. Try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFields() {
        firstName.setText("");
        lastName.setText("");
        email.setText("");
        password.setText("");
        confirmPassword.setText("");
    }
}

package com.example.projectmaddoulingoclone;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class MainActivity3 extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1001;
    private boolean isPasswordVisible = false;
    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // Initialize Facebook SDK
        FacebookSdk.sdkInitialize(getApplicationContext());

        // Initialize Firebase Auth and Realtime Database Reference
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Configure Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("504415670661-a04l82e0saul1b155rk7hj7vl500d95m.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        ImageView backArrow = findViewById(R.id.backArrow);
        Button forgotPasswordBtn = findViewById(R.id.forgotPassword);
        EditText passwordInput = findViewById(R.id.passwordInput);
        EditText userNameInput = findViewById(R.id.usernameInput);
        ImageView passwordToggle = findViewById(R.id.passwordToggle);
        Button signBtn = findViewById(R.id.signInButton);
        Button facebookButton = findViewById(R.id.facebookButton);
        Button googleButton = findViewById(R.id.googleButton);

        passwordInput.setTransformationMethod(PasswordTransformationMethod.getInstance());

        backArrow.setOnClickListener(v -> onBackPressed());
        forgotPasswordBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
            startActivity(intent);
        });

        signBtn.setOnClickListener(v -> {
            String emailOrUsername = userNameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (emailOrUsername.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity3.this, "Email/Username and password are required", Toast.LENGTH_SHORT).show();
                return;
            }

            authenticateUser(emailOrUsername, password);
        });

        googleButton.setOnClickListener(v -> signInWithGoogle());
        passwordToggle.setOnClickListener(v -> {
            if (isPasswordVisible) {
                passwordInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
                passwordToggle.setImageResource(R.drawable.vision);
            } else {
                passwordInput.setTransformationMethod(null);
                passwordToggle.setImageResource(R.drawable.vision_off);
            }
            isPasswordVisible = !isPasswordVisible;
            passwordInput.setSelection(passwordInput.getText().length());
        });

        // Initialize Facebook Callback Manager
        mCallbackManager = CallbackManager.Factory.create();

        facebookButton.setOnClickListener(v -> {
            LoginManager.getInstance().logInWithReadPermissions(
                    MainActivity3.this,
                    Arrays.asList("email", "public_profile")
            );
            LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    handleFacebookAccessToken(loginResult.getAccessToken());
                }

                @Override
                public void onCancel() {
                    Toast.makeText(MainActivity3.this, "Facebook login canceled.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException error) {
                    Toast.makeText(MainActivity3.this, "Facebook login failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void authenticateUser(String emailOrUsername, String password) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean isAuthenticated = false;

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String dbEmail = userSnapshot.child("email").getValue(String.class);
                    String dbPassword = userSnapshot.child("password").getValue(String.class);
                    String dbUsername = userSnapshot.child("firstName").getValue(String.class);

                    if ((emailOrUsername.equals(dbEmail) || emailOrUsername.equals(dbUsername)) && password.equals(dbPassword)) {
                        isAuthenticated = true;
                        break;
                    }
                }

                if (isAuthenticated) {
                    Toast.makeText(MainActivity3.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity3.this, MainActivity10.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity3.this, "Invalid email/username or password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity3.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle Facebook Login Result
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        // Handle Google Sign-In Result
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account.getIdToken());
                }
            } catch (ApiException e) {
                Toast.makeText(this, "Google sign-in failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                updateUI(user);
            } else {
                Log.e("GoogleSignIn", "Authentication failed", task.getException());
                Toast.makeText(MainActivity3.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                updateUI(null);
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                updateUI(user);
            } else {
                Toast.makeText(MainActivity3.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                updateUI(null);
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(MainActivity3.this, MainActivity10.class);
            startActivity(intent);
            Toast.makeText(MainActivity3.this, "Authentication successful", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(MainActivity3.this, "Authentication failed. Please log in to continue.", Toast.LENGTH_SHORT).show();
        }
    }
}

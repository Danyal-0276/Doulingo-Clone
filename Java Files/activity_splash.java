package com.example.projectmaddoulingoclone;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class activity_splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        ImageView splashLogo = findViewById(R.id.splash_logo);
        Animation splashAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_animation);

        splashLogo.startAnimation(splashAnimation);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(activity_splash.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 2000);
    }
}

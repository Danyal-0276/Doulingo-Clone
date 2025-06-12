package com.example.projectmaddoulingoclone;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import com.bumptech.glide.Glide;

public class MainActivity5 extends AppCompatActivity {

    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        Button continueBtn = findViewById(R.id.continue_button);
        ImageView backArrow = findViewById(R.id.backArrow);

        backArrow.setOnClickListener(v -> onBackPressed());

        continueBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity5.this, MainActivity6.class);
            startActivity(intent);
        });

        ImageView imageView = findViewById(R.id.duo_gif);
        Glide.with(this).load(R.drawable.giphy).into(imageView);
    }
}

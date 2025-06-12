package com.example.projectmaddoulingoclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ImageView backArrow = findViewById(R.id.backArrow);
        Button signInButton = findViewById(R.id.signInButton);
        Button getStartedButton = findViewById(R.id.getStartedButton);

        backArrow.setOnClickListener(v -> onBackPressed());

        signInButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
            startActivity(intent);
        });

        getStartedButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, MainActivity5.class);
            startActivity(intent);
        });
    }
}

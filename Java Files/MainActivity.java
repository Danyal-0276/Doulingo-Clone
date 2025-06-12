package com.example.projectmaddoulingoclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button getStartedBtn = findViewById(R.id.getStartedBtn);
        Button existingAccountBtn = findViewById(R.id.existingAccountBtn);

        getStartedBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        });

        existingAccountBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity3.class);
            startActivity(intent);
        });
    }
}

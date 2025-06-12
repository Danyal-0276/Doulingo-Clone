package com.example.projectmaddoulingoclone;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        ImageView closeIcon = findViewById(R.id.closeIcon);

        closeIcon.setOnClickListener(v -> finish());
    }
}

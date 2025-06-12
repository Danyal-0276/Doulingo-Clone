package com.example.projectmaddoulingoclone;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity7 extends AppCompatActivity {

    private final String[] fruitList = {"App store", "Friends/family", "X(Twitter)", "Youtube", "News/article/blog", "TikTok", "Instagram"};
    private final int[] flagImage = {R.drawable.playstore, R.drawable.people, R.drawable.xt, R.drawable.youtube, R.drawable.artical, R.drawable.tiktok, R.drawable.instagram};

    private Button continueButton;
    private CustomRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        continueButton = findViewById(R.id.button14); // Default button color

        List<FruitItem> fruitItems = getFruitItems();

        adapter = new CustomRecyclerViewAdapter(this, fruitItems, position -> {
            continueButton.setBackgroundResource(R.drawable.btn2);
            continueButton.setTextColor(Color.parseColor("#142329"));
            Toast.makeText(this, "Clicked: " + fruitItems.get(position).getName(), Toast.LENGTH_SHORT).show();
        });

        recyclerView.setAdapter(adapter);

        ImageView backImage = findViewById(R.id.backArrow);
        backImage.setOnClickListener(v -> onBackPressed());

        continueButton.setOnClickListener(v -> {
            if (adapter.getSelectedPosition() != -1) {
                Intent intent = new Intent(MainActivity7.this, MainActivity8.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please select an option first!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<FruitItem> getFruitItems() {
        List<FruitItem> items = new ArrayList<>();
        for (int i = 0; i < fruitList.length; i++) {
            items.add(new FruitItem(fruitList[i], flagImage[i]));
        }
        return items;
    }
}

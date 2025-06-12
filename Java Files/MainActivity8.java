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

public class MainActivity8 extends AppCompatActivity {

    private final String[] fruitList = {"I'm new", "I know some common words", "I can have basic conversations", "I can talk about various topics"};
    private final int[] flagImage = {R.drawable.sig1, R.drawable.sig2, R.drawable.sig3, R.drawable.sig4};

    private Button continueButton;
    private CustomRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);

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
                Intent intent = new Intent(MainActivity8.this, MainActivity9.class);
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

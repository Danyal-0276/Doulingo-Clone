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

public class MainActivity6 extends AppCompatActivity {

    private final String[] fruitList = {"Chinese", "French", "German", "South-Korean", "English"};
    private final int[] flagImage = {R.drawable.china, R.drawable.france, R.drawable.germany, R.drawable.southkorea, R.drawable.unitedstates};

    private Button continueButton;
    private CustomRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        continueButton = findViewById(R.id.button11); // Default button color

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
                Intent intent = new Intent(MainActivity6.this, MainActivity7.class);
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

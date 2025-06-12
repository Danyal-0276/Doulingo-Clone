package com.example.projectmaddoulingoclone;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.projectmaddoulingoclone.databinding.ActivityMain10Binding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;

public class MainActivity10 extends AppCompatActivity {

    ActivityMain10Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain10Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottom.setItemIconTintList(null);

        if (savedInstanceState == null) {
            replaceFragment(new HomeFragment());
        }

        binding.bottom.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    replaceFragment(new HomeFragment());
                } else if (itemId == R.id.chest) {
                    replaceFragment(new ChestFragment());
                } else if (itemId == R.id.news) {
                    replaceFragment(new NewsFragment());
                } else if (itemId == R.id.profile) {
                    replaceFragment(new ProfileFragment());
                } else if (itemId == R.id.reward) {
                    replaceFragment(new RewardFragment());
                }
                return true;
            }
        });
    }

    // Replace current fragment
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}

package com.example.food.Activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.food.Fragment.CartFragment;
import com.example.food.Fragment.HomeFragment;
import com.example.food.Fragment.ProfileFragment;
import com.example.food.Fragment.SettingFragment;
import com.example.food.Fragment.SupportFragment;
import com.example.food.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnView = findViewById(R.id.bnView);

        bnView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                loadFragment(new HomeFragment(), true);
            } else if (id == R.id.nav_profile) {
                loadFragment(new ProfileFragment(), false);
            } else if (id == R.id.nav_cart) {
                loadFragment(new CartFragment(), false);
            } else if (id == R.id.nav_support) {
                loadFragment(new SupportFragment(), false);
            } else {
                loadFragment(new SettingFragment(), false);
            }

            return true;
        });

        bnView.setSelectedItemId(R.id.nav_home);
    }

    public void loadFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.Container, fragment);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }
}

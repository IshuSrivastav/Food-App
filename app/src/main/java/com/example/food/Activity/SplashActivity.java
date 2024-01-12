package com.example.food.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.food.Fragment.HomeFragment;
import com.example.food.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences pref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                Boolean check = pref.getBoolean("isLoggedIn", false);
                String username = pref.getString("username", "");

                Intent iNext;
                if (check){  //for True (user is Logged In
                    iNext = new Intent(SplashActivity.this, MainActivity.class);
                    iNext.putExtra("username", username); // Pass the username
                }else { //for false (either Firest Time or User is Logged out
                    iNext = new Intent(SplashActivity.this, RegistrationActivity.class);
                }
                startActivity(iNext);
                finish();

//                Intent iHome = new Intent(SpleshActivity.this,MainActivity.class);
//                startActivity(iHome);
//                finish();

            }
        },2000);


    }
}

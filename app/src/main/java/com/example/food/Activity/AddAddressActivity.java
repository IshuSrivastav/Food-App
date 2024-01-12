package com.example.food.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.food.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText name, address, city, postalCode, phoneNumber;
    Button addAddressBtn;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);



        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        //Toolbar
        toolbar = findViewById(R.id.add_address_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        name = findViewById(R.id.ad_name);
        address = findViewById(R.id.ad_address);
        city = findViewById(R.id.ad_city);
        postalCode = findViewById(R.id.ad_code);
        phoneNumber = findViewById(R.id.ad_phone);
        addAddressBtn = findViewById(R.id.ad_add_address);

        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = name.getText().toString().trim();
                String userCity = city.getText().toString().trim();
                String userAddress = address.getText().toString().trim();
                String userCode = postalCode.getText().toString().trim();
                String userNumber = phoneNumber.getText().toString().trim();

                if (!userName.isEmpty() && !userCity.isEmpty() && !userAddress.isEmpty() && !userCode.isEmpty() && !userNumber.isEmpty()) {
                    String finalAddress = userName + ", " + userAddress + ", " + userCity + ", " + userCode + ", " + userNumber;

                    Map<String, String> map = new HashMap<>();
                    map.put("userAddress", finalAddress);

                    firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                            .collection("Address").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful()) {
                                        // Address Added successfully, navigate to AddressActivity
                                        Intent intent = new Intent(AddAddressActivity.this, AddressActivity.class);
                                        intent.putExtra("address", finalAddress); // Pass the address data
                                        startActivity(intent);
                                        finish(); // Finish the current activity to prevent the user from coming back to this screen
                                    } else {
                                        Toast.makeText(AddAddressActivity.this, "Failed to add address", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(AddAddressActivity.this, "Kindly Fill All Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

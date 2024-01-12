package com.example.food.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.food.Models.PopularFoodModel;
import com.example.food.Models.RecommendedModel;
import com.example.food.Models.ShowAllModel;
import com.example.food.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private List<HashMap<String, Object>> itemList;


    Toolbar toolbar;
    private ImageView detailedImg;
    private TextView name, rating, description, price;
    private Button addToCart, buyNow;
    private Button addItems, removeItems;
    private TextView quantity;

    private RecommendedModel recommendedModel;
    private PopularFoodModel popularFoodModel;
    private ShowAllModel showAllModel;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    private int currentQuantity = 1; // Default quantity is 1
    private double currentPrice; // Set the initial price from your model

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        toolbar = findViewById(R.id.detailed_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

        itemList = new ArrayList<>();

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        Object obj = getIntent().getSerializableExtra("detailed");

        if (obj instanceof RecommendedModel) {
            recommendedModel = (RecommendedModel) obj;
        } else if (obj instanceof PopularFoodModel) {
            popularFoodModel = (PopularFoodModel) obj;
        } else if (obj instanceof ShowAllModel) {
            showAllModel = (ShowAllModel) obj;
        }

        detailedImg = findViewById(R.id.detailed_img);
        name = findViewById(R.id.detailed_name);
        rating = findViewById(R.id.my_rating);
        description = findViewById(R.id.detailed_desc);
        price = findViewById(R.id.detailed_price);
        addToCart = findViewById(R.id.add_to_cart);
        buyNow = findViewById(R.id.buy_now);
        addItems = findViewById(R.id.add_item);
        removeItems = findViewById(R.id.remove_item);
        quantity = findViewById(R.id.quantity);

        // Load data into views based on the selected model
        if (recommendedModel != null) {
            displayFoodDetails(recommendedModel);
        } else if (popularFoodModel != null) {
            displayFoodDetails(popularFoodModel);
        } else if (showAllModel != null) {
            displayFoodDetails(showAllModel);
        }


        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, AddressActivity.class);
                if(recommendedModel != null)
                {
                    intent.putExtra("item",recommendedModel);
                }
                if(popularFoodModel != null)
                {
                    intent.putExtra("item",popularFoodModel);
                }
                if(showAllModel != null)
                {
                    intent.putExtra("item",showAllModel);
                }


                startActivity(intent);

            }
        });


        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentQuantity < 10) {
                    currentQuantity++;
                    currentPrice = currentPrice + getProductPrice();
                    updateQuantityAndPrice();
                } else {
                    Toast.makeText(DetailActivity.this, "You can't add more than 10 products.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentQuantity > 1) {
                    currentQuantity--;
                    currentPrice = currentPrice - getProductPrice();
                    updateQuantityAndPrice();
                } else {
                    Toast.makeText(DetailActivity.this, "Quantity cannot be less than 1", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private double getProductPrice() {
        double productPrice = 0.0;
        if (recommendedModel != null) {
            productPrice = recommendedModel.getPrice();
        } else if (popularFoodModel != null) {
            productPrice = popularFoodModel.getPrice();
        } else if (showAllModel != null) {
            productPrice = showAllModel.getPrice();
        }
        return productPrice;
    }

    private void updateQuantityAndPrice() {
        quantity.setText(String.valueOf(currentQuantity));
        price.setText(String.valueOf(currentPrice + " Rs"));
    }

    private void displayFoodDetails(Object foodModel) {
        if (foodModel instanceof RecommendedModel) {
            RecommendedModel model = (RecommendedModel) foodModel;
            Glide.with(getApplicationContext()).load(model.getImg_url()).into(detailedImg);
            name.setText(model.getName());
            rating.setText(model.getRating());
            description.setText(model.getDescription());
            price.setText(String.valueOf(model.getPrice() + " Rs"));
            currentPrice = model.getPrice();
        } else if (foodModel instanceof PopularFoodModel) {
            PopularFoodModel model = (PopularFoodModel) foodModel;
            Glide.with(getApplicationContext()).load(model.getImg_url()).into(detailedImg);
            name.setText(model.getName());
            rating.setText(model.getRating());
            description.setText(model.getDescription());
            price.setText(String.valueOf(model.getPrice() + " Rs"));
            currentPrice = model.getPrice();
        } else if (foodModel instanceof ShowAllModel) {
            ShowAllModel model = (ShowAllModel) foodModel;
            Glide.with(getApplicationContext()).load(model.getImg_url()).into(detailedImg);
            name.setText(model.getName());
            rating.setText(model.getRating());
            description.setText(model.getDescription());
            price.setText(String.valueOf(model.getPrice() + " Rs"));
            currentPrice = model.getPrice();
        }
    }

    private void addToCart() {
        if (auth.getCurrentUser() != null) {
            String saveCurrentTime, saveCurrentDate;
            Calendar calForDate = Calendar.getInstance();

            SimpleDateFormat currentDate = new SimpleDateFormat("MM, dd, yyyy");
            saveCurrentDate = currentDate.format(calForDate.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH, mm, ss");
            saveCurrentTime = currentTime.format(calForDate.getTime());

            HashMap<String, Object> cartMap = new HashMap<>();
            cartMap.put("productName", name.getText().toString());
            cartMap.put("productPrice", currentPrice); // Store current price including quantity
            cartMap.put("quantity", currentQuantity); // Store quantity
            cartMap.put("currentTime", saveCurrentTime);
            cartMap.put("currentDate", saveCurrentDate);

            // Add image URL to cartMap
            String imageUrl = null;
            if (recommendedModel != null) {
                imageUrl = recommendedModel.getImg_url();
            } else if (popularFoodModel != null) {
                imageUrl = popularFoodModel.getImg_url();
            } else if (showAllModel != null) {
                imageUrl = showAllModel.getImg_url();
            }

            if (imageUrl != null) {
                cartMap.put("imageUrl", imageUrl);
            }

            firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                    .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(DetailActivity.this, "Added To Cart", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(DetailActivity.this, "Failed to add to Cart", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            // User is not authenticated, handle this situation (e.g., show a login screen)
            Toast.makeText(DetailActivity.this, "User not authenticated. Please log in.", Toast.LENGTH_SHORT).show();
            // Optionally, navigate the user to the login screen or perform any other necessary actions.
        }
    }
}

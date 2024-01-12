package com.example.food.Fragment;// Your imports go here

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.food.Activity.ShowAllActivity;
import com.example.food.Adapter.CategoryAdapter;
import com.example.food.Adapter.PopularFoodAdapter;
import com.example.food.Adapter.RecommendedAdapter;
import com.example.food.Models.CategoryModel;
import com.example.food.Models.PopularFoodModel;
import com.example.food.Models.RecommendedModel;
import com.example.food.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import java.util.List;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    String user_name ;
    String userImageUrl;

    TextView catShowAll, recommendedShowAll, popularFoodShowAll,display_name;
    ImageView userImage;
    LinearLayout linearLayout;

    ProgressDialog progressDialog;
//Category
    RecyclerView catRecyclerview, recommendedRecyclerview, popularFoodRecyclerview;
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;

    // Recommended RecyclerView

    RecommendedAdapter recommendedAdapter;
    List<RecommendedModel> recommendedModelList;

    // Popular Food

    PopularFoodAdapter popularFoodAdapter;
    List<PopularFoodModel> popularFoodModelList;

    //FireStore Database
    FirebaseFirestore db;
    FirebaseAuth auth;
    private DatabaseReference usersReference;

    public HomeFragment() {
        // Required empty public constructor
    }

    ImageSlider mainslider;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        db = FirebaseFirestore.getInstance();


        progressDialog = new ProgressDialog(getActivity());
        catRecyclerview = view.findViewById(R.id.rec_category);
        recommendedRecyclerview = view.findViewById(R.id.new_product_rec);
        popularFoodRecyclerview = view.findViewById(R.id.popular_rec);
        catShowAll = view.findViewById(R.id.category_see_all);
        recommendedShowAll = view.findViewById(R.id.newProducts_see_all);
        popularFoodShowAll = view.findViewById(R.id.popular_see_all);
        display_name = view.findViewById(R.id.display_name);
        userImage = view.findViewById(R.id.userImage);


        // Initialize Firebase components
        auth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        usersReference = firebaseDatabase.getReference("users");

        // Get the current user ID from Firebase Auth
        String userId = auth.getCurrentUser().getUid();


        // Fetch the user's name and image URL using the user ID
        usersReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // User data found, get user's name and image URL
                    String userName = snapshot.child("name").getValue(String.class);
                    userImageUrl = snapshot.child("mImageUrl").getValue(String.class);

                    // Now you have the user's image URL, you can use it to load the image into ImageView
                    if (userImageUrl != null && !userImageUrl.isEmpty()) {
                        Glide.with(requireContext()).load(userImageUrl).into(userImage);
                    }

                    // Set user's name to the TextView
                    display_name.setText("Hi, " + userName + "!");
                } else {
                    // User data not found
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });






        catShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        recommendedShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        popularFoodShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ShowAllActivity.class);
                startActivity(intent);
            }
        });




        linearLayout = view.findViewById(R.id.home_container);
        linearLayout.setVisibility(View.GONE);
        // Slider setup code remains the same
        //Slider
        mainslider = view.findViewById(R.id.image_slider);

        final List<SlideModel> remoteimages = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("Slider")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        for (DataSnapshot data : datasnapshot.getChildren()) {
                            // Retrieve image URL and title from the database
                            String imageUrl = data.child("url").getValue(String.class);
                            String title = data.child("title").getValue(String.class);

                            // Check for null values in imageUrl and title
                            if (imageUrl != null && title != null) {
                                // Add images to the remoteimages list
                                remoteimages.add(new SlideModel(imageUrl, title, ScaleTypes.FIT));
                            } else {
                                // Log an error if imageUrl or title is null

                                Log.e(TAG, "Image URL or title is null for a slide");
                            }
                        }
                        // Set the image list to the ImageSlider
                        mainslider.setImageList(remoteimages, ScaleTypes.FIT);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle database error (if any)
                        Log.e(TAG, "Error reading data from Firebase: " + error.getMessage());
                    }
                });


        progressDialog.setTitle("Welcome to My Food App");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        // Category RecyclerView setup
        catRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryModelList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(), categoryModelList);
        catRecyclerview.setAdapter(categoryAdapter);

        // Fetching data from Firebase/Firestore
        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CategoryModel categoryModel = document.toObject(CategoryModel.class);
                                categoryModelList.add(categoryModel);
                                categoryAdapter.notifyDataSetChanged();
                                linearLayout.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();
                            }


                        } else {
                            Log.e(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        // Recommended Products  RecyclerView setup

        recommendedRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        recommendedModelList = new ArrayList<>();
        recommendedAdapter = new RecommendedAdapter(getContext(), recommendedModelList);
        recommendedRecyclerview.setAdapter(recommendedAdapter);

        // Fetching data from Firebase/Firestore
        db.collection("Recommended")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                RecommendedModel recommendedModel = document.toObject(RecommendedModel.class);
                                recommendedModelList.add(recommendedModel);
                            }
                            recommendedAdapter.notifyDataSetChanged();
                        } else {
                            Log.e(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


        // Popular Foods  RecyclerView setup // all products

        popularFoodRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        popularFoodModelList = new ArrayList<>();
        popularFoodAdapter = new PopularFoodAdapter(getContext(), popularFoodModelList);
        popularFoodRecyclerview.setAdapter(popularFoodAdapter);

        // Fetching data from Firebase/Firestore
        db.collection("AllProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularFoodModel popularFoodModel = document.toObject(PopularFoodModel.class);
                                popularFoodModelList.add(popularFoodModel);
                            }
                            popularFoodAdapter.notifyDataSetChanged();
                        } else {
                            Log.e(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });



        return view;
    }
}

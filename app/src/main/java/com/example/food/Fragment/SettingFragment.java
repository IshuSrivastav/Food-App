package com.example.food.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.food.Activity.HelpActivity;
import com.example.food.Activity.PaymentMethodsActivity;
import com.example.food.Activity.PrivacyActivity;
import com.example.food.Activity.ShowProfile;
import com.example.food.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingFragment extends Fragment {

    private ImageView userImage;
    private TextView userName, userEmail, Profile, Help,Privacy, Payment;
    private DatabaseReference usersReference;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    public SettingFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        Profile = view.findViewById(R.id.dob);
        Help = view.findViewById(R.id.phone);
        Privacy = view.findViewById(R.id.password);
        Payment = view.findViewById(R.id.upload);

        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ShowProfile.class);
                startActivity(intent);
            }
        });

        Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                startActivity(intent);
            }
        });

        Privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PrivacyActivity.class);
                startActivity(intent);
            }
        });

        Payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PaymentMethodsActivity.class);
                startActivity(intent);
            }
        });

        // Initialize Firebase components
        auth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        usersReference = firebaseDatabase.getReference("users");

        // Initialize the progress dialog
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Welcome to Setting Section");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        // Set initial visibility to GONE for UI elements
        userName = view.findViewById(R.id.name); // Assuming you have a TextView with id 'name' for username
        userEmail = view.findViewById(R.id.email); // Assuming you have a TextView with id 'email' for email
        userImage = view.findViewById(R.id.img); // Assuming you have an ImageView with id 'img' for the profile image
        userName.setVisibility(View.GONE);
        userEmail.setVisibility(View.GONE);

        progressDialog.show(); // Show the progress dialog

        // Get the current user ID from Firebase Auth
        String userId = auth.getCurrentUser().getUid();

        // Fetch the user's data using the user ID
        usersReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss(); // Dismiss the progress dialog
                if (snapshot.exists()) {
                    // User data found, get user's data
                    String userNameText = snapshot.child("name").getValue(String.class);
                    String userEmailText = snapshot.child("email").getValue(String.class);
                    String userImageUrl = snapshot.child("mImageUrl").getValue(String.class); // Assuming the field name is "mImageUrl"

                    // Set user data to TextViews and make them visible
                    userName.setText(userNameText);
                    userEmail.setText(userEmailText);
                    userName.setVisibility(View.VISIBLE);
                    userEmail.setVisibility(View.VISIBLE);

                    // Load user image into the ImageView using Glide
                    Glide.with(requireContext()) // Use requireContext() to get the Fragment's context
                            .load(userImageUrl)
                            .placeholder(R.drawable.profile1) // You can set a placeholder image while the actual image is loading
                            .error(R.drawable.profile1) // You can set an error image if the loading fails
                            .into(userImage);
                } else {
                    // User data not found, handle as needed
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss(); // Dismiss the progress dialog in case of error
                // Handle database error
            }
        });

        return view;
    }
}

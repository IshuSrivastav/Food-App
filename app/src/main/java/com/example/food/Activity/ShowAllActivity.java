package com.example.food.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.denzcoskun.imageslider.models.SlideModel;
import com.example.food.Adapter.ShowAllAdapter;
import com.example.food.Models.ShowAllModel;
import com.example.food.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

public class ShowAllActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ShowAllAdapter showAllAdapter;
    List<ShowAllModel> showAllModelList;

    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        toolbar = findViewById(R.id.show_all_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        String type = getIntent().getStringExtra("type");

        firestore = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.show_all_rec);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        showAllModelList = new ArrayList<>();
        showAllAdapter = new ShowAllAdapter(this, showAllModelList);
        recyclerView.setAdapter(showAllAdapter);



        if(type == null || type.isEmpty())
        {
            firestore.collection("ShowAll")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot doc  : task.getResult().getDocuments()){
                                    ShowAllModel showAllModel = doc.toObject(ShowAllModel.class);
                                    showAllModelList.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    });
        }

        if(type != null && type.equalsIgnoreCase("burger"))
        {
            firestore.collection("ShowAll").whereEqualTo("type","burger")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot doc  : task.getResult().getDocuments()){
                                    ShowAllModel showAllModel = doc.toObject(ShowAllModel.class);
                                    showAllModelList.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    });
        }

        if(type != null && type.equalsIgnoreCase("pie"))
        {
            firestore.collection("ShowAll").whereEqualTo("type","pie")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot doc  : task.getResult().getDocuments()){
                                    ShowAllModel showAllModel = doc.toObject(ShowAllModel.class);
                                    showAllModelList.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    });
        }

        if(type != null && type.equalsIgnoreCase("hotdog"))
        {
            firestore.collection("ShowAll").whereEqualTo("type","hotdog")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot doc  : task.getResult().getDocuments()){
                                    ShowAllModel showAllModel = doc.toObject(ShowAllModel.class);
                                    showAllModelList.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    });
        }

        if(type != null && type.equalsIgnoreCase("pizza"))
        {
            firestore.collection("ShowAll").whereEqualTo("type","pizza")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot doc  : task.getResult().getDocuments()){
                                    ShowAllModel showAllModel = doc.toObject(ShowAllModel.class);
                                    showAllModelList.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    });
        }

        if(type != null && type.equalsIgnoreCase("drinks"))
        {
            firestore.collection("ShowAll").whereEqualTo("type","drinks")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot doc  : task.getResult().getDocuments()){
                                    ShowAllModel showAllModel = doc.toObject(ShowAllModel.class);
                                    showAllModelList.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    });
        }


    }
}





//
//package com.example.food.Activity;
//
//        import androidx.annotation.NonNull;
//        import androidx.appcompat.app.AppCompatActivity;
//        import androidx.recyclerview.widget.GridLayoutManager;
//        import androidx.recyclerview.widget.RecyclerView;
//
//        import android.os.Bundle;
//
//        import com.example.food.Adapter.ShowAllAdapter;
//        import com.example.food.Models.ShowAllModel;
//        import com.example.food.R;
//        import com.google.android.gms.tasks.OnCompleteListener;
//        import com.google.android.gms.tasks.Task;
//        import com.google.firebase.firestore.DocumentSnapshot;
//        import com.google.firebase.firestore.FirebaseFirestore;
//        import com.google.firebase.firestore.QuerySnapshot;
//
//        import java.util.ArrayList;
//        import java.util.List;
//
//public class ShowAllActivity extends AppCompatActivity {
//
//    RecyclerView recyclerView;  // RecyclerView to display items
//    ShowAllAdapter showAllAdapter;  // Adapter for the RecyclerView
//    List<ShowAllModel> showAllModelList;  // List to store retrieved items
//
//    FirebaseFirestore firestore;  // Firestore database instance
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_show_all);  // Set the activity layout
//
//        String type = getIntent().getStringExtra("type");  // Get category type passed from the previous activity
//
//        firestore = FirebaseFirestore.getInstance();  // Initialize Firestore database instance
//
//        recyclerView = findViewById(R.id.show_all_rec);  // Initialize RecyclerView from the layout XML file
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));  // Set GridLayoutManager with 2 columns
//
//        showAllModelList = new ArrayList<>();  // Initialize an empty list to store items
//        showAllAdapter = new ShowAllAdapter(this, showAllModelList);  // Initialize the adapter with the list of items
//        recyclerView.setAdapter(showAllAdapter);  // Set the adapter for the RecyclerView
//
//        // Check if a category type is provided
//        if (type != null && !type.isEmpty()) {
//            fetchItemsByType(type);  // If provided, fetch items of the specified type
//        } else {
//            fetchAllItems();  // If not provided, fetch all items
//        }
//    }
//
//    // Method to fetch items of a specific type from Firestore
//    private void fetchItemsByType(String type) {
//        // Query Firestore collection to get items where 'type' field matches the provided type
//        firestore.collection("ShowAll").whereEqualTo("type", type)
//                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            // Loop through the documents in the query result
//                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
//                                // Convert each document to a ShowAllModel object
//                                ShowAllModel showAllModel = doc.toObject(ShowAllModel.class);
//                                // Add the ShowAllModel object to the list
//                                showAllModelList.add(showAllModel);
//                            }
//                            // Notify the adapter that the data set has changed
//                            showAllAdapter.notifyDataSetChanged();
//                        }
//                    }
//                });
//    }
//
//    // Method to fetch all items from Firestore
//    private void fetchAllItems() {
//        // Query Firestore collection to get all items
//        firestore.collection("ShowAll").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    // Loop through the documents in the query result
//                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {
//                        // Convert each document to a ShowAllModel object
//                        ShowAllModel showAllModel = doc.toObject(ShowAllModel.class);
//                        // Add the ShowAllModel object to the list
//                        showAllModelList.add(showAllModel);
//                    }
//                    // Notify the adapter that the data set has changed
//                    showAllAdapter.notifyDataSetChanged();
//                }
//            }
//        });
//    }
//}

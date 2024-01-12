package com.example.food.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.Activity.AddressActivity;
import com.example.food.Adapter.MyCartAdapter;
import com.example.food.Models.MyCartModel;
import com.example.food.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment implements MyCartAdapter.OnItemClickListener {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private List<MyCartModel> cartModelList;
    private MyCartAdapter cartAdapter;
    private TextView totalPriceText;
    private int totalPrice;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private Button buyNow;


    public CartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        toolbar = view.findViewById(R.id.my_cart_toolbar);
        recyclerView = view.findViewById(R.id.cart_rec);
        totalPriceText = view.findViewById(R.id.textView3);
        buyNow = view.findViewById(R.id.buy_now);

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddressActivity.class);
              //  intent.putintex("totalPrice",totalPrice);
                startActivity(intent);
            }
        });

        // Set toolbar as the ActionBar
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setTitle("My Cart");
//            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    activity.finish();
//                }
//            });
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartModelList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(getContext(), cartModelList);
        recyclerView.setAdapter(cartAdapter);

        cartAdapter.setOnItemClickListener(this);

        fetchCartItems();

        return view;
    }

    private void fetchCartItems() {
        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            cartModelList.clear();
                            totalPrice = 0;
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                String documentId = doc.getId();

                                MyCartModel myCartModel = doc.toObject(MyCartModel.class);
                                myCartModel.setDocumentId(documentId);
                                cartModelList.add(myCartModel);
                                // Calculate total price for each item and sum them up
                                totalPrice += myCartModel.getProductPrice() * myCartModel.getQuantity();
                            }
                            cartAdapter.notifyDataSetChanged();
                            updateTotalPrice();
                        }
                    }
                });
    }


    @Override
    public void onDeleteClick(int position) {
        MyCartModel removedItem = cartModelList.get(position);
        int removedItemTotalPrice = removedItem.getProductPrice() * removedItem.getQuantity();
        totalPrice -= removedItemTotalPrice;

        // Update total price on UI
        updateTotalPrice();

        // Remove item from Firestore
        firestore.collection("AddToCart")
                .document(auth.getCurrentUser().getUid())
                .collection("User")
                .document(removedItem.getDocumentId()) // Assume you have a documentId field in MyCartModel
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Item deleted successfully from Firestore

                        // Now, update the UI
                        cartModelList.remove(position);
                        cartAdapter.notifyItemRemoved(position);
                        cartAdapter.notifyItemRangeChanged(position, cartModelList.size());

                        // Update total price again after removing item
                        updateTotalPrice();
                    }
                });
    }

    private void updateTotalPrice() {
        totalPriceText.setText("Total Price: " + totalPrice + " Rs");
    }
}


package com.example.together.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.together.R;
import com.example.together.adapters.AdapterCartItem;
import com.example.together.adapters.AdapterOrderShop;
import com.example.together.adapters.AdapterOrderedItem;
import com.example.together.models.ModelCartItem;
import com.example.together.models.ModelOrderClient;
import com.example.together.models.ModelOrderItem;
import com.example.together.models.ModelOrderShop;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OrderDetailsClient extends AppCompatActivity {

    private String orderId; // orderTo;

    //ui views
    private TextView orderIdTv, dateTv, orderStatusTv, totalItemsTv, costTv, addressTv;
    private RecyclerView itemsRv;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_client);

        //init views
        orderIdTv = findViewById(R.id.orderIdTv);
        dateTv = findViewById(R.id.dateTv);
        orderStatusTv = findViewById(R.id.orderStatusTv);
        totalItemsTv = findViewById(R.id.totalItemsTv);
        costTv = findViewById(R.id.costTv);
        addressTv = findViewById(R.id.addressTv);
        itemsRv = findViewById(R.id.itemsRv);

        Intent intent = getIntent();
//        orderTo = intent.getStringExtra("orderTo");
        orderId = intent.getStringExtra("orderId");

        firebaseAuth = FirebaseAuth.getInstance();
        loadOrderDetails();
    }

    private void loadOrderDetails() {
        //load order details
//        DatabaseReference ref = FirebaseDatabase.getInstance()
    }

    ArrayList<ModelOrderItem> orderItemList;


//    private void showCartDialog() {
//        //init list
//        orderItemList = new ArrayList<>();
//
//        //inflate cart layout
//        View view = LayoutInflater.from(this).inflate(R.layout.dialog_cart, null);
//        cardItemRv = view.findViewById(R.id.cardItemRv);
//        sTotalTv = view.findViewById(R.id.sTotalTv);
//        checkoutBtn = view.findViewById(R.id.checkoutBtn);
//
//        DocumentReference userDocument = db.collection("clients").document(userId);
//
//        userDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//
//                    if (document.exists()) {
//                        // Check if the "Address" field exists in the document
//                        if (document.contains("Address")) {
//                            // Retrieve the "Address" field as a String
//                            myAddress = document.getString("Address");
//                        }
//                        if (document.contains("Phone Number")) {
//                            myNumber = document.getString("Phone Number");
//                        }
//
//                    }
//                }
//            }
//        });
//
//
//        //dialog
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        //set view to dialog
//        builder.setView(view);
//
//
//        checkoutBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // first validate delivery address
//                if (myAddress.equals("") || myAddress.equals("null")) {
//                    Toast.makeText(Client.this, "Please enter your address in your profile before placing order", Toast.LENGTH_SHORT).show();
//                    return; // don't proceed further
//                }
//                if (myNumber.equals("") || myNumber.equals("null")) {
//                    Toast.makeText(Client.this, "Please enter your phone in your profile before placing order", Toast.LENGTH_SHORT).show();
//                    return; // don't proceed further
//                }
//                if (orderItemList.size() == 0) {
//                    // cart list is empty
//                    Toast.makeText(Client.this, "No item in cart", Toast.LENGTH_SHORT).show();
//                }
//
//                submitOrdersToSellers();
//                submitOrder(); //add the order to DB under the orders collection
//                deleteCartData(); //when confirm the order delete the products from the cart
//
//                //open order details
//                Intent intent = new Intent(Client.this, Client.class);
//                startActivity(intent);
//            }
//        });
//
//        DocumentReference docRef = db.collection("clients").document(userId);
//
//        docRef.collection("cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                orderItemList.clear();
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot cartDocument : task.getResult()) {
//                        //get information
//                        String idProduct = cartDocument.getString("uid");
//                        String pIdProduct = cartDocument.getString("productId");
//                        String nameProduct = cartDocument.getString("productTitle");
//                        String priceProduct = cartDocument.getString("productPriceEach");
//                        String costProduct = cartDocument.getString("productPrice");
//                        String quantityProduct = cartDocument.getString("productQuantity");
//
//                        //update allTotalPrice
//                        allTotalPrice += Double.parseDouble(costProduct);
//
//                        // Use toObject to convert the document snapshot to a ModelProduct object
//                        ModelCartItem modelCartItem = new ModelCartItem(
//                                ""+idProduct,
//                                ""+pIdProduct,
//                                ""+nameProduct,
//                                ""+priceProduct,
//                                ""+costProduct,
//                                ""+quantityProduct);
//
//                        orderItemList.add(modelCartItem);
//
//                    }
//                    //setup adapter
//                    adapterCartItem = new AdapterCartItem(Client.this, orderItemList);
//                    //set adapter
//                    cardItemRv.setAdapter(adapterCartItem);
//                    sTotalTv.setText("₪" + allTotalPrice);
//                }
//            }
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//
//        //reset total price on dialog dismiss
//        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                allTotalPrice = 0.0;
//            }
//        });
//
//    }
}
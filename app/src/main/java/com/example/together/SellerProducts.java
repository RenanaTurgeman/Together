package com.example.together;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nonnull;

public class SellerProducts extends Fragment {

  private EditText searchProductsEt;
  private ImageButton filterProductBtn;
  private TextView filteredProductsTv;
  private RecyclerView productsRv;
  private ArrayList<ModelProduct> productList;
  private AdapterProductSeller adapterProductSeller;
  private FirebaseAuth firebaseAuth;

  FirebaseFirestore db;
//  ///TODO:-----50:00-----
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_distribution_center, container, false);
    searchProductsEt = view.findViewById(R.id.searchProductsEt);
    filterProductBtn = view.findViewById(R.id.filterProductBtn);
    filteredProductsTv = view.findViewById(R.id.filteredProductsTv);
    productsRv = view.findViewById(R.id.productsRv);
    firebaseAuth = FirebaseAuth.getInstance();
    db = FirebaseFirestore.getInstance();



    loadAllProducts();

    //search
    searchProductsEt.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        try {
          adapterProductSeller.getFilter().filter(s);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });

    filterProductBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose Category:")
                .setItems(Constants.productCategories_1, new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                    //get selected item
                    String selected = Constants.productCategories_1[which];
                    filteredProductsTv.setText(selected);
                    if (selected.equals("הכל")) {
                      /////TODO: 55:17------------
                      loadAllProducts();
                    }
                    else {
                      loadFilteredProducts(selected);
                    }
                  }
                })
                .show();
      }
    });

    return view;

  }

  private void loadFilteredProducts(String selected) {
    productList = new ArrayList<>();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    String userID = user.getUid();
    DocumentReference docRef = db.collection("seller").document(userID);

    docRef.collection("products").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
      @Override
      public void onComplete(@NonNull Task<QuerySnapshot> task) {
        if (task.isSuccessful()) {
          for (QueryDocumentSnapshot productDocument : task.getResult()) {

            if (productDocument.contains("productCategory")) {
              String productCategory = productDocument.getString("productCategory");

              // Check if the productCategory matches the selected category
              if (selected.equals(productCategory)) {
                // Use toObject to convert the document snapshot to a ModelProduct object
                ModelProduct modelProduct = productDocument.toObject(ModelProduct.class);
                productList.add(modelProduct);
              }
            }

            // Use toObject to convert the document snapshot to a ModelProduct object
            ModelProduct modelProduct = productDocument.toObject(ModelProduct.class);
            productList.add(modelProduct);

          }
          //setup adapter
          adapterProductSeller = new AdapterProductSeller(getContext(), productList);
          //set adapter
          productsRv.setAdapter(adapterProductSeller);
        }
      }
    });


  }


  private void loadAllProducts() {
    productList = new ArrayList<>();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    String userID = user.getUid();
    DocumentReference docRef = db.collection("seller").document(userID);

    docRef.collection("products").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
      @Override
      public void onComplete(@NonNull Task<QuerySnapshot> task) {
        if (task.isSuccessful()) {
          for (QueryDocumentSnapshot productDocument : task.getResult()) {
            // Use toObject to convert the document snapshot to a ModelProduct object
            ModelProduct modelProduct = productDocument.toObject(ModelProduct.class);
            productList.add(modelProduct);

            }
          //setup adapter
          adapterProductSeller = new AdapterProductSeller(getContext(), productList);
          //set adapter
          productsRv.setAdapter(adapterProductSeller);
          }
      }
    });

  }
}
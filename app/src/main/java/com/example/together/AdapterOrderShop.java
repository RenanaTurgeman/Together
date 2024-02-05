package com.example.together;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AdapterOrderShop extends RecyclerView.Adapter<AdapterOrderShop.HolderOrderShop> {
    FirebaseFirestore db;
    private Context context;
    public ArrayList<ModelOrderShop> orderShopArrayList;

    public AdapterOrderShop(Context context, ArrayList<ModelOrderShop> orderShopArrayList) {
        this.context = context;
        this.orderShopArrayList = orderShopArrayList;
    }

    @NonNull
    @Override
    public HolderOrderShop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View view = LayoutInflater.from(context).inflate(R.layout.row_order_seller, parent, false);
        return new HolderOrderShop(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderOrderShop holder, int position) {
//        get data at the position
        ModelOrderShop modelOrderShop = orderShopArrayList.get(position);
        String orderBy = modelOrderShop.getOrderBy();
        String orderId = modelOrderShop.getOrderId();
        String orderCost = modelOrderShop.getOrderCost();
        String orderStatus = modelOrderShop.getOrderStatus();
        String orderTime = modelOrderShop.getOrderTime();

        //load user info
        loadUserInfo(modelOrderShop, holder);

        //set data
        holder.amountTv.setText("סכום: ₪"+ orderCost);
        holder.statusTv.setText(orderStatus);
        holder.orderIdTv.setText("order ID: "+orderId);
        //change order status text color
        if (orderStatus.equals("בתהליך")) {
            holder.statusTv.setTextColor(context.getResources().getColor(R.color.lavender));
        } else if (orderStatus.equals("הושלמה")) {
            holder.statusTv.setTextColor(context.getResources().getColor(R.color.green));
        } else if (orderStatus.equals("בוטלה")) {
            holder.statusTv.setTextColor(context.getResources().getColor(R.color.red));
        }

        //convert timestamp to proper format
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(orderTime));

        // Format the Calendar instance to a human-readable date string
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(calendar.getTime());

        holder.orderDateTv.setText(formattedDate);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open order details
            }
        });
    }

    private void loadUserInfo(ModelOrderShop modelOrderShop, HolderOrderShop holder) {
        // To load the client phone
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String clientId = modelOrderShop.getOrderBy();
        DocumentReference clientRef = db.collection("clients").document(clientId);

        clientRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Retrieve the "Phone Number" field from the document
                        String phoneNumber = document.getString("PhoneNumber");

                        // Do something with the phoneNumber, for example, set it in your ViewHolder
                        holder.phoneTv.setText(phoneNumber);
                    } else {
                        // Handle the case where the document doesn't exist
                    }
                } else {
                    // Handle potential errors here
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return orderShopArrayList.size();
    }

    //view holder class for row_order_selle.xml

    class HolderOrderShop extends RecyclerView.ViewHolder{
        //ui views for row_order_selle.xml
        private TextView orderIdTv,orderDateTv,phoneTv,amountTv,statusTv;
        private ImageView nextIv;
        public HolderOrderShop(@NonNull View itemView) {
            super(itemView);

            //init ui views
            orderIdTv = itemView.findViewById(R.id.orderIdTv);
            orderDateTv = itemView.findViewById(R.id.orderDateTv);
            phoneTv = itemView.findViewById(R.id.phoneTv);
            amountTv = itemView.findViewById(R.id.amountTv);
            statusTv = itemView.findViewById(R.id.statusTv);
            nextIv = itemView.findViewById(R.id.nextIv);
        }
    }
}

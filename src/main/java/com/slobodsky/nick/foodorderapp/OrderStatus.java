package com.slobodsky.nick.foodorderapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.slobodsky.nick.foodorderapp.Common.Common;
import com.slobodsky.nick.foodorderapp.Model.Request;
import com.slobodsky.nick.foodorderapp.ViewHolder.OrderViewHolder;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;

    public RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;

    DatabaseReference requests;

    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order_status);

        database = FirebaseDatabase.getInstance();

        requests = database.getReference("Requests");

        recyclerView = findViewById(R.id.listOrders);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);

        loadOrders(Common.currentUser.getPhone());
    }

    private void loadOrders(String phone)
    {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(

                Request.class, R.layout.order_layout, OrderViewHolder.class,
                requests.orderByChild("phone").equalTo(phone)
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model,
                                              int position) {

                viewHolder.txtOrderID.setText(adapter.getRef(position).getKey());

                viewHolder.txtOrderStatus.setText(convertCodeToStatus(model.getStatus()));

                viewHolder.txtOrderaddress.setText(model.getAddress());

                viewHolder.txtOrderPhone.setText(model.getPhone());

            }
        };

        recyclerView.setAdapter(adapter);
    }

    private String convertCodeToStatus(String status)
    {
        if (status.equals("0"))
        {
            return "Placed.";
        }
        else if (status.equals("1"))
        {
            return "On its way.";
        }
        else
        {
            return "Shipped.";
        }
    }
}
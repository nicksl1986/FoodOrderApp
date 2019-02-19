package com.slobodsky.nick.foodorderapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.slobodsky.nick.foodorderapp.Interface.ItemClickListener;
import com.slobodsky.nick.foodorderapp.Model.Category;
import com.slobodsky.nick.foodorderapp.Model.Food;
import com.slobodsky.nick.foodorderapp.ViewHolder.FoodViewHolder;
import com.slobodsky.nick.foodorderapp.ViewHolder.MenuViewHolder;
import com.squareup.picasso.Picasso;

public class FoodList extends AppCompatActivity {

    RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;

    DatabaseReference foodList;

    String CategoryID = "";

    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_food_list);

        if (getIntent() != null)
            CategoryID = getIntent().getStringExtra("CategoryID");


        database = FirebaseDatabase.getInstance();

        foodList = database.getReference("Food");

        recyclerView = findViewById(R.id.recycler_food);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        if (!CategoryID.isEmpty() && CategoryID != null)
        {
            loadListFood(CategoryID);
        }
    }

    private void loadListFood(String CategoryID)
    {

        Query query = foodList.orderByChild("MenuID").equalTo(CategoryID);

        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class, R.layout.food_item,
                FoodViewHolder.class, query) {

            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {

                viewHolder.food_name.setText(model.getName());

                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.food_image);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Intent foodDetails = new Intent(FoodList.this,
                                FoodDetails.class);

                        foodDetails.putExtra("FoodID", adapter.getRef(position).getKey());

                        startActivity(foodDetails);
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
    }
}

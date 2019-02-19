package com.slobodsky.nick.foodorderapp;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.slobodsky.nick.foodorderapp.Database.Database;
import com.slobodsky.nick.foodorderapp.Model.Food;
import com.slobodsky.nick.foodorderapp.Model.Order;
import com.squareup.picasso.Picasso;

public class FoodDetails extends AppCompatActivity {

    TextView food_name, food_price, food_description;

    ImageView food_image;

    CollapsingToolbarLayout collapsingToolbarLayout;

    FloatingActionButton btnCart;

    ElegantNumberButton numberButton;

    FirebaseDatabase database;

    DatabaseReference food;

    Food currentFood;

    String FoodID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_food_details);

        database = FirebaseDatabase.getInstance();

        food = database.getReference("Food");

        numberButton = findViewById(R.id.number_button);

        btnCart = findViewById(R.id.btnCart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Database(getBaseContext()).addToCart(new Order(

                        FoodID, currentFood.getName(), numberButton.getNumber(),
                        currentFood.getPrice()
                ));

                Toast.makeText(FoodDetails.this, "Added to cart.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        food_description = findViewById(R.id.food_description);

        food_name = findViewById(R.id.food_name);

        food_price = findViewById(R.id.food_price);

        food_image = findViewById(R.id.image_food);

        collapsingToolbarLayout = findViewById(R.id.collapsing);

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);

        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        if (getIntent() != null)

            FoodID = getIntent().getStringExtra("FoodID");

        if (!FoodID.isEmpty())
        {
            getDetailsFood(FoodID);
        }

    }

    private void getDetailsFood(String FoodID)
    {
        food.child(FoodID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               currentFood = dataSnapshot.getValue(Food.class);

                Picasso.with(getBaseContext()).load(currentFood.getImage()).into(food_image);

                collapsingToolbarLayout.setTitle(currentFood.getName());

                food_price.setText(currentFood.getPrice());

                food_name.setText(currentFood.getName());

                food_description.setText(currentFood.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

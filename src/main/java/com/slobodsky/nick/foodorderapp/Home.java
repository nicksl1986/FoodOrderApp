package com.slobodsky.nick.foodorderapp;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.slobodsky.nick.foodorderapp.Common.Common;
import com.slobodsky.nick.foodorderapp.Interface.ItemClickListener;
import com.slobodsky.nick.foodorderapp.Model.Category;
import com.slobodsky.nick.foodorderapp.Model.Order;
import com.slobodsky.nick.foodorderapp.ViewHolder.MenuViewHolder;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;

    DatabaseReference Category;

    TextView txtFullName;

    RecyclerView recycler_menu;

    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Menu");

        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();

        Category = database.getReference("Category");

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent cartIntent = new Intent(Home.this, Cart.class);

               startActivity(cartIntent);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        txtFullName = headerView.findViewById(R.id.txtFullname);

        txtFullName.setText(Common.currentUser.getName());

        recycler_menu = findViewById(R.id.recycler_menu);

        recycler_menu.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recycler_menu.setLayoutManager(layoutManager);

        loadMenu();

        ActivityCompat.requestPermissions(Home.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

    }

    private void loadMenu()
    {
                adapter = new
                FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,
                        R.layout.menu_item,
                        MenuViewHolder.class, Category) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model,
                                              int position) {

                viewHolder.txtMenuName.setText(model.getName());

                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageView);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Intent foodList = new Intent(Home.this, FoodList.class);

                        foodList.putExtra("CategoryID", adapter.getRef(position).getKey());

                        startActivity(foodList);
                    }
                });

            }
        };

        recycler_menu.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);

        } else {

            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_menu)
        {

        }
        else if (id == R.id.nav_cart)
        {
            Intent cartIntent = new Intent(Home.this, Cart.class);

            startActivity(cartIntent);
        }
        else if (id == R.id.nav_orders)
        {
            Intent orderIntent = new Intent(Home.this, OrderStatus.class);

            startActivity(orderIntent);
        }
        else if (id == R.id.nav_log_out)
        {
            Intent logIntent = new Intent(Home.this, SignIn.class);

            logIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(logIntent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}

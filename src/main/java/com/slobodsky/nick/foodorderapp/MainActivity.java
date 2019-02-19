package com.slobodsky.nick.foodorderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.slobodsky.nick.foodorderapp.Common.Common;
import com.slobodsky.nick.foodorderapp.Model.User;

public class MainActivity extends AppCompatActivity {

    Button btnSignIn, btnSignUp;

    TextView txtSlogan;

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        signInAutoMaticIfPossible();

        setContentView(R.layout.activity_main);

        btnSignIn = findViewById(R.id.btnSignIn);

        btnSignUp = findViewById(R.id.btnSignUp);

        txtSlogan = findViewById(R.id.txtSlogan);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signUp = new Intent(MainActivity.this, SignUp.class);

                startActivity(signUp);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signIn = new Intent(MainActivity.this, SignIn.class);

                startActivity(signIn);

            }
        });

    }

    void signInAutoMaticIfPossible()
    {

        if (SharedPref.sherdInstance().getBoolForKey(this, Strings1.USER_LOGED_IN))
        {
            String json = SharedPref.sherdInstance().loadStringForKey(this,
                    Strings1.USER_INFO);

            Gson gson = new Gson();

            User user= gson.fromJson(json, User.class);

            Common.currentUser = user;

            Intent homeIntent = new Intent(MainActivity.this,
                    Home.class);

            startActivity(homeIntent);

            finish();
        }

    }

}

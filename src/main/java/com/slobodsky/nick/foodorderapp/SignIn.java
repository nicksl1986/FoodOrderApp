package com.slobodsky.nick.foodorderapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.util.Strings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.slobodsky.nick.foodorderapp.Common.Common;
import com.slobodsky.nick.foodorderapp.Model.User;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final EditText edtPhone, edtPassword;

        Button btnSignIn;

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in);

        edtPhone = findViewById(R.id.edtPhone);

        edtPassword = findViewById(R.id.edtPassword);

        btnSignIn = findViewById(R.id.btnSignIn);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog = new ProgressDialog(SignIn.this);

                mDialog.setMessage("Please waiting...");

                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child(edtPhone.getText().toString()).exists())
                        {
                            mDialog.dismiss();

                            User user = dataSnapshot.child(edtPhone.getText().toString()).
                                    getValue(User.class);

                            user.setPhone(edtPhone.getText().toString());

                            if (user.getPassword().equals(edtPassword.getText().toString()))
                            {
                                Intent homeIntent = new Intent(SignIn.this,
                                        Home.class);

                                Common.currentUser = user;

                                startActivity(homeIntent);

                                Gson gson = new Gson();

                                String json = gson.toJson(user);

                                SharedPref.sherdInstance().
                                        saveStringWithKey(SignIn.this,
                                                json,Strings1.USER_INFO);

                                SharedPref.sherdInstance().
                                        saveBoolWithKey(SignIn.this,
                                                true,Strings1.USER_LOGED_IN);


                                finish();
                            }
                            else
                            {
                                Toast.makeText(SignIn.this, "Sign in failed !",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            mDialog.dismiss();

                            Toast.makeText(SignIn.this,
                                    "User doesn't exist in database !",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}

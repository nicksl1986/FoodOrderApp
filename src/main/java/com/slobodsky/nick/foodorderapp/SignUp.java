package com.slobodsky.nick.foodorderapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.slobodsky.nick.foodorderapp.Model.User;

public class SignUp extends AppCompatActivity {

    MaterialEditText edtName, edtPhone, edtPassword;

    Button btnSignUp;

    AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        edtName = findViewById(R.id.edtName);

        edtPhone = findViewById(R.id.edtPhone);

        edtPassword = findViewById(R.id.edtPassword);

        btnSignUp = findViewById(R.id.btnSignUp);

        alert  = new AlertDialog.Builder(this);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtName.length() < 2 || edtName.length() > 24)
                {
                    alert.setTitle("Alert message");

                    alert.setMessage("Username must contain between 2 and 24 characters !");

                    alert.show();
                }

                if (edtPhone.length() < 9)
                {
                    alert.setTitle("Alert message");

                    alert.setMessage("Phone number must contain at least 9 characters !");

                    alert.show();
                }

                if (edtPassword.length() < 6 || edtPassword.length() > 12)
                {
                    alert.setTitle("Alert message");

                    alert.setMessage("Password must contain between 6 and 12 characters !");

                    alert.show();
                }


                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);

                mDialog.setMessage("Please waiting...");

                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child(edtPhone.getText().toString()).exists())
                        {
                            mDialog.dismiss();

                            Toast.makeText(SignUp.this,
                                    "Phone number already exists !",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            mDialog.dismiss();

                            User user = new User(edtName.getText().toString(),
                                    edtPassword.getText().toString());

                            table_user.child(edtPhone.getText().toString()).setValue(user);

                            Toast.makeText(SignUp.this, "Sign up successfully !",
                                    Toast.LENGTH_SHORT).show();

                            finish();
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

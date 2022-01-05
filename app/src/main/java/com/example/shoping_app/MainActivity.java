package com.example.shoping_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoping_app.FrontSection.HomeActivity;
import com.example.shoping_app.Prevalent.Prevalent;
import com.example.shoping_app.Sellers.SellerHome_Activity;
import com.example.shoping_app.Sellers.SellerRegistration;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    Button button1,button2;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    private TextView seller;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1=findViewById(R.id.login_id);
        button2=findViewById(R.id.signup_id);
        seller=findViewById(R.id.Want_seller_id);

        progressDialog=new ProgressDialog(this);


        Paper.init(this);

        databaseReference= FirebaseDatabase.getInstance().getReference("user");
        Paper.init(this);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

        String UserPhoneKey= Paper.book().read(Prevalent.UserPhoneKey);
        String Userpasswordkey=Paper.book().read(Prevalent.UserPasswordKey);

        if (UserPhoneKey !=null && Userpasswordkey !=null)
        {
            if (!UserPhoneKey.isEmpty() && !Userpasswordkey.isEmpty())
            {
               AllowAcces(UserPhoneKey,Userpasswordkey);
                progressDialog.setTitle("Already Login");
                progressDialog.setMessage("Please wait.......");
                progressDialog.show();
            }
        }
        seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, SellerRegistration.class);
                startActivity(intent);
            }
        });

    }

    private void AllowAcces(String number, String password) {


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child(number).exists())
                {
                    Model model=snapshot.child(number).getValue(Model.class);

                    if (model.getMobile().equals(number))
                    {
                        if (model.getPassword().equals(password))
                        {
                            Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(MainActivity.this, HomeActivity.class);
                            Prevalent.currentUser = model;
                            startActivity(intent);
                            progressDialog.dismiss();
                            finish();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Password can't matched", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser =FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!=null)
        {
            Intent intent=new Intent(MainActivity.this, SellerHome_Activity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
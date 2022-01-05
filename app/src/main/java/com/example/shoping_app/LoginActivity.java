package com.example.shoping_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoping_app.AdminSection.AdminOptionPage;
import com.example.shoping_app.FrontSection.HomeActivity;
import com.example.shoping_app.FrontSection.NumberVerifyActivity;
import com.example.shoping_app.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    EditText number,pass;
    Button login;
    TextView admin1,notadmin;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    private CheckBox checkBox;
    private TextView forgotPass;
    private String dbuser="user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        number=findViewById(R.id.mobile_id);
        pass=findViewById(R.id.pass1_id);
        login=findViewById(R.id.mainlogin_id);
        admin1=findViewById(R.id.admin_id);
        notadmin=findViewById(R.id.notadmin_id);
        progressDialog=new ProgressDialog(this);
        forgotPass=findViewById(R.id.forgotPas_idd);

        checkBox=(CheckBox) findViewById(R.id.checkbox_id);
        Paper.init(this);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });

        admin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setText("Admin login");
                notadmin.setVisibility(View.VISIBLE);
                admin1.setVisibility(View.INVISIBLE);
                dbuser="admin";
            }
        });
        notadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setText("User Login");
                notadmin.setVisibility(View.INVISIBLE);
                admin1.setVisibility(View.VISIBLE);
                dbuser="user";
            }
        });
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, NumberVerifyActivity.class);
                startActivity(intent);

            }
        });

    }
    public void LoginUser()
    {
        String NUMBER=number.getText().toString();
        String PASSWORD=pass.getText().toString();

        if (NUMBER.isEmpty())
        {
            number.setError("Number can't be empty");
            number.requestFocus();
        }
        if (PASSWORD.isEmpty())
        {
            pass.setError("enter valid password");
            pass.requestFocus();
        }
        else
        {
            progressDialog.setTitle("Login  Account");
            progressDialog.setMessage("Please wait.......");
            progressDialog.show();
            VerifyUser(NUMBER,PASSWORD);
        }
    }

    private void VerifyUser(String number, String password) {

        if(checkBox.isChecked())
        {
            Paper.book().write(Prevalent.UserPhoneKey,number);
            Paper.book().write(Prevalent.UserPasswordKey,password);
        }

        databaseReference= FirebaseDatabase.getInstance().getReference(dbuser);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child(number).exists())
                {
                    Model model=snapshot.child(number).getValue(Model.class);

                    if (model.getMobile().equals(number))
                    {
                        if (model.getPassword().equals(password)) {


                            if (dbuser.equals("admin"))
                            {
                                Toast.makeText(LoginActivity.this, "Admin Login Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, AdminOptionPage.class);
                                startActivity(intent);
                                progressDialog.dismiss();
                                finish();

                            }


                            if (dbuser.equals("user")) {

                                Toast.makeText(LoginActivity.this, "user Login Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                Prevalent.currentUser=model; //pass user data to home page
                                startActivity(intent);
                                progressDialog.dismiss();
                                finish();
                            }

                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Password can't matched", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }
}
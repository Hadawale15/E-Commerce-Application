package com.example.shoping_app.Sellers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoping_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SellerLogin extends AppCompatActivity {

    private EditText email,password;
    private Button loginButton;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);
        email=findViewById(R.id.Email_edittext33);
        password=findViewById(R.id.password_edittext34);

        loginButton=findViewById(R.id.Seller_login_button35_id);
        firebaseAuth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginMethod();
            }


        });



    }
    private void loginMethod() {
        String Email=email.getText().toString();
        String Pass=password.getText().toString();


          if (Email.isEmpty())
        {
            Toast.makeText(SellerLogin.this, "Please enter valid Email", Toast.LENGTH_SHORT).show();
        }



        else if (Pass.isEmpty() )
        {
            Toast.makeText(SellerLogin.this, "Password must be minimum 6 digit", Toast.LENGTH_SHORT).show();
        }
        else
        {
            progressDialog.setTitle("Seller Login");
            progressDialog.setMessage("Please wait.......");
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        Intent intent=new Intent(SellerLogin.this,SellerHome_Activity.class);
                        startActivity(intent);
                        progressDialog.dismiss();
                        finish();
                    }


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SellerLogin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });

        }



    }
}
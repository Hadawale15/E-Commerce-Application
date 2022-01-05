package com.example.shoping_app.Sellers;

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

import com.example.shoping_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SellerRegistration extends AppCompatActivity {

    private EditText name,email,phone,password,address;
    private Button register;
    private TextView alreadyRegister;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registration);

        name=findViewById(R.id.Name_edittext23);
        email=findViewById(R.id.email_edittext25);
        phone=findViewById(R.id.phone_edittext24);
        password=findViewById(R.id.password_edittext26);
        address=findViewById(R.id.address_edittext27);
        register=findViewById(R.id.Register_button28_id);
        alreadyRegister=findViewById(R.id.AlreadyRegister_button29_id);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("Sellers");
        progressDialog=new ProgressDialog(this);


        alreadyRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SellerRegistration.this,SellerLogin.class);
                startActivity(intent);
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerifyDetails();

            }

        });

    }

    private void VerifyDetails() {
        String SName=name.getText().toString();
        String SEmail=email.getText().toString();
        String SPhone=phone.getText().toString();
        String SPassword=password.getText().toString();
        String SAddress=address.getText().toString();

        if (SName.isEmpty())
        {
            Toast.makeText(SellerRegistration.this, "Please enter valid Name", Toast.LENGTH_SHORT).show();
        }

       else if (SEmail.isEmpty())
        {
            Toast.makeText(SellerRegistration.this, "Please enter valid Email", Toast.LENGTH_SHORT).show();
        }

        else if (SPhone.isEmpty())
        {
            Toast.makeText(SellerRegistration.this, "Please enter valid Phone Number", Toast.LENGTH_SHORT).show();
        }

        else if (SPassword.isEmpty() )
        {
            Toast.makeText(SellerRegistration.this, "Password must be minimum 6 digit", Toast.LENGTH_SHORT).show();
        }

       else if (SAddress.isEmpty())
        {
            Toast.makeText(SellerRegistration.this, "Please enter valid Address", Toast.LENGTH_SHORT).show();
        }
       else
        {
            CreateAccount(SName,SPhone,SEmail,SPassword,SAddress);
            progressDialog.setTitle("Seller Registration");
            progressDialog.setMessage("Please wait.......");
            progressDialog.show();
        }


    }

    private void CreateAccount(String sName, String sPhone, String sEmail, String sPassword, String sAddress) {

        firebaseAuth.createUserWithEmailAndPassword(sEmail,sPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    String SellerUID=firebaseAuth.getCurrentUser().getUid();
                    HashMap<String,Object> hashMap=new HashMap<>();
                    hashMap.put("Seller_Name",sName);
                    hashMap.put("Seller_Phone_No",sPhone);
                    hashMap.put("Seller_Email",sEmail);
                    hashMap.put("Seller_Password",sPassword);
                    hashMap.put("Seller_Address",sAddress);
                    hashMap.put("Seller_UID",SellerUID);

                    databaseReference.child(SellerUID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            
                            if (task.isSuccessful())
                            {
                                Toast.makeText(SellerRegistration.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(SellerRegistration.this, SellerHome_Activity.class);
                                startActivity(intent);
                                progressDialog.dismiss();
                                finish();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(SellerRegistration.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                 progressDialog.dismiss();
                        }
                    });


                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(SellerRegistration.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }


}
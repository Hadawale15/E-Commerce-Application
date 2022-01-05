package com.example.shoping_app.Sellers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoping_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class CutomerDetailsForOrder extends AppCompatActivity {

    private TextView t1,t2,t3,t4,t5,t6;
    private String customerName,customerAddress,customerCity,customerPinCode,customerMobile,customerRegisterMobile,pid,sellerUid;
    private Button reach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutomer_details_for_order);

        t1=findViewById(R.id.Customer_name_id02);
        t2=findViewById(R.id.Customer_add_id03);
        t3=findViewById(R.id.Customer_city_id04);
        t4=findViewById(R.id.Customer_pincode_id05);
        t5=findViewById(R.id.Customer_mobile_id06);
        t6=findViewById(R.id.Customer_RegisterMobile_id07);
        reach=findViewById(R.id.Product_reached_id08);

        customerName=getIntent().getStringExtra("Name");
        customerAddress=getIntent().getStringExtra("Add");
        customerCity=getIntent().getStringExtra("City");
        customerPinCode=getIntent().getStringExtra("pin");
        customerMobile=getIntent().getStringExtra("Mobile");
        customerRegisterMobile=getIntent().getStringExtra("RegisterMobile");
        pid=getIntent().getStringExtra("Pid");
        sellerUid=getIntent().getStringExtra("SellerUID");


        t1.setText("Customer Name: "+customerName);
        t2.setText("Customer Address: "+customerAddress);
        t3.setText("Customer City: "+customerCity);
        t4.setText("Customer PinCode: "+customerPinCode);
        t5.setText("Customer Mobile No: "+customerMobile);
        t6.setText("Customer Register Number: "+customerRegisterMobile);

        reach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("CartList");
                databaseReference.child("AdminView").child(customerRegisterMobile).child("product").child(pid).child("Status").setValue("Reached").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful())
                        {
                               DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference("CartList");
                            databaseReference2.child("SellerView").child(sellerUid).child("product").child(pid).child("Status").setValue("Reached").addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(CutomerDetailsForOrder.this, "Updated Successfully", Toast.LENGTH_SHORT).show();

                                    Intent intent=new Intent(CutomerDetailsForOrder.this,SellerNewOrder.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CutomerDetailsForOrder.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                       }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CutomerDetailsForOrder.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
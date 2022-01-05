package com.example.shoping_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.shoping_app.FrontSection.HomeActivity;
import com.example.shoping_app.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class productDetailsActivivty extends AppCompatActivity {

    private ImageView productImg;
    private TextView selectedPName,selectedPPrize,selectedPDesc;
    private ElegantNumberButton elegantNumberButton;
   private Button addedToCart;
    private String productId,sellerName,sellerUid;
    private String Selected_Imageuri;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details_activivty);

        productImg=findViewById(R.id.Selected_product_Img_id);
        selectedPName=findViewById(R.id.Selected_product_name_id);
        selectedPPrize=findViewById(R.id.Selected_product_prize_id);
        selectedPDesc=findViewById(R.id.Selected_product_Description_id);
        elegantNumberButton=findViewById(R.id.elegant_button_id);
        addedToCart=findViewById(R.id.Selected_product_added_cart_id);

        productId=getIntent().getStringExtra("pid");
        sellerName=getIntent().getStringExtra("SellerUID");
        sellerUid=getIntent().getStringExtra("SellerPhone");
        getProdutDetails(productId);

        addedToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartMethod(sellerUid,sellerName);
            }


        });




    }
    private void getProdutDetails(String productId) {

        databaseReference= FirebaseDatabase.getInstance().getReference("product").child(productId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    productModel productModel=snapshot.getValue(com.example.shoping_app.productModel.class);
                    selectedPName.setText(productModel.getProduct_Name());
                    selectedPPrize.setText(productModel.getProduct_Price());
                    selectedPDesc.setText(productModel.getProduct_Description());
                    Picasso.get().load(productModel.Image_Uri).into(productImg);
                    Selected_Imageuri=productModel.Image_Uri;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void CartMethod(String sellerName1,String sellerUid1) {
        String CurrentDate,CurrentTime;
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat dateFormat=new SimpleDateFormat("MMM dd,yyyy");
        CurrentDate=dateFormat.format(calendar.getTime());

        SimpleDateFormat dateFormat1=new SimpleDateFormat("HH:mm:ss a");
        CurrentTime=dateFormat1.format(calendar.getTime());

         DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference("CartList");
        final HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("pid",productId);
        hashMap.put("Pname",selectedPName.getText().toString());
        hashMap.put("Pprize",selectedPPrize.getText().toString());
        hashMap.put("Pdesc",selectedPDesc.getText().toString());
        hashMap.put("Image",Selected_Imageuri);
        hashMap.put("Discount","");
        hashMap.put("Quantity",elegantNumberButton.getNumber());
        hashMap.put("Time",CurrentTime);
        hashMap.put("Date",CurrentDate);
        hashMap.put("Status","Not Delivered");
        hashMap.put("Seller_Phone_No",sellerName1);
        hashMap.put("Seller_UID",sellerUid1);
        hashMap.put("Confirmation","Pending");

        databaseReference2.child("UserView").child(Prevalent.currentUser.getMobile()).child("product").child(productId).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    databaseReference2.child("AdminView").child(Prevalent.currentUser.getMobile()).child("product").child(productId).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            databaseReference2.child("SellerView").child(sellerUid1).child("product").child(productId).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(productDetailsActivivty.this, "Product Added to cart", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(productDetailsActivivty.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(productDetailsActivivty.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(productDetailsActivivty.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(productDetailsActivivty.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
package com.example.shoping_app.Sellers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoping_app.R;
import com.example.shoping_app.productModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddProductActivity extends AppCompatActivity {

    private String catName, productName, productDesc, productprice,SaveCurrentDate,SaveCurrentTime;
    private TextView cat;
    private ImageView pimage;
    private EditText pname, pdes, pprice;
    private Button addButton;
    private static final int picnumber = 1;
    private Uri picUri;
    private String productRandomKey,downloadImageuri;
    private  StorageReference storageReference;
    private DatabaseReference databaseReference,sellerDatabaseRef;
    private ProgressDialog progressDialog;
    private String sellerName,sellerEmail,sellerPhone,sellerAddress,sellerUid;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        cat = findViewById(R.id.heading_id);
        pname = findViewById(R.id.input_product_name_id);
        pdes = findViewById(R.id.input_product_description_id);
        pprice = findViewById(R.id.input_product_price_id);
        pimage = findViewById(R.id.take_product_pic_id);
        addButton = findViewById(R.id.add_product_button_id);
        progressDialog=new ProgressDialog(this);

        storageReference= FirebaseStorage.getInstance().getReference().child("product Image");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("product");
        sellerDatabaseRef=FirebaseDatabase.getInstance().getReference("Sellers");
        firebaseAuth=FirebaseAuth.getInstance();


          catName=getIntent().getExtras().get("category").toString();

         cat.setText("Category: "+catName);

        pimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakeProductPic();
            }


        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productName = pname.getText().toString();
                productDesc = pdes.getText().toString();
                productprice = pprice.getText().toString();

                if (picUri == null) {
                    Toast.makeText(AddProductActivity.this, "Product Image Mandatory.....", Toast.LENGTH_SHORT).show();
                } else if (productName.isEmpty()) {
                    Toast.makeText(AddProductActivity.this, "Product name Mandatory....", Toast.LENGTH_SHORT).show();
                } else if (productDesc.isEmpty()) {
                    Toast.makeText(AddProductActivity.this, "Product Description Mandatory.....", Toast.LENGTH_SHORT).show();
                } else if (productprice.isEmpty()) {
                    Toast.makeText(AddProductActivity.this, "Product Price Mandatory.....", Toast.LENGTH_SHORT).show();
                } else {
                    storeProductInfo();
                }

            }
        });

        sellerDatabaseRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    sellerName=snapshot.child("Seller_Name").getValue().toString();
                    sellerEmail=snapshot.child("Seller_Email").getValue().toString();
                    sellerPhone=snapshot.child("Seller_Phone_No").getValue().toString();
                    sellerAddress=snapshot.child("Seller_Address").getValue().toString();
                    sellerUid=snapshot.child("Seller_UID").getValue().toString();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddProductActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    //to open gallary
    private void TakeProductPic() {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, picnumber);
    }

    //select picture from galary
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == picnumber && resultCode == RESULT_OK && data != null) {
            picUri = data.getData();
            pimage.setImageURI(picUri);
            Toast.makeText(AddProductActivity.this, "get image", Toast.LENGTH_SHORT).show();
        }

    }
  private void storeProductInfo()
  {
      progressDialog.setTitle("Adding Product");
      progressDialog.setMessage("please wait...");
      progressDialog.show();

      Calendar calendar=Calendar.getInstance();

      SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMM dd,yyyy");
      SaveCurrentDate=simpleDateFormat.format(calendar.getTime());

      SimpleDateFormat simpleTimeFormat=new SimpleDateFormat("HH:mm:ss a");
      SaveCurrentTime =simpleTimeFormat.format(calendar.getTime());

      productRandomKey=SaveCurrentDate+SaveCurrentTime;

      //stored image on firebase storage
      StorageReference filepath=storageReference.child(picUri.getLastPathSegment() + productRandomKey+".Jpg" );

      final UploadTask uploadTask=filepath.putFile(picUri);
      uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
          @Override
          public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
              Toast.makeText(AddProductActivity.this, "Product Image Successfully Stored", Toast.LENGTH_SHORT).show();

              //save image url to firebase database
              Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                  @Override
                  public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                      if (!task.isSuccessful())
                      {
                          throw task.getException();
                      }
                       downloadImageuri=filepath.getDownloadUrl().toString();
                      return filepath.getDownloadUrl();
                       }

              }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                  @Override
                  public void onComplete(@NonNull Task<Uri> task) {
                      if (task.isSuccessful())
                      {
                          downloadImageuri=task.getResult().toString();
                          Toast.makeText(AddProductActivity.this, "product Image Save to to database", Toast.LENGTH_SHORT).show();
                          SaveProductInDatabase();
                      }
                  }
              });
          }
      }).addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
              Toast.makeText(AddProductActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
               progressDialog.dismiss();
          }
      });

  }
  //save product data in firebase database
  private void SaveProductInDatabase()
  {

    // productModel model=new productModel(catName,productRandomKey,SaveCurrentDate,SaveCurrentTime,downloadImageuri,productName,productDesc,productprice);

      HashMap<String,Object> hashMap1=new HashMap<>();
      hashMap1.put("Category_Name",catName);
      hashMap1.put("Product_RandomKey",productRandomKey);
      hashMap1.put("Date",SaveCurrentDate);
      hashMap1.put("Time",SaveCurrentTime);
      hashMap1.put("Image_Uri",downloadImageuri);
      hashMap1.put("Product_Name",productName.toLowerCase());
      hashMap1.put("Product_Description",productDesc);
      hashMap1.put("Product_Price",productprice);
      hashMap1.put("Seller_Name",sellerName);
      hashMap1.put("Seller_Phone_No",sellerPhone);
      hashMap1.put("Seller_Email",sellerEmail);
      hashMap1.put("Seller_Address",sellerAddress);
      hashMap1.put("Seller_UID",sellerUid);
      hashMap1.put("Product_Status","Not Approved");

     databaseReference.child(productRandomKey).setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
         @Override
         public void onComplete(@NonNull Task<Void> task) {
             if (task.isSuccessful())
             {
                 Toast.makeText(AddProductActivity.this, "Product data save in firebase database", Toast.LENGTH_SHORT).show();
                 progressDialog.dismiss();
                 finish();
             }
         }
     }).addOnFailureListener(new OnFailureListener() {
         @Override
         public void onFailure(@NonNull Exception e) {
             Toast.makeText(AddProductActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
             progressDialog.dismiss();
         }
     });



  }
}
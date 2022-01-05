package com.example.shoping_app.FrontSection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoping_app.Prevalent.Prevalent;
import com.example.shoping_app.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

public class SettingActivity extends AppCompatActivity {
    private EditText newName,newMobile,newAddress;
   private ImageView newProfileImg;
    private TextView settingClosebt,settingUpdateBt,settingAddprofbt;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    private  String checker="Not Clicked";
    private Uri imageUri;
    private StorageReference storageReference;
    private StorageTask storageTask;
    private String myurl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        newName=findViewById(R.id.setting_userName_id);
        newMobile=findViewById(R.id.setting_mobileNumber_id);
        newAddress=findViewById(R.id.setting_userAddress_id);
        newProfileImg=findViewById(R.id.setting_ProfileImage_id);
        settingClosebt=findViewById(R.id.setting_close_button_id);
        settingUpdateBt=findViewById(R.id.setting_update_button_id);
        settingAddprofbt=findViewById(R.id.setting_changeProfileButton_id);

        progressDialog=new ProgressDialog(this);

        databaseReference= FirebaseDatabase.getInstance().getReference("user");
        storageReference= FirebaseStorage.getInstance().getReference().child("Profile Picture");

        ShowuserDetails(newName,newMobile,newAddress,newProfileImg);
    }

    private void ShowuserDetails(EditText newName, EditText newMobile, EditText newAddress, ImageView newProfileImg) {
        databaseReference=FirebaseDatabase.getInstance().getReference("user").child(Prevalent.currentUser.getMobile());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.child("image").exists()) {
                        String NAME = snapshot.child("name").getValue().toString();
                        String MOBILE = snapshot.child("mobile").getValue().toString();
                        String ADDRESS = snapshot.child("address").getValue().toString();
                        String IMG = snapshot.child("image").getValue().toString();

                        Picasso.get().load(IMG).into(newProfileImg);
                        newMobile.setText(MOBILE);
                        newName.setText(NAME);
                        newAddress.setText(ADDRESS);


                    }
                    else
                    {
                        Toast.makeText(SettingActivity.this, "No profile Details", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }) ;
        settingAddprofbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker="Clicked";
                CropImage.activity(imageUri)
                        .start(SettingActivity.this);

            }
        });
        settingClosebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        settingUpdateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setTitle("Updating profile");
                progressDialog.setTitle("please wait while updating your Profile");
                progressDialog.show();
                if (checker.equals("Clicked"))
                {
                    SaveProfileAndDetails();
                }
                else if (checker.equals("Not Clicked"))
                {
                    UpdateOnlyDetails();
                }
            }




        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK &&data!=null)
        {
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            imageUri=result.getUri();
            newProfileImg.setImageURI(imageUri);
        }
        else
        {
            Toast.makeText(SettingActivity.this, "Error ! please try again", Toast.LENGTH_SHORT).show();
        }
    }
    private void SaveProfileAndDetails() {
      if (TextUtils.isEmpty(newName.getText().toString()))
      {
          Toast.makeText(SettingActivity.this, "Name is mandatory...", Toast.LENGTH_SHORT).show();
      }
        if (TextUtils.isEmpty(newMobile.getText().toString()))
        {
            Toast.makeText(SettingActivity.this, "Mobile Number is mandatory...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(newAddress.getText().toString()))
        {
            Toast.makeText(SettingActivity.this, "Address is mandatory...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(newName.getText().toString()))
        {
            Toast.makeText(SettingActivity.this, "Name is mandatory...", Toast.LENGTH_SHORT).show();
        }
        if (imageUri!=null)
        {
            StoreImgToStorage();
        }

    }

    private void StoreImgToStorage() {
        final StorageReference stRef=storageReference.child(Prevalent.currentUser.getMobile() + ".jpg");
        final UploadTask uploadTask= stRef.putFile(imageUri);

    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
         @Override
         public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
          Task<Uri> task=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
              @Override
              public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                  if (!task.isSuccessful())
                  {
                      throw task.getException();
                  }
                  String downloaduri=stRef.getDownloadUrl().toString();
                  return stRef.getDownloadUrl();

              }
          }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                 @Override
                 public void onComplete(@NonNull Task<Uri> task)
                 {
                     if (task.isSuccessful())
                     {
                         Uri downloadUrl = task.getResult();
                         myurl = downloadUrl.toString();

                         String Uname=newName.getText().toString();
                         String Umobile=newMobile.getText().toString();
                         String Uaddress=newAddress.getText().toString();

                         HashMap<String,Object> hashMap=new HashMap<>();
                         hashMap.put("name",Uname);
                         hashMap.put("address",Uaddress);
                         hashMap.put("mobile Number",Umobile);
                         hashMap.put("image",myurl);
                         databaseReference=FirebaseDatabase.getInstance().getReference("user");

                         databaseReference.child(Prevalent.currentUser.getMobile()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                             @Override
                             public void onComplete(@NonNull Task<Void> task) {
                                 Toast.makeText(SettingActivity.this, "profile Updated Successfully", Toast.LENGTH_SHORT).show();
                                 Intent intent=new Intent(SettingActivity.this, HomeActivity.class);
                                 startActivity(intent);
                                 finish();
                                 progressDialog.dismiss();
                             }
                         }).addOnFailureListener(new OnFailureListener() {
                             @Override
                             public void onFailure(@NonNull Exception e) {
                                 Toast.makeText(SettingActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                 progressDialog.dismiss();
                             }
                         });



                     }
                     else
                     {

                         Toast.makeText(SettingActivity.this, "Error.", Toast.LENGTH_SHORT).show();
                         progressDialog.dismiss();
                     }


                 }
             });

         }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(SettingActivity.this, "photo not uplaoded", Toast.LENGTH_SHORT).show();
        }
    });

    }

    private void UpdateOnlyDetails() {
        Toast.makeText(SettingActivity.this, "ssssad", Toast.LENGTH_SHORT).show();


        String Uname = newName.getText().toString();
        String Umobile = newMobile.getText().toString();
        String Uaddress = newAddress.getText().toString();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", Uname);
        hashMap.put("address", Uaddress);
        hashMap.put("mobile Number", Umobile);
        databaseReference = FirebaseDatabase.getInstance().getReference("user");

        databaseReference.child(Prevalent.currentUser.getMobile()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(SettingActivity.this, "profile Updated Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SettingActivity.this, HomeActivity.class);
                startActivity(intent);
                progressDialog.dismiss();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SettingActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


    }




}

package com.example.shoping_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ResetPassword extends AppCompatActivity {

    private TextView textView;
    private EditText pass1,pass2;
    private Button reset;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        databaseReference= FirebaseDatabase.getInstance().getReference("user");

        textView=findViewById(R.id.a11);
        pass1=findViewById(R.id.reset_pass_id1);
        pass2=findViewById(R.id.confirm_reset_pass_id2);
        reset=findViewById(R.id.reset_pass_button_id);


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetmethod();
            }
        });





      //  Intent intent=getIntent();
        //String num=intent.getStringExtra("enterMobile").toString();
        //Toast.makeText(ResetPassword.this, num, Toast.LENGTH_SHORT).show();
        //textView.setText(num);
    }

    private void resetmethod() {

        String NewPass1=pass1.getText().toString();
        String NewPass2=pass2.getText().toString();

        if (TextUtils.isEmpty(NewPass1))
        {
            Toast.makeText(ResetPassword.this, "enter valid password", Toast.LENGTH_SHORT).show();
        }
       else if (TextUtils.isEmpty(NewPass2))
        {
            Toast.makeText(ResetPassword.this, "enter valid password", Toast.LENGTH_SHORT).show();
        }
       else
        {
            if (NewPass1.equals(NewPass2))
            {
                changeMethode(NewPass2);
            }

        }

    }

    private void changeMethode(String newPass2) {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("777").exists())
                {
                    HashMap<String,Object> hashMap=new HashMap<>();
                    hashMap.put("password",newPass2);

                    databaseReference.child("777").updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(ResetPassword.this, "password Reset Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ResetPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(ResetPassword.this, "Don't have any account related to this no", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ResetPassword.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}
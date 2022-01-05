package com.example.shoping_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private EditText name,mobile,pass1,pass2,email;
    private Button createAcc;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name=findViewById(R.id.Register_username_id);
        mobile=findViewById(R.id.Register_mobile_id);
        email=findViewById(R.id.Register_Email_id);
        pass1=findViewById(R.id.Register_pass1_id);
        pass2=findViewById(R.id.Register_pass2_id);
        createAcc=findViewById(R.id.createAcc_id);
         databaseReference= FirebaseDatabase.getInstance().getReference("user");
         firebaseAuth=FirebaseAuth.getInstance();
         progressDialog=new ProgressDialog(this);


         createAcc.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 CreateAccount();
             }


         });
    }
    private void CreateAccount() {
        String Name=name.getText().toString();
        String Mobile=mobile.getText().toString();
        String Email=email.getText().toString();
        String Password1=pass1.getText().toString();
        String Password2=pass2.getText().toString();

        if (Name.isEmpty())
        {
            name.setError("Number can't be empty");
            name.requestFocus();
        }
        if (Mobile.isEmpty())
        {
            mobile.setError("Number can't be empty");
            mobile.requestFocus();
        }
        if (Email.isEmpty())
        {
            email.setError("Email id can't be empty");
            email.requestFocus();
        }
        if (Password1.isEmpty())
        {
            pass1.setError("enter valid password");
            pass1.requestFocus();
        }
        if (!Password2.equals(Password1))
        {
          pass2.setError("password can not matched");
          pass2.requestFocus();
        }
        else
        {
            CheckNumber(Name,Mobile,Email,Password2);
            progressDialog.setTitle("Creating Account");
            progressDialog.setMessage("Please wait while Your Account has been created");
            progressDialog.show();
        }

    }

    private void CheckNumber(String name1,String mobile1,String email,String password1  ) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!snapshot.child(mobile1).exists())
                {
                    HashMap<String,Object> userdataMap=new HashMap<>();
                    userdataMap.put("mobile",mobile1);
                    userdataMap.put("email",email);
                    userdataMap.put("name",name1);
                    userdataMap.put("password",password1);



                 //   Model model=new Model(name1,mobile1,password1);
                    firebaseAuth.createUserWithEmailAndPassword(email,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            databaseReference.child(mobile1).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        progressDialog.dismiss();
                                        Toast.makeText(SignUpActivity.this, "Account created Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else
                {

                    Toast.makeText(SignUpActivity.this, "Already used Mobile Number! please try with another Number", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SignUpActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}
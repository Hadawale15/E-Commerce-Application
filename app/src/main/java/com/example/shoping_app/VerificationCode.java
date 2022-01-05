package com.example.shoping_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerificationCode extends AppCompatActivity {

    EditText enterOtp;
    Button next;
   private String phoneNo,otpid;
    FirebaseAuth mAuth;
    private String Mob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);


         Intent intent=getIntent();

         Mob=intent.getStringExtra("mobile").toString();

        enterOtp=findViewById(R.id.enter_otp_id);
        next=findViewById(R.id.next_id);

        mAuth=FirebaseAuth.getInstance();

        phoneNo=getIntent().getStringExtra("mobile").toString();

        initiateOTP();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enterOtp.getText().toString().isEmpty())
                {
                    Toast.makeText(VerificationCode.this, "can't Empty", Toast.LENGTH_SHORT).show();
                }
                else  if (enterOtp.getText().toString().length()!=6){
                    Toast.makeText(VerificationCode.this, "invald", Toast.LENGTH_SHORT).show();
                }
                else {
                    PhoneAuthCredential phoneAuthCredential= PhoneAuthProvider.getCredential(otpid,enterOtp.getText().toString());
                    signInWithPhoneAuthCredential(phoneAuthCredential);
                }
            }
        });
    }

    private void initiateOTP() {



        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNo)       // Phone number to verify
                        .setTimeout(80L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                otpid=s;

                                Toast.makeText(VerificationCode.this, "sent", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                                Toast.makeText(VerificationCode.this, "verify", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(VerificationCode.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Intent intent=new Intent(VerificationCode.this,ResetPassword.class);
                            intent.putExtra("enterMobile",Mob);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(VerificationCode.this, "error", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }



}
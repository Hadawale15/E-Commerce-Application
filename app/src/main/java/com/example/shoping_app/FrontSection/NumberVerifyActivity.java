package com.example.shoping_app.FrontSection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.shoping_app.R;
import com.example.shoping_app.VerificationCode;
import com.hbb20.CountryCodePicker;

public class NumberVerifyActivity extends AppCompatActivity {

    EditText no;
    CountryCodePicker ccp;
    Button get;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_verify);

        no=findViewById(R.id.number_txt_id);
        get=findViewById(R.id.get_otp_id);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(no);

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NumberVerifyActivity.this, VerificationCode.class);
                intent.putExtra("mobile",ccp.getFullNumberWithPlus().replace(" ",""));
                startActivity(intent);
            }
        });


    }
}
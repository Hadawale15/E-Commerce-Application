package com.example.shoping_app.AdminSection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.shoping_app.MainActivity;
import com.example.shoping_app.R;

public class AdminOptionPage extends AppCompatActivity {

    private TextView check,view,log,maintain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_option_page);
        check=findViewById(R.id.checkAndApproveProduct_id);
        view=findViewById(R.id.viewOrd_id);
        log=findViewById(R.id.logoutAd_id);
        maintain=findViewById(R.id.maintain_product_text_id);

//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(AdminOptionPage.this, ProductCatagory.class);
//                startActivity(intent);
//            }
//        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminOptionPage.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminOptionPage.this, AdminNewOrderActivity.class);
                startActivity(intent);
            }
        });
        maintain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminOptionPage.this, MaintainProduct.class);
                startActivity(intent);
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent=new Intent(AdminOptionPage.this, ProductApprovement_Activity.class);
                 startActivity(intent);

            }
        });
    }
}
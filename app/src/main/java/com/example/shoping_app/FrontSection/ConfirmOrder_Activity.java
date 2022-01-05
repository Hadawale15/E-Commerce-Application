package com.example.shoping_app.FrontSection;

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

import com.example.shoping_app.Prevalent.Prevalent;
import com.example.shoping_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmOrder_Activity extends AppCompatActivity {

    private EditText confirmName,confirmMobile,confirmAdd,confirmCity,confirmPin;
    private Button confirmButton;
    TextView tAmountTextView;
    private  String TotalAmount;
   private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        confirmName=findViewById(R.id.Confirm_Order_Name_id);
        confirmMobile=findViewById(R.id.Confirm_Order_Mobile_id);
        confirmAdd=findViewById(R.id.Confirm_Order_Addr_id);
        confirmCity=findViewById(R.id.Confirm_Order_City_id);
        confirmPin=findViewById(R.id.Confirm_Order_Pin_id);
        confirmButton=findViewById(R.id.Confirm_Order_Button_id);
        tAmountTextView=findViewById(R.id.Confirm_Order_totalAmount_id);
        TotalAmount=getIntent().getStringExtra("totalAmount");
        tAmountTextView.setText("TotalAmount: " +TotalAmount);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();
            }


        });




    }
    private void Check() {

        if (TextUtils.isEmpty(confirmName.getText().toString()))
        {
            Toast.makeText(ConfirmOrder_Activity.this, "Name is Mandatory", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(confirmMobile.getText().toString()))
        {
            Toast.makeText(ConfirmOrder_Activity.this, "Mobile is Mandatory", Toast.LENGTH_SHORT).show();
        }
       else if (TextUtils.isEmpty(confirmAdd.getText().toString()))
        {
            Toast.makeText(ConfirmOrder_Activity.this, "Address is Mandatory", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(confirmCity.getText().toString()))
        {
            Toast.makeText(ConfirmOrder_Activity.this, "Name of city is Mandatory", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(confirmPin.getText().toString()))
        {
            Toast.makeText(ConfirmOrder_Activity.this, "Pin code  is Mandatory", Toast.LENGTH_SHORT).show();
        }
        else {
            ConfirmOrder();
        }
    }

    private void ConfirmOrder() {
        String CurrentDate1,CurrentTime1;
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat dateFormat=new SimpleDateFormat("MMM dd,yyyy");
        CurrentDate1=dateFormat.format(calendar.getTime());

        SimpleDateFormat dateFormat1=new SimpleDateFormat("HH:mm:ss a");
        CurrentTime1=dateFormat1.format(calendar.getTime());

        databaseReference= FirebaseDatabase.getInstance().getReference("Orders");
        HashMap<String,Object> map=new HashMap<>();
        map.put("Date",CurrentDate1);
        map.put("Time",CurrentTime1);
        map.put("Name",confirmName.getText().toString());
        map.put("Mobile",confirmMobile.getText().toString());
        map.put("Address",confirmAdd.getText().toString());
        map.put("City",confirmCity.getText().toString());
        map.put("PinCode",confirmPin.getText().toString());
        map.put("TotalAmount",TotalAmount);
        map.put("CurrentState","Not Shipped");
        map.put("Confirmation","done");
        map.put("RegisterMobileNo",Prevalent.currentUser.getMobile());

        databaseReference.child(Prevalent.currentUser.getMobile()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("CartList").child("UserView").child(Prevalent.currentUser.getMobile());
                    databaseReference1.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {





                                Toast.makeText(ConfirmOrder_Activity.this, "Order Placed Successfully Place", Toast.LENGTH_SHORT).show();
                                 Intent intent11=new Intent(ConfirmOrder_Activity.this, HomeActivity.class);
                                 //intent11.putExtra("id1","done");
                                 intent11.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                 startActivity(intent11);
                                 finish();
                             }
                         }
                     }).addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             Toast.makeText(ConfirmOrder_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                         }
                     });
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
@Override
public void onFailure(@NonNull Exception e) {
        Toast.makeText(ConfirmOrder_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        });



        }
}
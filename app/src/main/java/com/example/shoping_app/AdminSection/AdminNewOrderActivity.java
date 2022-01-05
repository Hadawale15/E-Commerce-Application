package com.example.shoping_app.AdminSection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoping_app.AllModel.AdminOrderedProductModel;
import com.example.shoping_app.R;
import com.example.shoping_app.ViewHolder.Ad_oreder_view_holder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminNewOrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference,databaseReference1;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_order);
        recyclerView=findViewById(R.id.new_orders_recycle_id);
        layoutManager= new LinearLayoutManager(AdminNewOrderActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


       ShoWOrderMethod();

    }

    private void ShoWOrderMethod() {


        try {
            databaseReference = FirebaseDatabase.getInstance().getReference("Orders");

            FirebaseRecyclerOptions<AdminOrderedProductModel> options = new FirebaseRecyclerOptions.Builder<AdminOrderedProductModel>()
                    .setQuery(databaseReference, AdminOrderedProductModel.class).build();

            FirebaseRecyclerAdapter<AdminOrderedProductModel, Ad_oreder_view_holder> adapter11 = new FirebaseRecyclerAdapter<AdminOrderedProductModel, Ad_oreder_view_holder>(options) {
                @NonNull
                @Override
                public Ad_oreder_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.neworder_singlerow_layout, parent, false);
                    Ad_oreder_view_holder holderAd = new Ad_oreder_view_holder(view);
                    return holderAd;
                }

                @Override
                protected void onBindViewHolder(@NonNull Ad_oreder_view_holder holder1, int position, @NonNull AdminOrderedProductModel modelAd) {
                    holder1.custname.setText("Customer Name: " + modelAd.getName());
                    holder1.custmob.setText("MobileNo: " + modelAd.getMobile());
                    holder1.custadd.setText("Address: " + modelAd.getAddress() + "," + modelAd.getCity());
                    holder1.custpincode.setText("PinCode: " + modelAd.getPinCode());
                    holder1.prodprice.setText("TotalAmount: Rs " + modelAd.getTotalAmount());
                    holder1.custdate.setText(" Ordered Date,Time: " + modelAd.getDate() + "," + modelAd.getTime());

                    holder1.showProdB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String UID = getRef(holder1.getAdapterPosition()).getKey();
                            String customerName=modelAd.getName();
                            String customerAddress=modelAd.getAddress();
                            String customerCity=modelAd.getCity();
                            String customerPinCode=modelAd.getPinCode();
                            String customerMobile=modelAd.getMobile();
                            String customerRegisterMob=modelAd.getRegisterMobileNo();

                            Intent intent = new Intent(AdminNewOrderActivity.this, ProductOfOrderedActivity.class);
                            intent.putExtra("Uid", UID);
                            intent.putExtra("CutomerName",customerName);
                            intent.putExtra("CutomerAddress",customerAddress);
                            intent.putExtra("CutomerCity",customerCity);
                            intent.putExtra("CutomerPinCode",customerPinCode);
                            intent.putExtra("CutomerMobile",customerMobile);
                            intent.putExtra("CustomerRegisterMobile",customerRegisterMob);

                            startActivity(intent);
                        }
                    });

                    holder1.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            CharSequence ans[] = new CharSequence[]
                                    {
                                            "Yes", "No"
                                    };

                            AlertDialog.Builder builder = new AlertDialog.Builder(AdminNewOrderActivity.this);
                            builder.setTitle("Have you shifted this order");
                            builder.setItems(ans, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    if (which == 0) {
                                        String UID = getRef(holder1.getAdapterPosition()).getKey();
                                            RemoveOrder(UID);


                                    } else {
                                        finish();
                                    }

                                }


                            });
                            builder.show();
                        }
                    });
                }
            };

            recyclerView.setAdapter(adapter11);
            adapter11.startListening();

        } catch (Exception e) {
            Toast.makeText(AdminNewOrderActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void RemoveOrder(String uid) {
        databaseReference.child(uid).removeValue();

    }
}
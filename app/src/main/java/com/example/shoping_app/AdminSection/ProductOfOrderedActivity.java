package com.example.shoping_app.AdminSection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoping_app.AllModel.OrderedProductModel;
import com.example.shoping_app.R;
import com.example.shoping_app.ViewHolder.OrderedProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ProductOfOrderedActivity extends AppCompatActivity {

    private String UserId = "",CusName,CusAddress,CusCity,CusPin,CusMobile,CustRegisterMobile;
    private String sellerUID;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_of_ordered);

        recyclerView = findViewById(R.id.Order_products_recycle_id2);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        UserId = getIntent().getStringExtra("Uid").toString();
        CusName=getIntent().getStringExtra("CutomerName").toString();
        CusAddress=getIntent().getStringExtra("CutomerAddress").toString();
        CusCity=getIntent().getStringExtra("CutomerCity").toString();
        CusPin=getIntent().getStringExtra("CutomerPinCode").toString();
        CusMobile=getIntent().getStringExtra("CutomerMobile").toString();
        CustRegisterMobile=getIntent().getStringExtra("CustomerRegisterMobile").toString();



        databaseReference = FirebaseDatabase.getInstance().getReference().child("CartList").child("AdminView").child(UserId).child("product");

        showProductMethod();

        FirebaseRecyclerOptions<OrderedProductModel> option = new FirebaseRecyclerOptions.Builder<OrderedProductModel>()
                .setQuery(databaseReference.orderByChild("Status").equalTo("Not Delivered"), OrderedProductModel.class).build();


        FirebaseRecyclerAdapter<OrderedProductModel, OrderedProductViewHolder> adapter = new FirebaseRecyclerAdapter<OrderedProductModel, OrderedProductViewHolder>(option) {
            @NonNull
            @Override
            public OrderedProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderedproduct_single_row_view, parent, false);
                OrderedProductViewHolder holder = new OrderedProductViewHolder(view);
                return holder;
            }

            @Override
            protected void onBindViewHolder(@NonNull OrderedProductViewHolder holder, int i, @NonNull OrderedProductModel productsModel) {
                holder.pName2.setText(productsModel.getPname());
                holder.pPrize2.setText(productsModel.getPprize());
                holder.pDesc2.setText(productsModel.getPdesc());
                holder.pQty2.setText(productsModel.getQuantity());
                Picasso.get().load(productsModel.getImage()).into(holder.pImg2);

                //sellerUID=productsModel.getSeller_UID();


                String productId = productsModel.getPid();
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        sellerUID=productsModel.getSeller_UID();
                        CharSequence ans[] = new CharSequence[]
                                {
                                        "Yes", "No"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(ProductOfOrderedActivity.this);
                        builder.setTitle("Want to shift this product");
                        builder.setItems(ans, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    databaseReference.child(productId).child("Status").setValue("Delivered").addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference("CartList").child("SellerView").child(sellerUID);
                                                databaseReference2.child("product").child(productId).child("Status").setValue("Delivered").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        HashMap<String,Object> map=new HashMap<>();
                                                        map.put("CutomerName",CusName);
                                                        map.put("CustomerAddress",CusAddress);
                                                        map.put("CustomerCity",CusCity);
                                                        map.put("CutomerPinCode",CusPin);
                                                        map.put("CutomerMobile",CusMobile);
                                                        map.put("CustomerRegisterMobile",CustRegisterMobile);

                                                        databaseReference2.child("product").child(productId).updateChildren(map);
                                                        Toast.makeText(ProductOfOrderedActivity.this, "set", Toast.LENGTH_SHORT).show();

                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(ProductOfOrderedActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ProductOfOrderedActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                            }
                        });
                        builder.show();
                    }
                });

            }


        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }


    private void showProductMethod() {


    }

}
    /*









     */
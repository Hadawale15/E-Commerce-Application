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

import com.example.shoping_app.R;
import com.example.shoping_app.ViewHolder.MyViewHolder;
import com.example.shoping_app.productModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ProductApprovement_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_approvement);

        recyclerView=findViewById(R.id.approve_product_recycle_id);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ProductApprovement_Activity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        databaseReference= FirebaseDatabase.getInstance().getReference("product");


    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<productModel> options21=new FirebaseRecyclerOptions.Builder<productModel>()
                .setQuery(databaseReference.orderByChild("Product_Status").equalTo("Not Approved") ,productModel.class).build();

        FirebaseRecyclerAdapter<productModel, MyViewHolder> adapter21=new FirebaseRecyclerAdapter<productModel, MyViewHolder>(options21) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int i, @NonNull productModel productModel) {
                holder.productName.setText(productModel.getProduct_Name());
                holder.productPrice.setText(productModel.getProduct_Price());
                holder.productDesc.setText(productModel.getProduct_Description());
                Picasso.get()
                        .load(productModel.getImage_Uri())
                        .into(holder.productImg);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final String productId=productModel.getProduct_RandomKey();
                        CharSequence arr[]=new CharSequence[]
                                {
                                        "Yes",
                                        "No"
                                };
                        AlertDialog.Builder  builder=new AlertDialog.Builder(ProductApprovement_Activity.this);
                        builder.setTitle("Do you want to approve this product. Are you sure?");
                        builder.setItems(arr, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (which==0)
                                {
                                    ChangeStatusMethod(productId);

                                }
                                if (which==1)
                                {

                                }

                            }


                        });
                        builder.show();
                    }

                });

            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowproductview,parent,false);
                MyViewHolder myViewHolder=new MyViewHolder(view);
                return myViewHolder;
            }
        };
        recyclerView.setAdapter(adapter21);
        adapter21.startListening();

    }


    private void ChangeStatusMethod(String pid) {
        databaseReference.child(pid).child("Product_Status").setValue("Approved").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ProductApprovement_Activity.this, "Product Approved Successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
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

import com.example.shoping_app.R;
import com.example.shoping_app.ViewHolder.MyViewHolder;
import com.example.shoping_app.productModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MaintainProduct extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    private RecyclerView.LayoutManager layoutManager;
    private AlertDialog alertDialog;
    private String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain_product);

        recyclerView=findViewById(R.id.Admin_side_Recycleview_id220);
        layoutManager= new LinearLayoutManager(MaintainProduct.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        databaseReference= FirebaseDatabase.getInstance().getReference("product");


    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<productModel> options = new FirebaseRecyclerOptions.Builder<productModel>()
                .setQuery(databaseReference, productModel.class).build();


        FirebaseRecyclerAdapter<productModel, MyViewHolder> adapter = new FirebaseRecyclerAdapter<productModel, MyViewHolder>(options) {
            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowproductview, parent, false);
                MyViewHolder myViewHolder = new MyViewHolder(view);
                return myViewHolder;

            }

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

                        CharSequence abc[]=new CharSequence[]
                                {
                                        "yes",
                                        "No"
                                };
                        AlertDialog.Builder builder=new AlertDialog.Builder(MaintainProduct.this);
                        builder.setTitle("Do you want to Delete that product");
                        builder.setItems(abc, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which==0)
                                {
                                    productId=getRef(holder.getAdapterPosition()).getKey();
                                    databaseReference.child(productId).removeValue();
                                }
                                else {
                                    alertDialog.dismiss();
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
}
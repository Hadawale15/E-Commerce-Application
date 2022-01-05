package com.example.shoping_app.Sellers;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoping_app.AllModel.SellerModel;
import com.example.shoping_app.R;
import com.example.shoping_app.ViewHolder.SellerViewHolder;
import com.example.shoping_app.productDetailsActivivty;
import com.example.shoping_app.productModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class SellerHomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    RecyclerView.LayoutManager layoutManager;
    private AlertDialog.Builder builder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_seller_home, container, false);


        databaseReference= FirebaseDatabase.getInstance().getReference("product");
        recyclerView=view.findViewById(R.id.seller_home_recycler_id);
        layoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        builder=new AlertDialog.Builder(getActivity());
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<SellerModel> options12=new FirebaseRecyclerOptions.Builder<SellerModel>()
                .setQuery(databaseReference.orderByChild("Seller_UID").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()),SellerModel.class).build();


        FirebaseRecyclerAdapter<SellerModel, SellerViewHolder> adapter=new FirebaseRecyclerAdapter<SellerModel, SellerViewHolder>(options12) {
            @NonNull
            @Override
            public SellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.vrification_single_row,parent,false);
                SellerViewHolder ViewHolder=new SellerViewHolder(view);
                return ViewHolder;

            }

            @Override
            protected void onBindViewHolder(@NonNull SellerViewHolder holder, int i, @NonNull SellerModel productModel) {

                holder.productName.setText(productModel.getProduct_Name());
                holder.productPrice.setText(productModel.getProduct_Price());
                holder.productDesc.setText(productModel.getProduct_Description());
           //     Toast.makeText(getActivity(), productModel.getProduct_Name(), Toast.LENGTH_SHORT).show();


                String productId = productModel.getProduct_RandomKey();
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        CharSequence cond[]=new CharSequence[]
                                {
                                        "Yes"
                                        ,"No"
                                };

                        builder.setTitle("Do you want to remove this product");
                        builder.setItems(cond, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (which==0)
                                {
                                    databaseReference.child(productId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                            {
                                                Toast.makeText(getActivity(), "Item Removed Successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });
                        builder.show();
                    }
                });

                final String status=productModel.getProduct_Status();
                if (status.equals("Approved"))
                {
                    holder.verify.setVisibility(View.VISIBLE);
                }
                else
                {
                    holder.notVerify.setVisibility(View.VISIBLE);
                    holder.verify.setVisibility(View.INVISIBLE);
                }
                Picasso.get()
                        .load(productModel.getImage_Uri())
                        .into(holder.productImg);


            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}
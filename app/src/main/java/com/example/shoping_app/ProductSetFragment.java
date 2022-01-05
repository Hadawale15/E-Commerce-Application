package com.example.shoping_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoping_app.ViewHolder.MyViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ProductSetFragment extends Fragment {
    RecyclerView.LayoutManager layoutManager;


    RecyclerView recyclerView;
    DatabaseReference databaseReference;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_product_set, container, false);


        recyclerView=view.findViewById(R.id.RecycleView_id);
        layoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        databaseReference= FirebaseDatabase.getInstance().getReference("product");





        
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<productModel> options=new FirebaseRecyclerOptions.Builder<productModel>()
                .setQuery(databaseReference.orderByChild("Product_Status").equalTo("Approved"),productModel.class).build();


        FirebaseRecyclerAdapter<productModel, MyViewHolder> adapter=new FirebaseRecyclerAdapter<productModel, MyViewHolder>(options) {
            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowproductview,parent,false);
                MyViewHolder myViewHolder=new MyViewHolder(view);
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
                            Intent intent = new Intent(getActivity(), productDetailsActivivty.class);
                            intent.putExtra("pid",productModel.getProduct_RandomKey());
                            intent.putExtra("SellerUID",productModel.getSeller_UID());
                            intent.putExtra("SellerPhone",productModel.getSeller_Phone_No());
                            startActivity(intent);





                    }

                });
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}
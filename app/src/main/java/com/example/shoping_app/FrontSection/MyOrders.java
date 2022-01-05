package com.example.shoping_app.FrontSection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoping_app.AllModel.OrderedProductModel;
import com.example.shoping_app.Interface.ItemClickListner;
import com.example.shoping_app.Prevalent.Prevalent;
import com.example.shoping_app.R;
import com.example.shoping_app.ViewHolder.MyOrdersViewHolder;
import com.example.shoping_app.ViewHolder.MyViewHolder;
import com.example.shoping_app.ViewHolder.OrderedProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MyOrders extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);


        recyclerView = findViewById(R.id.MyOrder_recycle_id2);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("CartList").child("AdminView").child(Prevalent.currentUser.getMobile()).child("product");


    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<OrderedProductModel> option = new FirebaseRecyclerOptions.Builder<OrderedProductModel>()
                .setQuery(databaseReference, OrderedProductModel.class).build();



        FirebaseRecyclerAdapter<OrderedProductModel, MyOrdersViewHolder> adapter = new FirebaseRecyclerAdapter<OrderedProductModel, MyOrdersViewHolder>(option) {
            @NonNull
            @Override
            public MyOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myorders_single_row, parent, false);
                MyOrdersViewHolder holder = new MyOrdersViewHolder(view);
                return holder;
            }

            @Override
            protected void onBindViewHolder(@NonNull MyOrdersViewHolder holder, int i, @NonNull OrderedProductModel productsModel) {
                holder.pName2.setText(productsModel.getPname());
                holder.pPrize2.setText(productsModel.getPprize());
                holder.pDesc2.setText(productsModel.getPdesc());
                holder.pQty2.setText(productsModel.getQuantity());
                Picasso.get().load(productsModel.getImage()).into(holder.pImg2);

               final String Status=productsModel.getStatus();
               final String Confirm=productsModel.getConfirmation();
               if (Status.equals("Delivered"))
               {
                   holder.delivered.setVisibility(View.VISIBLE);
                   holder.notDel.setVisibility(View.INVISIBLE);


               }
               else if (Status.equals("Not Delivered"))
               {
                   holder.notDel.setVisibility(View.VISIBLE);
                   holder.delivered.setVisibility(View.INVISIBLE);
               }
               else if (Status.equals("Reached"))
               {
                   holder.delivered.setVisibility(View.VISIBLE);
                   holder.notDel.setVisibility(View.INVISIBLE);
                   holder.delivered.setText("Received");
               }

            }

        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
package com.example.shoping_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.shoping_app.ViewHolder.MyViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class SearchProductActivity extends AppCompatActivity {

    private EditText Stext;
    private Button Sbutton;
    private RecyclerView recyclerView;
    private String text;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);
        Stext=findViewById(R.id.Search_product_edittext_id);
        Sbutton=findViewById(R.id.Search_product_Button_id);
        recyclerView=findViewById(R.id.Search_product_recycler_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchProductActivity.this));

        Sbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text=Stext.getText().toString().toLowerCase();
                showSearchProduct(text);
            }


        });

    }
    private void showSearchProduct(String text) {
     databaseReference= FirebaseDatabase.getInstance().getReference("product");

        FirebaseRecyclerOptions<productModel> options22=new FirebaseRecyclerOptions.Builder<productModel>()
                .setQuery(databaseReference.orderByChild("Product_Name").startAt(text),productModel.class).build();

        FirebaseRecyclerAdapter<productModel, MyViewHolder> adapter=new FirebaseRecyclerAdapter<productModel, MyViewHolder>(options22) {
            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowproductview,parent,false);
                MyViewHolder myViewHolder=new MyViewHolder(view);
                return myViewHolder;

            }

            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int i, @NonNull productModel productModel) {

                holder.productName.setText(productModel.getProduct_Name());
                holder.productPrice.setText(productModel.getProduct_Price());
                holder.productDesc.setText(productModel.getProduct_Description());
                Picasso.get()
                        .load(productModel.Image_Uri)
                        .into(holder.productImg);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SearchProductActivity.this, productDetailsActivivty.class);
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
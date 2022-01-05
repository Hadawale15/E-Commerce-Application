package com.example.shoping_app.Sellers;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoping_app.AllModel.CartModel;
import com.example.shoping_app.FrontSection.SettingActivity;
import com.example.shoping_app.R;
import com.example.shoping_app.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class SellerNewOrder extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference2;
    RecyclerView.LayoutManager layoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_seller_new_order, container, false);

        databaseReference2= FirebaseDatabase.getInstance().getReference("CartList");
        recyclerView=view.findViewById(R.id.seller_receivedOrders_recycler_id);
        layoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        databaseReference2=FirebaseDatabase.getInstance().getReference("CartList").child("SellerView").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("product");

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<CartModel> options = new FirebaseRecyclerOptions.Builder<CartModel>()
                .setQuery(databaseReference2.orderByChild("Status").equalTo("Delivered"), CartModel.class).build();



        FirebaseRecyclerAdapter<CartModel, CartViewHolder> adapter1 = new FirebaseRecyclerAdapter<CartModel, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull CartModel cartModel) {

                cartViewHolder.pName.setText(cartModel.getPname());
                cartViewHolder.pPrize.setText("Rs " + cartModel.getPprize());
                cartViewHolder.pQty.setText("Qty: " + cartModel.getQuantity());
                cartViewHolder.pDesc.setText(cartModel.getPdesc());
                Picasso.get().load(cartModel.getImage()).into(cartViewHolder.pImg);
                cartViewHolder.pEdit.setVisibility(View.GONE);
                cartViewHolder.pRemove.setText("Infomation");
                cartViewHolder.pRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String CustName=cartModel.getCutomerName();
                        String CustAdd=cartModel.getCustomerAddress();
                        String CustCity=cartModel.getCustomerCity();
                        String CustPinCode=cartModel.getCutomerPinCode();
                        String CustMobile=cartModel.getCutomerMobile();
                        String CustomerRegisterMobile=cartModel.getCustomerRegisterMobile();
                        String PID=cartModel.getPid();
                        String SUID=cartModel.getSeller_UID();


                        Intent intent=new Intent(getActivity(),CutomerDetailsForOrder.class);
                        intent.putExtra("Name",CustName);
                        intent.putExtra("Add",CustAdd);
                        intent.putExtra("City",CustCity);
                        intent.putExtra("pin",CustPinCode);
                        intent.putExtra("Mobile",CustMobile);
                        intent.putExtra("RegisterMobile",CustomerRegisterMobile);
                        intent.putExtra("Pid",PID);
                        intent.putExtra("SellerUID",SUID);

                        startActivity(intent);
                    }
                });

            }


            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_single_row, parent, false);
                CartViewHolder cartViewHolder = new CartViewHolder(view);
                return cartViewHolder;
            }
        };
        recyclerView.setAdapter(adapter1);
        adapter1.startListening();
    }
}
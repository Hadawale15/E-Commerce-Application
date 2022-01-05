package com.example.shoping_app.FrontSection;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoping_app.AllModel.CartModel;
import com.example.shoping_app.Prevalent.Prevalent;
import com.example.shoping_app.R;
import com.example.shoping_app.ViewHolder.CartViewHolder;
import com.example.shoping_app.productDetailsActivivty;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView totalamount,CurrentStatus;
    private Button nextButton;
    private DatabaseReference databaseReference,databaseReference2,databaseReference3;
    RecyclerView.LayoutManager layoutManager;
    private double completeAmount=0;
    private String sellerUid1,prductID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.Cart_recycle_id);
        layoutManager = new LinearLayoutManager(CartActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        totalamount = findViewById(R.id.Cart_total_amount_id);
        nextButton = findViewById(R.id.Cart_next_Buttonm_id);
        CurrentStatus = findViewById(R.id.order_status_id);

        totalamount.setText(String.valueOf(completeAmount));

       // status = getIntent().getStringExtra("id1");





    }


        @Override
        protected void onStart () {
            super.onStart();


            OrderStatus();
            databaseReference = FirebaseDatabase.getInstance().getReference("CartList").child("UserView").child(Prevalent.currentUser.getMobile()).child("product");

            databaseReference2 = FirebaseDatabase.getInstance().getReference("CartList").child("AdminView").child(Prevalent.currentUser.getMobile()).child("product");


            completeAmount = 0;


            FirebaseRecyclerOptions<CartModel> options = new FirebaseRecyclerOptions.Builder<CartModel>()
                    .setQuery(databaseReference, CartModel.class).build();

            FirebaseRecyclerAdapter<CartModel, CartViewHolder> adapter1 = new FirebaseRecyclerAdapter<CartModel, CartViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull CartModel cartModel) {

                    cartViewHolder.pName.setText(cartModel.getPname());
                    cartViewHolder.pPrize.setText("Rs " + cartModel.getPprize());
                    cartViewHolder.pQty.setText("Qty: " + cartModel.getQuantity());
                    cartViewHolder.pDesc.setText(cartModel.getPdesc());
                    Picasso.get().load(cartModel.getImage()).into(cartViewHolder.pImg);

                    sellerUid1 = cartModel.getSeller_UID();
                    prductID = cartModel.getPid();

                    databaseReference3=FirebaseDatabase.getInstance().getReference("CartList").child("SellerView").child(sellerUid1).child("product");

                    cartViewHolder.pEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(CartActivity.this, productDetailsActivivty.class);
                            intent.putExtra("pid", cartModel.getPid());
                            intent.putExtra("SellerUID",cartModel.getSeller_UID());
                            intent.putExtra("SellerPhone",cartModel.getSeller_Phone_No());
                            startActivity(intent);
                        }
                    });
                    try {
                       final double c = Integer.parseInt(cartModel.getPprize()) * Integer.parseInt(cartModel.getQuantity());
                        completeAmount = completeAmount + c;


                        totalamount.setText(String.valueOf(completeAmount));
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(CartActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    cartViewHolder.pRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            databaseReference2.child(cartModel.getPid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    databaseReference.child(cartModel.getPid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                       databaseReference3.child(cartModel.getPid()).removeValue();
                                            Toast.makeText(CartActivity.this, "Item Removed", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(CartActivity.this, CartActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(CartActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CartActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    });
                    nextButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(CartActivity.this, ConfirmOrder_Activity.class);
                            intent.putExtra("totalAmount", String.valueOf(completeAmount));
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



    private void OrderStatus() {
            databaseReference = FirebaseDatabase.getInstance().getReference("Orders");
            databaseReference.child(Prevalent.currentUser.getMobile()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String currentState = snapshot.child("CurrentState").getValue().toString();
                        String userName = snapshot.child("Name").getValue().toString();

                        if (currentState.equals("Shipped")) {
                            recyclerView.setVisibility(View.INVISIBLE);
                            nextButton.setVisibility(View.INVISIBLE);
                            totalamount.setVisibility(View.INVISIBLE);

                            CurrentStatus.setVisibility(View.VISIBLE);
                            CurrentStatus.setText("Congratulation your order has been Shipped Successfully,Soon you will be received your product");

                        } else if (currentState.equals("Not Shipped")) {
                            recyclerView.setVisibility(View.INVISIBLE);
                            nextButton.setVisibility(View.INVISIBLE);
                            totalamount.setVisibility(View.INVISIBLE);
                            CurrentStatus.setVisibility(View.VISIBLE);
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

    }

}
/*




2.

3.

 */
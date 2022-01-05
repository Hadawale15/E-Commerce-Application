package com.example.shoping_app.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoping_app.Interface.ItemClickListner;
import com.example.shoping_app.R;

public class SellerViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView productName,productPrice,productDesc;
    public ImageView productImg;
    public Button verify,notVerify;
    public ItemClickListner listner;

    public SellerViewHolder(@NonNull View itemView) {
        super(itemView);
        productName=(TextView) itemView.findViewById(R.id.product_name41);
        productPrice=(TextView) itemView.findViewById(R.id.product_price43);
        productDesc=(TextView) itemView.findViewById(R.id.prouct_desc44);
        productImg=(ImageView) itemView.findViewById(R.id.product_image42);
        verify=itemView.findViewById(R.id.product_verified_button45);
        notVerify=itemView.findViewById(R.id.product_Notverified_button46);
    }



    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }
    @Override
    public void onClick(View v) {

        listner.onClick(v, getAdapterPosition(), false);
    }
}

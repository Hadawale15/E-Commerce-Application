package com.example.shoping_app.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoping_app.Interface.ItemClickListner;
import com.example.shoping_app.R;

public class OrderedProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView pImg2;
    public TextView pName2,pPrize2,pDesc2,pQty2;

    private ItemClickListner listner;

    public OrderedProductViewHolder(@NonNull View itemView) {
        super(itemView);

        pImg2=itemView.findViewById(R.id.Cart_product_img_id2);
        pName2=itemView.findViewById(R.id.Cart_product_name_id2);
        pDesc2=itemView.findViewById(R.id.Cart_product_desc_id2);
        pQty2=itemView.findViewById(R.id.Cart_product_qty_id2);
        pPrize2=itemView.findViewById(R.id.Cart_product_prize_id2);



    }


    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner=listner;

    }

    @Override
    public void onClick(View v) {
        listner.onClick(v, getAdapterPosition(), false);


    }
}

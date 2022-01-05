package com.example.shoping_app.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoping_app.Interface.ItemClickListner;
import com.example.shoping_app.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView pImg;
    public TextView pName,pPrize,pDesc,pQty;
    public Button pRemove,pEdit;
    private ItemClickListner listner;


    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        pImg=itemView.findViewById(R.id.Cart_product_img_id);
        pName=itemView.findViewById(R.id.Cart_product_name_id);
        pDesc=itemView.findViewById(R.id.Cart_product_desc_id);
        pQty=itemView.findViewById(R.id.Cart_product_qty_id);
        pPrize=itemView.findViewById(R.id.Cart_product_prize_id);
        pRemove=itemView.findViewById(R.id.Cart_remove_button_id);
        pEdit=itemView.findViewById(R.id.cart_edit_button_id);



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

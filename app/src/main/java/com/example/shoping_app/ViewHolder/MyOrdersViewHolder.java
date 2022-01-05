package com.example.shoping_app.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoping_app.Interface.ItemClickListner;
import com.example.shoping_app.R;

public class MyOrdersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView pImg2;
    public TextView pName2,pPrize2,pDesc2,pQty2;
    public Button delivered,notDel;

    private ItemClickListner listner;

    public MyOrdersViewHolder(@NonNull View itemView) {
        super(itemView);

        pImg2=itemView.findViewById(R.id.Cart_product_img_id3);
        pName2=itemView.findViewById(R.id.Cart_product_name_id3);
        pDesc2=itemView.findViewById(R.id.Cart_product_desc_id3);
        pQty2=itemView.findViewById(R.id.Cart_product_qty_id3);
        pPrize2=itemView.findViewById(R.id.Cart_product_prize_id3);
        delivered=itemView.findViewById(R.id.delivered_screen_id);
        notDel=itemView.findViewById(R.id.not_delivered_screen_id);




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

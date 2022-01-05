package com.example.shoping_app.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoping_app.Interface.ItemClickListner;
import com.example.shoping_app.R;

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView productName,productPrice,productDesc;
    public ImageView productImg;
    public ItemClickListner listner;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        productName=(TextView) itemView.findViewById(R.id.output_product_name_id);
        productPrice=(TextView) itemView.findViewById(R.id.output_product_prize_id);
        productDesc=(TextView) itemView.findViewById(R.id.output_product_Desc_id);
        productImg=(ImageView) itemView.findViewById(R.id.output_product_image_id);
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

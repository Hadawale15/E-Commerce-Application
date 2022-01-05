package com.example.shoping_app.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoping_app.Interface.ItemClickListner;
import com.example.shoping_app.R;

public class Ad_oreder_view_holder extends RecyclerView.ViewHolder implements View.OnClickListener {

   public TextView custname,custmob,custadd,custpincode,custdate,prodprice;
   public Button showProdB;
    private ItemClickListner listner;



    public Ad_oreder_view_holder(@NonNull View itemView) {
        super(itemView);

        custname=itemView.findViewById(R.id.Admin_new_orderl_ayout_name_id);
        custmob=itemView.findViewById(R.id.Admin_new_order_layout_mobile_id);
        custadd=itemView.findViewById(R.id.Admin_new_order_layout_address_id);
        custpincode=itemView.findViewById(R.id.Admin_new_order_layout_pin_id);
        custdate=itemView.findViewById(R.id.Admin_new_order_date__id);
        prodprice=itemView.findViewById(R.id.Admin_new_order_layout_price_id);
        showProdB=itemView.findViewById(R.id.Admin_ordered_product_id);

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

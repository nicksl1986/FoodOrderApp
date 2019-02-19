package com.slobodsky.nick.foodorderapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;

import com.slobodsky.nick.foodorderapp.Interface.ItemClickListener;
import com.slobodsky.nick.foodorderapp.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtOrderID, txtOrderStatus, txtOrderPhone, txtOrderaddress;

    private ItemClickListener itemClickListener;


    public OrderViewHolder(View itemView) {

        super(itemView);

        txtOrderID = itemView.findViewById(R.id.order_id);

        txtOrderStatus = itemView.findViewById(R.id.order_status);

        txtOrderPhone =  itemView.findViewById(R.id.order_phone);

        txtOrderaddress =  itemView.findViewById(R.id.order_address);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {

        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {

       itemClickListener.onClick(v, getAdapterPosition(), false);

    }
}

package com.slobodsky.nick.foodorderapp.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.slobodsky.nick.foodorderapp.Interface.ItemClickListener;
import com.slobodsky.nick.foodorderapp.R;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView food_name;

    public ImageView food_image;

    private ItemClickListener itemClickListener;

    public FoodViewHolder(View itemView) {

        super(itemView);

        food_name = itemView.findViewById(R.id.food_name);

        food_image = itemView.findViewById(R.id.food_image);

        itemView.setOnClickListener(this);
    }


    public void setItemClickListener(ItemClickListener itemclickListener) {

        this.itemClickListener = itemclickListener;
    }



    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v, getAdapterPosition(), false);

    }
}

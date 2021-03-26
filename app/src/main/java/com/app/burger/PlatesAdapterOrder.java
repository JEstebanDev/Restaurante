package com.app.burger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlatesAdapterOrder extends RecyclerView.Adapter<PlatesAdapterOrder.ViewHolder> {
    ArrayList<Plates> arrayPlates;
    int heigh,width;
    private Context mContext;
    public PlatesAdapterOrder(ArrayList<Plates> arrayPlates, int heigh, int width, Context mContext) {
        this.arrayPlates = arrayPlates;
        this.heigh=heigh;
        this.width=width;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PlatesAdapterOrder.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_order,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PlatesAdapterOrder.ViewHolder holder, int position) {
        final Plates plato=arrayPlates.get(position);
        Picasso.get().load(arrayPlates.get(position).getImage()).resize(heigh,width).centerCrop().into(holder.ivimageView);
        holder.textVname.setText(arrayPlates.get(position).getName());
        holder.textVprice.setText("$ "+arrayPlates.get(position).getPrice());
        holder.btnPlus.setOnClickListener(v -> {
        });
        holder.btnMinus.setOnClickListener(v -> {
        });
        holder.itemView.setOnClickListener(v -> {
            Intent intent=new Intent(holder.itemView.getContext(),Details_Plates.class);
            intent.putExtra("itemDetails",plato);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return arrayPlates.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivimageView;
        TextView textVname;
        TextView textVprice;
        Button btnPlus;
        Button btnMinus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivimageView=itemView.findViewById(R.id.imageView);
            textVname=itemView.findViewById(R.id.name);
            textVprice=itemView.findViewById(R.id.price);
            btnPlus=itemView.findViewById(R.id.btnPlus);
            btnMinus=itemView.findViewById(R.id.btnMinus);
        }
    }
}
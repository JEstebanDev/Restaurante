package com.app.burger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlatesAdapter extends RecyclerView.Adapter<PlatesAdapter.ViewHolder> {
    ArrayList<Plates> arrayPlates;

    public PlatesAdapter(ArrayList<Plates> arrayPlates) {
        this.arrayPlates = arrayPlates;
    }

    @NonNull
    @Override
    public PlatesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlatesAdapter.ViewHolder holder, int position) {


        Picasso.get().load(arrayPlates.get(position).getImage()).resize(850,400).centerCrop().into(holder.ivimageView);
        holder.textVname.setText(arrayPlates.get(position).getName());
        holder.textVprice.setText("$ "+arrayPlates.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return arrayPlates.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivimageView;
        TextView textVname;
        TextView textVprice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivimageView=itemView.findViewById(R.id.imageView);
            textVname=itemView.findViewById(R.id.name);
            textVprice=itemView.findViewById(R.id.price);
        }
    }
}
package com.app.burger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlatesAdapter extends RecyclerView.Adapter<PlatesAdapter.ViewHolder> {
    ArrayList<Plates> arrayPlates;
    int heigh,width;
    private Context mContext;
    public PlatesAdapter(ArrayList<Plates> arrayPlates, int heigh, int width, Context mContext) {
        this.arrayPlates = arrayPlates;
        this.heigh=heigh;
        this.width=width;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PlatesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        return new ViewHolder(view);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PlatesAdapter.ViewHolder holder, int position) {
        final Plates plato=arrayPlates.get(position);
        Picasso.get().load(arrayPlates.get(position).getImage()).resize(heigh,width).centerCrop().into(holder.ivimageView);
        holder.textVname.setText(arrayPlates.get(position).getName());
        holder.textVprice.setText("$ "+arrayPlates.get(position).getPrice());
        holder.btnBuy.setOnClickListener(v -> {
            DBHelper dBhelper = new DBHelper(mContext);
            dBhelper.INSERT_PLATE(arrayPlates.get(position).getId(),arrayPlates.get(position).getImage(),
                    arrayPlates.get(position).getName(),
                    arrayPlates.get(position).getDescription(),
                    1,
                    arrayPlates.get(position).getPrice());
            holder.btnBuy.setText(R.string.Anadido);
            holder.btnBuy.setBackgroundColor( Color.parseColor( "#FFCC33" ) );


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
        Button btnBuy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivimageView=itemView.findViewById(R.id.imageView);
            textVname=itemView.findViewById(R.id.name);
            textVprice=itemView.findViewById(R.id.price);
            btnBuy=itemView.findViewById(R.id.btnBuy);
        }
    }
}
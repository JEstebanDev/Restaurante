package com.app.burger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class PlatesAdapterOrder extends RecyclerView.Adapter<PlatesAdapterOrder.ViewHolder> {
    ArrayList<PlatesOrder> arrayPlates;
    int heigh,width;
    private Context mContext;



    public PlatesAdapterOrder(ArrayList<PlatesOrder> arrayPlates, int heigh, int width, Context mContext) {
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
        final PlatesOrder plato=arrayPlates.get(position);
        Picasso.get().load(arrayPlates.get(position).getImage()).resize(heigh,width).centerCrop().into(holder.ivimageView);
        holder.textVname.setText(arrayPlates.get(position).getName());
        holder.textVprice.setText("$ "+arrayPlates.get(position).getPrice());
        AtomicInteger constant= new AtomicInteger(1);
        DBHelper dbHelper=new DBHelper(mContext);

        holder.btnPlus.setOnClickListener(v -> {
            //condicional para que la cantidad de un producto no pase de 9
            if (constant.get()>8){
                constant.set(8);
            }
            if (constant.get()<0){
                constant.set(1);
            }else{
                holder.textAmount.setText(""+constant.incrementAndGet());
                dbHelper.UPDATE_PLATE(arrayPlates.get(position).getId(),constant.get());
            }
        });
        holder.btnMinus.setOnClickListener(v -> {
            if(constant.get()<=0){
                constant.getAndSet(0);
            }else{
                holder.textAmount.setText(""+constant.decrementAndGet());
            }
            if (constant.get()==0){
                Log.d("entrando","aqui");
                dbHelper.DELETE_PLATE(arrayPlates.get(position).getId());
                //remueve item anteriormenten borrado por haber elegido 0 en cantidad de producto
               arrayPlates.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, arrayPlates.size());
                holder.itemView.setVisibility(View.GONE);
                //final de pasos para remover
            } else{
                dbHelper.UPDATE_PLATE(arrayPlates.get(position).getId(),constant.get());
            }

        });
        holder.itemView.setOnClickListener(v -> {
            Intent intent=new Intent(holder.itemView.getContext(),Details_Plates.class);
            intent.putExtra("itemDetails",plato);
            holder.itemView.getContext().startActivity(intent);
        });

        holder.btnOrderNow.setOnClickListener(v -> {


        });
    }

    @Override
    public int getItemCount() {
        return arrayPlates.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivimageView;
        TextView textVname;
        TextView textAmount;
        TextView textVprice;
        Button btnOrderNow;
        Button btnPlus;
        Button btnMinus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivimageView=itemView.findViewById(R.id.imageView);
            textVname=itemView.findViewById(R.id.name);
            textAmount=itemView.findViewById(R.id.textAmount);
            textVprice=itemView.findViewById(R.id.price);
            btnOrderNow=itemView.findViewById(R.id.btnOrderNow);
            btnPlus=itemView.findViewById(R.id.btnPlus);
            btnMinus=itemView.findViewById(R.id.btnMinus);
        }
    }
}
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class PlatesAdapterOrder extends RecyclerView.Adapter<PlatesAdapterOrder.ViewHolder> {
    ArrayList<Plates> arrayPlates;
    int heigh,width;
    private Context mContext;

    private FirebaseFirestore databaseReference;

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
        holder.textAmount.setText(""+arrayPlates.get(position).getAmount());
        holder.textVprice.setText("$ "+arrayPlates.get(position).getPrice());
        AtomicInteger constant= new AtomicInteger(1);
        DBHelper dbHelper=new DBHelper(mContext);
        constant.set(arrayPlates.get(position).getAmount());
        holder.btnPlus.setOnClickListener(v -> {
            //condicional para que la cantidad de un producto no pase de 9
            if (constant.get()>8){
                constant.set(8);
            }
            if (constant.get()<0){
                constant.set(1);
            }else{
                holder.textAmount.setText(""+constant.incrementAndGet());
                arrayPlates.get(position).setAmount(constant.get());
                dbHelper.UPDATE_PLATE(arrayPlates.get(position).getId(),constant.get());
            }
        });
        holder.btnMinus.setOnClickListener(v -> {
            if(constant.get()<=0){
                constant.getAndSet(0);
            }else{
                arrayPlates.get(position).setAmount(constant.get());
                holder.textAmount.setText(""+constant.decrementAndGet());
            }
            if (constant.get()==0){
                arrayPlates.get(position).setAmount(constant.get());
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
        Button btnPlus;
        Button btnMinus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivimageView=itemView.findViewById(R.id.imageView);
            textVname=itemView.findViewById(R.id.name);
            textAmount=itemView.findViewById(R.id.textAmount);
            textVprice=itemView.findViewById(R.id.price);
            btnPlus=itemView.findViewById(R.id.btnPlus);
            btnMinus=itemView.findViewById(R.id.btnMinus);
        }
    }
}
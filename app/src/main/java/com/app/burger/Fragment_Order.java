package com.app.burger;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Fragment_Order extends Fragment {

    public ArrayList<Plates> aPlates;
    public PlatesAdapterOrder platesAdapter;
    public RecyclerView recycler_menu;
    public LinearLayout layoutmensaje;
    public Button btnOrderNow;
    public Fragment_Order() {
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment__order, container, false);
        recycler_menu=view.findViewById(R.id.recycler_pedido);
        recycler_menu.setHasFixedSize(true);
        recycler_menu.setLayoutManager(new GridLayoutManager(getContext(),2));
        layoutmensaje = view.findViewById(R.id.layoutmensaje);

        btnOrderNow=view.findViewById(R.id.btnOrderNow);
        btnOrderNow.setOnClickListener(v -> {
            DBHelper dbHelper=new DBHelper(getContext());
            FirebaseAuth mAuth=FirebaseAuth.getInstance();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("users").document(mAuth.getCurrentUser().getEmail());
            Map<String, Integer> datos=new HashMap<>();

            for (int i=0;i<aPlates.size();i++)
            {
                datos.put(aPlates.get(i).getId(),aPlates.get(i).getAmount());
                dbHelper.UPDATE_PLATE_BILL(aPlates.get(i).getId(),"activo");
            }
            Map<String, Object> user = new HashMap<>();
            user.put("name", mAuth.getCurrentUser().getEmail());
            user.put("points", 0);
            user.put("state", "activo");
            user.put("fav-plate", datos);

            Map<String, Object> data_order = new HashMap<>();
            data_order.put("id_user", mAuth.getCurrentUser().getEmail());
            data_order.put("plates", datos);
            data_order.put("state", "active");

            db.collection("users").document(mAuth.getCurrentUser().getEmail())
                    .set(user)
                    .addOnSuccessListener(aVoid -> Log.d("EXCELENTE", "DocumentSnapshot successfully written!"));

            db.collection("order")
                    .add(data_order)
                    .addOnSuccessListener(documentReference ->
                            Log.d("TAG", "Document PLATES added with ID: " + documentReference.getId()))
                    .addOnFailureListener(e ->
                            Log.w("TAG", "Error adding document", e));
        });
        return view;
    }
}
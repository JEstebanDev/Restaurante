package com.app.burger;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Fragment_Bill extends Fragment {

    public TextView textVTotalPrice;
    public RecyclerView recycler_factura;
    public LinearLayout sup,layoutmensaje;
    public ScrollView contenido;
    public Fragment_Bill() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment__bill, container, false);

        recycler_factura=view.findViewById(R.id.recycler_factura);
        recycler_factura.setHasFixedSize(true);
        recycler_factura.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        sup = view.findViewById(R.id.sup);
        contenido = view.findViewById(R.id.contenido);
        layoutmensaje = view.findViewById(R.id.layoutmensaje);
        textVTotalPrice=view.findViewById(R.id.textVTotalPrice);

        return view;
    }
}
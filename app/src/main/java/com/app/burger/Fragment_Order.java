package com.app.burger;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Fragment_Order extends Fragment {

    public ArrayList<Plates> aPlates;
    public PlatesAdapterOrder platesAdapter;
    public RecyclerView recycler_menu;
    public Fragment_Order() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment__order, container, false);

        recycler_menu=view.findViewById(R.id.recycler_pedido);
        recycler_menu.setHasFixedSize(true);
        recycler_menu.setLayoutManager(new GridLayoutManager(getContext(),2));



        return view;
    }
}
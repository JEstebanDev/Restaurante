package com.app.burger;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class Fragment_Menu extends Fragment implements View.OnClickListener{

    public Fragment_Menu() {
        // Required empty public constructor
    }

    private ArrayList<Plates> aPlates;
    private RecyclerView recycler_menu;
    private PlatesAdapter platesAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment__menu, container, false);

        ImageButton button_sandwich=view.findViewById(R.id.button_sandwich);
        ImageButton button_pizza=view.findViewById(R.id.button_pizza);
        ImageButton button_burger=view.findViewById(R.id.button_burger);
        ImageButton button_drink=view.findViewById(R.id.button_drink);

        recycler_menu=view.findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        recycler_menu.setLayoutManager(new GridLayoutManager(getContext(),2));

        button_sandwich.setOnClickListener(this);
        button_pizza.setOnClickListener(this);
        button_burger.setOnClickListener(this);
        button_drink.setOnClickListener(this);
        
        default_value();

        return view;
    }

    private void default_value() {
        FirebaseFirestore databaseReference = FirebaseFirestore.getInstance();
        aPlates=new ArrayList<>();
        databaseReference.collection("plates").whereNotEqualTo("category","Favoritos").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Plates plate=new Plates(
                                    document.getId(),
                                    document.get("name").toString(),
                                    document.get("description").toString(),
                                    document.get("image").toString(),
                                    Integer.parseInt(document.get("price").toString()));
                            aPlates.add(plate);
                        }
                        platesAdapter=new PlatesAdapter(aPlates,650,400, getContext());
                        recycler_menu.setAdapter(platesAdapter);
                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case (R.id.button_sandwich):
                consult("sandwich");
                break;
            case (R.id.button_pizza):
                consult("pizza");
                break;
            case (R.id.button_burger):
                consult("burger");
                break;
            case (R.id.button_drink):
                consult("drink");
                break;

        }
    }


    private void consult(String category) {
        FirebaseFirestore databaseReference = FirebaseFirestore.getInstance();
        aPlates=new ArrayList<>();

        databaseReference.collection("plates")
                .whereEqualTo("category", category)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Plates plate=new Plates(
                                    document.getId(),
                                    document.get("name").toString(),
                                    document.get("description").toString(),
                                    document.get("image").toString(),
                                    Integer.parseInt(document.get("price").toString()));
                            aPlates.add(plate);
                        }
                        platesAdapter=new PlatesAdapter(aPlates, 650, 400, getContext());
                        recycler_menu.setAdapter(platesAdapter);
                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
                    }
                });
    }
}
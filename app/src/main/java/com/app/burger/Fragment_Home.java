package com.app.burger;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Home extends Fragment{

    RecyclerView recycler_home,recycler_fav;
    DocumentReference docRef;
    private ArrayList<Plates> aPlates;
    private ArrayList<Plates> aUserPlates;
    private PlatesAdapter platesAdapter;
    public Fragment_Home() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        View view=inflater.inflate(R.layout.fragment__home, container, false);

        recycler_home=view.findViewById(R.id.recycler_home);
        recycler_home.setHasFixedSize(true);
        recycler_home.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recycler_fav= view.findViewById(R.id.recycler_fav);
        recycler_fav.setHasFixedSize(true);
        recycler_fav.setLayoutManager(new GridLayoutManager(getContext(),2));

        consult(currentUser.getEmail());

        return view;
    }

    private void consult(String idUser) {
        FirebaseFirestore databaseReference = FirebaseFirestore.getInstance();
        aPlates=new ArrayList<>();
        aUserPlates = new ArrayList<>();

        databaseReference.collection("plates")
                .whereEqualTo("category", "Favoritos")
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
                        platesAdapter=new PlatesAdapter(aPlates,850,400, getContext());
                        recycler_home.setAdapter(platesAdapter);
                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
                    }
                });
        docRef = databaseReference.collection("users").document(idUser);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Users usuario=new Users(document.get("name").toString(),
                            document.get("state").toString(),
                            Integer.parseInt(document.get("points").toString()),(List<String>) document.get("fav-plate"));

                    for (int i=0;i<usuario.getFavPlates().size();i++){
                        docRef = databaseReference.collection("plates").document(usuario.favPlates.get(i));
                        docRef.get().addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                DocumentSnapshot document1 = task1.getResult();
                                if (document1.exists()) {
                                    Plates plate=new Plates(
                                            document1.getId(),
                                            document1.get("name").toString(),
                                            document1.get("description").toString(),
                                            document1.get("image").toString(),
                                            Integer.parseInt(document1.get("price").toString()));
                                    aUserPlates.add(plate);
                                    platesAdapter=new PlatesAdapter(aUserPlates,650,400, getContext());
                                    recycler_fav.setAdapter(platesAdapter);
                                } else {
                                    Log.d("TAG", "No such document");
                                }
                            } else {
                                Log.d("TAG", "get failed with ", task1.getException());
                            }
                        });
                    }

                    //Log.d("DATOS USUARIO", "DocumentSnapshot data: " + document.getData());
                } else {
                    Log.d("TAG", "No such document");
                }
            } else {
                Log.d("TAG", "get failed with ", task.getException());
            }
        });
    }
}
package com.app.burger;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Fragment_Account extends Fragment {

    private RecyclerView recycler_fav;
    private ArrayList<Plates> aUserPlates;
    private PlatesAdapter platesAdapter;
    private DocumentReference docRef;

    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    public Fragment_Account() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment__account, container, false);

        recycler_fav= view.findViewById(R.id.recycler_fav);
        recycler_fav.setHasFixedSize(true);
        recycler_fav.setLayoutManager(new GridLayoutManager(getContext(),2));


        consult(currentUser.getEmail());
        return view;
    }

    private void consult(String idUser) {

        FirebaseFirestore databaseReference = FirebaseFirestore.getInstance();
        aUserPlates = new ArrayList<>();
        docRef = databaseReference.collection("users").document(idUser);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Users usuario=new Users(document.get("name").toString(),
                            document.get("state").toString(),
                            Integer.parseInt(document.get("points").toString()),
                            (Map<String, Integer>) document.get("fav-plate"));
                    for (Map.Entry<String, Integer> entry : usuario.getFavPlates().entrySet()) {
                        docRef = databaseReference.collection("plates").document(entry.getKey());
                        docRef.get().addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                DocumentSnapshot document1 = task1.getResult();
                                if (document1.exists()) {
                                    Plates plate=new Plates(
                                            document1.getId(),
                                            document1.get("name").toString(),
                                            document1.get("description").toString(),
                                            document1.get("image").toString(),
                                            Integer.parseInt(document1.get("price").toString()),0);
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
package com.app.burger;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class Home extends AppCompatActivity {

    RecyclerView recycler_home;
    private FirebaseFirestore databaseReference;
    private ArrayList<Plates> aPlates;
    private PlatesAdapter platesAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        recycler_home= findViewById(R.id.recycler_home);
        recycler_home.setHasFixedSize(true);
        recycler_home.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        databaseReference = FirebaseFirestore.getInstance();
        aPlates=new ArrayList<>();


        databaseReference.collection("plates")
                .whereEqualTo("category", "Favoritos")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Plates plate=new Plates(document.get("name").toString(),
                                    document.get("image").toString(),
                                    Integer.parseInt(document.get("price").toString()));
                            aPlates.add(plate);
                        }
                        platesAdapter=new PlatesAdapter(aPlates);
                        recycler_home.setAdapter(platesAdapter);
                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
                    }
                });
    }

}

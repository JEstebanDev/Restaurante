package com.app.burger;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class Home extends AppCompatActivity {

    RecyclerView recycler_home,recycler_fav;
    DocumentReference docRef;
    private ArrayList<Plates> aPlates;
    private ArrayList<Plates> aUserPlates;
    private PlatesAdapter platesAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Bundle b = getIntent().getExtras();
        String idUser = b.getString("idUser");

        recycler_home= findViewById(R.id.recycler_home);
        recycler_home.setHasFixedSize(true);
        recycler_home.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler_fav= findViewById(R.id.recycler_fav);
        recycler_fav.setHasFixedSize(true);
        recycler_fav.setLayoutManager(new GridLayoutManager(this,2));

        FirebaseFirestore databaseReference = FirebaseFirestore.getInstance();
        List<String> listFavPlates = null;
        aPlates=new ArrayList<>();
        List<String> listUserPlates = null;
        aUserPlates = new ArrayList<>();

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
                platesAdapter=new PlatesAdapter(aPlates,850,400);
                recycler_home.setAdapter(platesAdapter);
            } else {
                Log.d("TAG", "Error getting documents: ", task.getException());
            }
        });
        Users usuario=new Users();
        docRef = databaseReference.collection("users").document(idUser);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Users usuario=new Users(document.get("name").toString(),
                                document.get("state").toString(),
                                Integer.parseInt(document.get("points").toString()),(List<String>) document.get("fav-plate"));

                        for (int i=0;i<usuario.getFavPlates().size();i++){
                            docRef = databaseReference.collection("plates").document(usuario.favPlates.get(i));
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            Plates plate=new Plates(document.get("name").toString(),
                                                    document.get("image").toString(),
                                                    Integer.parseInt(document.get("price").toString()));
                                            aUserPlates.add(plate);
                                            platesAdapter=new PlatesAdapter(aUserPlates,650,400);
                                            recycler_fav.setAdapter(platesAdapter);
                                        } else {
                                            Log.d("TAG", "No such document");
                                        }
                                    } else {
                                        Log.d("TAG", "get failed with ", task.getException());
                                    }
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
            }
        });






        /*
        databaseReference.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (!document.get("fav-plate").toString().isEmpty()){
                                listFavPlates.add(document.get("fav-plate").toString());
                                Log.d("ELEMENTS", listFavPlates+"");
                            }

                        }
                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
                    }
                });
*/



    }
    void userSigned( String strMail){
        //if the user is log in i need all his data in the app
        //El siguiente ejemplo muestra cómo recuperar el contenido de un documento


    }
}

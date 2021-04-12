package com.app.burger;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity{

    private EditText editUser, editPassword;
    private FirebaseAuth mAuth;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editUser = findViewById(R.id.editUser);
        editPassword = findViewById(R.id.editPassword);

        Button btnLabelCreate = findViewById(R.id.btnLabelCreate);
        Button btnLogin = findViewById(R.id.btnLogin);

        DBHelper dbHelper =new DBHelper(this);
        SQLiteDatabase db=openOrCreateDatabase("burger.db",MODE_PRIVATE, null);
        dbHelper.onCreate(db);
        // ...
        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();

        btnLabelCreate.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, CreateAccount.class));
            finish();
        });

        btnLogin.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            String strUser = editUser.getText().toString().trim();
            String strPassword = editPassword.getText().toString().trim();

            if(!strUser.isEmpty() && !strPassword.isEmpty()){
                mAuth.signInWithEmailAndPassword(strUser, strPassword)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Mensaje","signInWithEmail:success");
                                startActivity(new Intent(Login.this, Menu_bar.class));
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Mensaje", "signInWithEmail:failure", task.getException());
                                builder.setTitle("Ups!");
                                builder.setMessage("Contraseña incorrecta")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", (dialog, id) -> {
                                            editPassword.setText("");
                                        });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }
                        });
            }else{
                builder.setTitle("Ups!");
                builder.setMessage("Llena todos los campos")
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, id) -> {
                            editUser.setText("");
                            editPassword.setText("");
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser==null){
            Log.i("Mensaje", "No user is signed in");
        }
    }
}

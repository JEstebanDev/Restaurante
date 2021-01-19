package com.app.burger;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private TextView textPolitics;
    private EditText editUser, editMail, editPassword, editRepassword;
    private Button btnLabelCreate, btnLabelLogin, btnLogin, btnCreate;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        textPolitics = findViewById(R.id.textPolitics);
        editUser = findViewById(R.id.editUser);
        editMail = findViewById(R.id.editMail);
        editPassword = findViewById(R.id.editPassword);
        editRepassword = findViewById(R.id.editRepassword);

        btnLabelCreate = findViewById(R.id.btnLabelCreate);
        btnLabelLogin = findViewById(R.id.btnLabelLogin);
        btnCreate = findViewById(R.id.btnCreate);
        btnLogin = findViewById(R.id.btnLogin);

        btnLabelCreate.setOnClickListener(this);
        btnLabelLogin.setOnClickListener(this);
        btnCreate.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        // ...
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    private boolean validation() {

        String strUser = editUser.getText().toString().trim();
        String strMail = editMail.getText().toString().trim();
        String strPassword = editPassword.getText().toString().trim();
        String strRepassword = editRepassword.getText().toString().trim();

        String strError="";

        if (!TextUtils.isEmpty(strUser) && !TextUtils.isEmpty(strMail)
                && !TextUtils.isEmpty(strPassword) && !TextUtils.isEmpty(strRepassword)) {
            if (isValidMail(strMail)) {
                if (strPassword != strRepassword) {
                    if (isValidPassword(strPassword)) {
                        return  true;
                    }
                }else{
                    Log.d("Mensaje", "Las contraseñas no coinciden");
                    return false;
                }
            }else{
                Log.d("Mensaje", "Correo no valido");
                return false;
            }
        }else{
            Log.d("Mensaje", "Rellena todos los campos");
            return false;
        }

        return false;
    }

    public static boolean isValidPassword(String strPassword) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9\\!\\@\\#\\$]{8,24}");

        return !TextUtils.isEmpty(strPassword) && PASSWORD_PATTERN.matcher(strPassword).matches();
    }

    private boolean isValidMail(CharSequence csMail) {
        return (!TextUtils.isEmpty(csMail) && Patterns.EMAIL_ADDRESS.matcher(csMail).matches());
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser==null){
            startActivity(new Intent(this,Login.class));
        }
    }
    @Override
    public void onClick(View v) {
        String strUser = editUser.getText().toString().trim();
        String strMail = editMail.getText().toString().trim();
        String strPassword = editPassword.getText().toString().trim();
        switch (v.getId()) {
            case R.id.btnLabelCreate:
                Log.i("Mensaje", "This is create");
                textPolitics.setVisibility(View.VISIBLE);
                editMail.setVisibility(View.VISIBLE);
                editRepassword.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.GONE);
                btnCreate.setVisibility(View.VISIBLE);


                break;
            case R.id.btnLabelLogin:
                Log.i("Mensaje", "This is login");
                textPolitics.setVisibility(View.GONE);
                editMail.setVisibility(View.GONE);
                editRepassword.setVisibility(View.GONE);
                btnLogin.setVisibility(View.VISIBLE);
                btnCreate.setVisibility(View.GONE);
                break;
            case R.id.btnCreate:
                Log.d("Mensaje", "boton presionado");
                if (validation()){
                    mAuth.createUserWithEmailAndPassword(strMail, strPassword)
                            .addOnCompleteListener(this, task -> {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("Mensaje", "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Log.d("Mensaje", "Logueo");
                                    startActivity(new Intent(Login.this, Home.class));
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("Mensaje", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            });
                }
            case R.id.btnLogin:
                Log.d("Mensaje", "boton login presionado");

                    mAuth.signInWithEmailAndPassword(strUser, strPassword)
                            .addOnCompleteListener(this, task -> {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("Mensaje","signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startActivity(new Intent(Login.this, Home.class));
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("Mensaje", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
        }
    }
}

package com.app.burger;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Details_Plates extends AppCompatActivity {

    ImageView imageView;
    TextView name,description,price;
    Button btnBuy;
    Plates plates;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_plates);
        imageView=findViewById(R.id.imageView);
        name=findViewById(R.id.name);
        description=findViewById(R.id.description);
        price=findViewById(R.id.price);
        btnBuy=findViewById(R.id.btnBuy);
        initValues();
    }

    @SuppressLint("SetTextI18n")
    private void initValues() {
        plates= (Plates) getIntent().getExtras().getSerializable("itemDetails");
        name.setText(plates.getName());
        description.setText(plates.getDescription());
        price.setText("$ "+plates.getPrice());
        Picasso.get().load(plates.getImage()).fit().into(imageView);
        btnBuy.setOnClickListener(v -> {
            btnBuy.setText(R.string.Anadido);
            btnBuy.setBackgroundColor( Color.parseColor( "#FFCC33" ) );
        });

    }
}

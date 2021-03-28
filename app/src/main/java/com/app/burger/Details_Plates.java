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
    PlatesOrder platesOrder;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_plates);
        imageView=findViewById(R.id.imageView);
        name=findViewById(R.id.name);
        description=findViewById(R.id.description);
        price=findViewById(R.id.price);
        initValues();
    }

    @SuppressLint("SetTextI18n")
    private void initValues() {
        platesOrder= (PlatesOrder) getIntent().getExtras().getSerializable("itemDetails");
        name.setText(platesOrder.getName());
        description.setText(platesOrder.getDescription());
        price.setText("$ "+platesOrder.getPrice());
        Picasso.get().load(platesOrder.getImage()).fit().into(imageView);

    }
}

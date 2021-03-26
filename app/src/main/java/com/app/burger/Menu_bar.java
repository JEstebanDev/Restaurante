package com.app.burger;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Menu_bar extends AppCompatActivity {

    Fragment_Home fragment_home=new Fragment_Home();
    Fragment_Menu fragment_menu=new Fragment_Menu();
    Fragment_Order fragment_order=new Fragment_Order();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_bar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.frame_Menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container,fragment_home);
        transaction.add(R.id.container,fragment_menu);
        transaction.add(R.id.container,fragment_order);
        transaction.hide(fragment_menu);
        transaction.hide(fragment_order);
        transaction.commit();
    }
    @SuppressLint("NonConstantResourceId")
    private final BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod= item -> {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId())
        {
            case R.id.menu_home:
                transaction.hide(fragment_menu);
                transaction.hide(fragment_order);
                transaction.show(fragment_home);
                transaction.commit();
                break;
            case R.id.menu_menu:
                transaction.hide(fragment_home);
                transaction.hide(fragment_order);
                transaction.show(fragment_menu);
                transaction.commit();

                break;
            case R.id.menu_pedido:
                metodo();
                transaction.hide(fragment_home);
                transaction.hide(fragment_menu);
                transaction.show(fragment_order);
                transaction.commit();


                break;
        }
        return false;
    };

    private void metodo() {
        fragment_order.aPlates=new ArrayList<>();
        DBHelper dbHelper=new DBHelper(this);
        Cursor cursor=dbHelper.GET_PLATE_DATA();
        if (cursor.moveToFirst()){
            do{
                String id_plate = cursor.getString(cursor.getColumnIndex("id_plate"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                String image = cursor.getString(cursor.getColumnIndex("image"));
                String price = cursor.getString(cursor.getColumnIndex("price"));
                Plates plate=new Plates(
                        id_plate,
                        name,
                        description,
                        image,
                        Integer.parseInt(price));
                fragment_order.aPlates.add(plate);
            }while(cursor.moveToNext());
            fragment_order.platesAdapter=new PlatesAdapterOrder(fragment_order.aPlates, 650, 400, fragment_order.getContext());
            fragment_order.recycler_menu.setAdapter(fragment_order.platesAdapter);
        }
        cursor.close();
    }
}

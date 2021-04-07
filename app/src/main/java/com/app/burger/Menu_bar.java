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
    Fragment_Bill fragment_bill=new Fragment_Bill();

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
        transaction.add(R.id.container, fragment_bill);
        transaction.hide(fragment_menu);
        transaction.hide(fragment_order);
        transaction.hide(fragment_bill);
        transaction.commit();
    }
    @SuppressLint("NonConstantResourceId")
    private final BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod= item -> {
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        switch (item.getItemId())
        {
            case R.id.menu_home:
                transaction.hide(fragment_menu);
                transaction.hide(fragment_order);
                transaction.hide(fragment_bill);
                transaction.show(fragment_home);
                transaction.commit();
                break;
            case R.id.menu_menu:
                transaction.hide(fragment_home);
                transaction.hide(fragment_order);
                transaction.hide(fragment_bill);
                transaction.show(fragment_menu);
                transaction.commit();

                break;
            case R.id.menu_pedido:
                reloadingOrder();
                transaction.hide(fragment_home);
                transaction.hide(fragment_menu);
                transaction.hide(fragment_bill);
                transaction.show(fragment_order);
                transaction.commit();
                break;
            case R.id.menu_factura:
                reloadingBill();
                transaction.hide(fragment_home);
                transaction.hide(fragment_menu);
                transaction.hide(fragment_order);
                transaction.show(fragment_bill);
                transaction.commit();
                break;
        }
        return false;
    };

    @SuppressLint("SetTextI18n")
    public void reloadingBill(){
        int iTotalPrice=0;
        ArrayList<Plates> aPlates;
        PlatesAdapterBill platesAdapter;
        aPlates=new ArrayList<>();
        DBHelper dbHelper=new DBHelper(this);
        Cursor cursor=dbHelper.GET_PLATE_DATA_BILL();
        if(cursor.getCount() == 0){
            platesAdapter=new PlatesAdapterBill(aPlates, 350, 350);
            fragment_bill.recycler_factura.setAdapter(platesAdapter);
            fragment_bill.textVTotalPrice.setText("0");
        }else{
            if (cursor.moveToFirst()){
                do{
                    String id_plate = cursor.getString(cursor.getColumnIndex("id_plate"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String description = cursor.getString(cursor.getColumnIndex("description"));
                    String image = cursor.getString(cursor.getColumnIndex("image"));
                    int price = cursor.getInt(cursor.getColumnIndex("price"));
                    int amount = cursor.getInt(cursor.getColumnIndex("amount"));
                    Plates plateOrder=new Plates(
                            id_plate,
                            name,
                            description,
                            image,
                            price,
                            amount);
                    aPlates.add(plateOrder);
                    iTotalPrice=iTotalPrice+amount*price;
                }while(cursor.moveToNext());
                platesAdapter=new PlatesAdapterBill(aPlates, 350, 350);
                fragment_bill.textVTotalPrice.setText("$ "+iTotalPrice);
                fragment_bill.recycler_factura.setAdapter(platesAdapter);
            }
        }
        cursor.close();
    }


    public void reloadingOrder() {
        fragment_order.aPlates=new ArrayList<>();
        DBHelper dbHelper=new DBHelper(this);
        Cursor cursor=dbHelper.GET_PLATE_DATA();
        if(cursor.getCount() == 0){
            fragment_order.platesAdapter=new PlatesAdapterOrder(fragment_order.aPlates, 650, 400, fragment_order.getContext());
            fragment_order.recycler_menu.setAdapter(fragment_order.platesAdapter);
        }else{
            if (cursor.moveToFirst()){
                do{

                    String id_plate = cursor.getString(cursor.getColumnIndex("id_plate"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String description = cursor.getString(cursor.getColumnIndex("description"));
                    String image = cursor.getString(cursor.getColumnIndex("image"));
                    int price = cursor.getInt(cursor.getColumnIndex("price"));
                    int amount = cursor.getInt(cursor.getColumnIndex("amount"));
                    Plates plateOrder=new Plates(
                            id_plate,
                            name,
                            description,
                            image,
                            price,
                            amount);
                    fragment_order.aPlates.add(plateOrder);
                }while(cursor.moveToNext());
                fragment_order.platesAdapter=new PlatesAdapterOrder(fragment_order.aPlates, 650, 400, fragment_order.getContext());
                fragment_order.recycler_menu.setAdapter(fragment_order.platesAdapter);
            }
        }
        cursor.close();
    }
}

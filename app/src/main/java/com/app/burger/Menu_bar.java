package com.app.burger;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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
                transaction.hide(fragment_home);
                transaction.hide(fragment_menu);
                transaction.show(fragment_order);
                transaction.commit();
                break;
        }
        return false;
    };
}

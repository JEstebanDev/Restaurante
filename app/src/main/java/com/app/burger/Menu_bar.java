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
    Fragment_Order fragment_order =new Fragment_Order();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_bar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.frame_Menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);

        loadFragment(fragment_home);

    }
    @SuppressLint("NonConstantResourceId")
    private final BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod= item -> {
        switch (item.getItemId())
        {
            case R.id.menu_home:
                loadFragment(fragment_home);
                break;
            case R.id.menu_menu:
                loadFragment(fragment_menu);
                break;
            case R.id.menu_pedido:
                loadFragment(fragment_order);
                break;

        }
        return false;
    };
    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.commit();

    }
}

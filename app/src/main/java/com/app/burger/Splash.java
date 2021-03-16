package com.app.burger;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        //FullScreen
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            final WindowInsetsController insetsController = getWindow().getInsetsController();
            if (insetsController != null) {
                insetsController.hide(WindowInsets.Type.statusBars());
            }
        } else {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        }
        //Animation

        Animation animationUp=AnimationUtils.loadAnimation(this,R.anim.top_animation);
        Animation animationBottom=AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        ImageView imageView=findViewById(R.id.logo);
        TextView byJuanDev=findViewById(R.id.textJuanDev);

        imageView.setAnimation(animationUp);
        byJuanDev.setAnimation(animationBottom);

        int SPLASH_SCREEN = 1500;
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(this, Login.class));
            finish();
        }, SPLASH_SCREEN);

    }
}
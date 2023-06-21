package com.prakruthi.ui;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.prakruthi.R;

import java.sql.Time;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new TimerTask() {
            @Override
            public void run() {
                Log.wtf(TAG, "run: " );
                startActivity(new Intent(SplashScreen.this, Login.class));
                finish();
            }
        },1000);

    }

}
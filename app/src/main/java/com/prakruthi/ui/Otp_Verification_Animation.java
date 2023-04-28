package com.prakruthi.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.prakruthi.R;

public class Otp_Verification_Animation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification_animation);

        getSupportActionBar().hide();

        startActivity(new Intent(Otp_Verification_Animation.this, MainActivity.class));


    }
}
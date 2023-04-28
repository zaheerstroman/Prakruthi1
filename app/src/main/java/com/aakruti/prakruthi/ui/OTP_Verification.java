package com.aakruti.prakruthi.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.aakruti.prakruthi.R;

public class OTP_Verification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        getSupportActionBar().hide();

        startActivity(new Intent(OTP_Verification.this, Otp_Verification_Animation.class));

        startActivity(new Intent(OTP_Verification.this, NewCredentials.class));

    }
}
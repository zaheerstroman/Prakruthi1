package com.prakruthi.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.prakruthi.R;


public class ForgetPassword extends AppCompatActivity {

    AppCompatButton forget_password_backbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        forget_password_backbtn = findViewById(R.id.forgetpassword_btn);
        getSupportActionBar().hide();
        forget_password_backbtn.setOnClickListener(view -> {
            super.onBackPressed();

        });
    }
}
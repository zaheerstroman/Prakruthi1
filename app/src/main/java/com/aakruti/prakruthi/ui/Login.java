package com.aakruti.prakruthi.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.aakruti.prakruthi.R;

public class Login extends AppCompatActivity {

    TextView register,forget_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        register = findViewById(R.id.register_an_account_login);
        forget_password = findViewById(R.id.forget_password_login);
        register.setOnClickListener(view -> {
            startActivity(new Intent(Login.this,RegistrationFrom.class));
        });

        forget_password.setOnClickListener(view -> {
            startActivity(new Intent(Login.this,ForgetPassword.class));
        });


    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
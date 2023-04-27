package com.aakruti.prakruthi.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aakruti.prakruthi.R;

public class Login extends AppCompatActivity {

    TextView register,forget_password;
    EditText username,password;
    AppCompatButton login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        register = findViewById(R.id.register_an_account_login);
        forget_password = findViewById(R.id.forget_password_login);
        username = findViewById(R.id.edittext_user_name);
        password = findViewById(R.id.edittext_login_password);
        login = findViewById(R.id.login_btn);

        register.setOnClickListener(view -> {
            startActivity(new Intent(Login.this,RegistrationFrom.class));
        });
        forget_password.setOnClickListener(view -> {
            startActivity(new Intent(Login.this,ForgetPassword.class));
        });
        login.setOnClickListener(view -> {
            if (username.getText().toString().isEmpty())
            {
                username.setError("Username is Required");
            }
            else if (password.getText().toString().isEmpty())
            {
                password.setError("Password is Required");
            }
            if(!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty())
            {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

            }

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
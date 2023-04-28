package com.aakruti.prakruthi.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aakruti.prakruthi.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

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
                Api();
            }
        });

    }

    public void Api()
    {
        //Start ProgressBar first (Set visibility VISIBLE)
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[2];
                field[0] = "mobile";
                field[1] = "password";
                //Creating array for data
                String[] data = new String[2];
                data[0] = username.getText().toString();
                data[1] = password.getText().toString();
                PutData putData = new PutData(Variables.BaseUrl+"login", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        //End ProgressBar (Set visibility to GONE)
                        Log.i("PutData", result);

                    }
                }
                //End Write and Read data with URL
            }

        });

    }


}
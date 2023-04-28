package com.aakruti.prakruthi.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aakruti.prakruthi.R;

public class NewCredentials extends AppCompatActivity {

    AppCompatButton forgetpassword_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_credentials);

        forgetpassword_btn  = findViewById(R.id.forgetpassword_btn);
        forgetpassword_btn.setOnClickListener(view -> {
            super.onBackPressed();
            finish();
        });





        getSupportActionBar().hide();

        startActivity(new Intent(NewCredentials.this, Pasword_Updated_Animation.class));

    }
}
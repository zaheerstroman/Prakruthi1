package com.aakruti.prakruthi.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.aakruti.prakruthi.R;

public class NewCredentials extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_credentials);

        getSupportActionBar().hide();
    }
}
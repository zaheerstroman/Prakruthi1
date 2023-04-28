package com.aakruti.prakruthi.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.aakruti.prakruthi.R;
import com.skydoves.powerspinner.PowerSpinnerView;

public class RegistrationFrom extends AppCompatActivity {

    EditText fullname,phone_number,email,password,city;

    PowerSpinnerView state,district,type;

    CheckBox terms;

    AppCompatButton sendotp, backbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_from);
        getSupportActionBar().hide();
        fullname = findViewById(R.id.edittext_full_name);
        phone_number = findViewById(R.id.edittext_phone_number);
        email = findViewById(R.id.edittext_email_address);
        password = findViewById(R.id.edittext_pass_word);
        city = findViewById(R.id.Edittext_City);
        sendotp = findViewById(R.id.send_OTP_btn);
        terms = findViewById(R.id.checkbox);
        state = findViewById(R.id.State_DropDown);
        district = findViewById(R.id.District_DropDown);
        backbtn = findViewById(R.id.registration_back_btn);
        backbtn.setOnClickListener(view -> {
            super.onBackPressed();
        });
        sendotp.setOnClickListener(view -> {
            if (fullname.getText().toString().trim().isEmpty()) {
                fullname.setError("Full name is required");
            }
            else if (phone_number.getText().toString().trim().isEmpty()) {
                phone_number.setError("Phone number is required");
            }
            else if (email.getText().toString().trim().isEmpty()) {
                email.setError("Email is required");
            }
            else if (password.getText().toString().trim().isEmpty()) {
                password.setError("Password is required");
            }
            else if (city.getText().toString().trim().isEmpty()) {
                city.setError("City is required");
            }
            else {

                String fullnameStr = fullname.getText().toString().trim();
                String phoneStr = phone_number.getText().toString().trim();
                String emailStr = email.getText().toString().trim();
                String passwordStr = password.getText().toString().trim();
                String cityStr = city.getText().toString().trim();

            }
        });

    }

}
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

    private SQLiteDatabase database;

    PowerSpinnerView state,district,type;

    CheckBox terms;

    AppCompatButton sendotp;
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

        // create database
        database = openOrCreateDatabase("mydatabase.db", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS user (fullname TEXT, phone_number TEXT, email TEXT, password TEXT, city TEXT)");

        sendotp.setOnClickListener(view -> {
            if (fullname.getText().toString().trim().isEmpty()) {
                fullname.setError("Full name is required");
            } else if (phone_number.getText().toString().trim().isEmpty()) {
                phone_number.setError("Phone number is required");
            } else if (email.getText().toString().trim().isEmpty()) {
                email.setError("Email is required");
            } else if (password.getText().toString().trim().isEmpty()) {
                password.setError("Password is required");
            } else if (city.getText().toString().trim().isEmpty()) {
                city.setError("City is required");
            } else {
                // insert data into database
                String fullnameStr = fullname.getText().toString().trim();
                String phoneStr = phone_number.getText().toString().trim();
                String emailStr = email.getText().toString().trim();
                String passwordStr = password.getText().toString().trim();
                String cityStr = city.getText().toString().trim();
                ContentValues values = new ContentValues();
                values.put("fullname", fullnameStr);
                values.put("phone_number", phoneStr);
                values.put("email", emailStr);
                values.put("password", passwordStr);
                values.put("city", cityStr);
                database.insert("user", null, values);

                // clear EditText fields
                fullname.setText("");
                phone_number.setText("");
                email.setText("");
                password.setText("");
                city.setText("");

                // show success message
                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
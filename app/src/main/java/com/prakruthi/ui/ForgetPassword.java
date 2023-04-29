package com.prakruthi.ui;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.prakruthi.R;


public class ForgetPassword extends AppCompatActivity {

   

    AppCompatButton forget_password_backbtn;

    AppCompatButton update_btn,nextbtn;
    EditText new_password,confirm_password,email_address;

    RelativeLayout via_sms_img,via_mail_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new_password = findViewById(R.id.edittext_new_password);
        confirm_password = findViewById(R.id.edittext_confirm_password);
        email_address = findViewById(R.id.edittext_fogetpassword_email_adrs);
        update_btn = findViewById(R.id.btn_update_text);
        nextbtn = findViewById(R.id.next_forget_password_btn);
        via_sms_img = findViewById(R.id.via_msg_makeselection);
        via_mail_img = findViewById(R.id.email_msg_makeselection);
        setContentView(R.layout.activity_forget_password);
        forget_password_backbtn = findViewById(R.id.forgetpassword_btn);
        getSupportActionBar().hide();
        forget_password_backbtn.setOnClickListener(view -> {
            super.onBackPressed();
        });
    }
}
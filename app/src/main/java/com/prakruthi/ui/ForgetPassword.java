package com.prakruthi.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.prakruthi.R;

import java.util.Objects;


public class ForgetPassword extends AppCompatActivity {
    AppCompatButton update_btn,nextbtn , forget_password_backbtn,otp_verfication_submit_btn;
    EditText new_password,confirm_password,email_address;

    RelativeLayout via_sms_img,via_mail_img;
    RelativeLayout forget_password_relativelayout,make_selection_relativelayout,otp_verification_relativelayout,new_credential_relativelayout,password_updated_animation_relativelayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        Objects.requireNonNull(getSupportActionBar()).hide();

        forget_password_relativelayout = findViewById(R.id.forget_password_email_RelativeLayout);
        make_selection_relativelayout = findViewById(R.id.MAKE_SELECTION_RelativeLayout);
        otp_verification_relativelayout = findViewById(R.id.Forget_Password_OTP_RelativeLayout);
        new_credential_relativelayout = findViewById(R.id.new_credentials_password_email);
        new_password = findViewById(R.id.edittext_new_password);
        confirm_password = findViewById(R.id.edittext_confirm_password);
        email_address = findViewById(R.id.edittext_forgetpassword_email_adrs);
        update_btn = findViewById(R.id.btn_update_text);
        nextbtn = findViewById(R.id.next_forget_password_btn);
        via_sms_img = findViewById(R.id.via_msg_makeselection);
        via_mail_img = findViewById(R.id.email_msg_makeselection);
        forget_password_backbtn = findViewById(R.id.forgetpassword_btn);
        otp_verfication_submit_btn = findViewById(R.id.btn_otp_submit);
        password_updated_animation_relativelayout = findViewById(R.id.password_updated_animation);

        forget_password_backbtn.setOnClickListener(view -> {
            super.onBackPressed();
        });

        nextbtn.setOnClickListener(view -> {
            forget_password_relativelayout.setVisibility(View.GONE);
            make_selection_relativelayout.setVisibility(View.VISIBLE);
        });

        via_sms_img.setOnClickListener(view -> {
            make_selection_relativelayout.setVisibility(View.GONE);
            otp_verification_relativelayout.setVisibility(View.VISIBLE);
        });

        otp_verfication_submit_btn.setOnClickListener(view -> {
            otp_verification_relativelayout.setVisibility(View.GONE);
            new_credential_relativelayout.setVisibility(View.VISIBLE);
        });

        update_btn.setOnClickListener(view -> {
            new_credential_relativelayout.setVisibility(View.GONE);
            password_updated_animation_relativelayout.setVisibility(View.VISIBLE);
        });
    }
}
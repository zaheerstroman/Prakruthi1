package com.prakruthi.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.goodiebag.pinview.Pinview;
import com.prakruthi.R;

import java.util.Objects;

public class OTP_Verification extends AppCompatActivity implements View.OnClickListener{

    AppCompatButton otp_btn_backpress, btn_otp_submit;

    TextView txt_otp_verification, txt_enter_otp_sent_to, tv_resend_otp_timer, txt_dont_receive_code, txt_re_send;

    Pinview pinview_4_digits;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

//        getSupportActionBar().hide();
        Objects.requireNonNull(getSupportActionBar()).hide();

        otp_btn_backpress = findViewById(R.id.otp_btn_backpress);
        btn_otp_submit = findViewById(R.id.btn_otp_submit);
        txt_otp_verification = findViewById(R.id.txt_otp_verification);
        txt_enter_otp_sent_to = findViewById(R.id.txt_enter_otp_sent_to);
        tv_resend_otp_timer = findViewById(R.id.tv_resend_otp_timer);
        txt_dont_receive_code = findViewById(R.id.txt_dont_receive_code);
        txt_re_send = findViewById(R.id.txt_re_send);
        pinview_4_digits = findViewById(R.id.pinview_4_digits);

//        btn_otp_submit.setOnClickListener(this);

        btn_otp_submit.setOnClickListener(view -> {

                    Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        });


                startTimer(60000, 1000);


    }

    public void startTimer(final long finish, long tick) {
        txt_re_send.setEnabled(false);
        txt_re_send.setClickable(false);
        new CountDownTimer(finish, tick) {

            public void onTick(long millisUntilFinished) {
                long remainedSecs = millisUntilFinished / 1000;
                String stringTime = String.format("%02d:%02d", (remainedSecs / 60), (remainedSecs % 60));
                tv_resend_otp_timer.setText(stringTime);// manage it according to you
            }

            public void onFinish() {
                tv_resend_otp_timer.setText("00:00");
                txt_re_send.setEnabled(true);
                txt_re_send.setClickable(true);
                cancel();
            }
        }.start();
    }


    private void verifyOtp() {

    }

    private void resendOtp() {



    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_re_send:
                resendOtp();
                break;
            case R.id.btn_otp_submit:
                if (pinview_4_digits.getValue().length() == 4)
                    verifyOtp();
                else
                    Toast.makeText(this, "Please Enter Valid Otp", Toast.LENGTH_SHORT).show();
                break;
            case R.id.otp_btn_backpress:
                finish();
                break;
        }
    }

}
package com.prakruthi.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.goodiebag.pinview.Pinview;
import com.prakruthi.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Objects;

public class OTP_Verification extends AppCompatActivity {

    AppCompatButton otp_btn_backpress, btn_otp_submit;

    TextView txt_enter_otp_sent_to, tv_resend_otp_timer, txt_re_send;

    Pinview pinview_4_digits;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        Objects.requireNonNull(getSupportActionBar()).hide();
        otp_btn_backpress = findViewById(R.id.otp_btn_backpress);
        btn_otp_submit = findViewById(R.id.btn_otp_submit_Registration);
        txt_enter_otp_sent_to = findViewById(R.id.txt_enter_otp_sent_to);
        tv_resend_otp_timer = findViewById(R.id.tv_resend_otp_timer);
        txt_re_send = findViewById(R.id.txt_re_send);
        pinview_4_digits = findViewById(R.id.pinview_4_digits);
        txt_enter_otp_sent_to.append(Variables.phoneNumber);
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
        //Start ProgressBar first (Set visibility VISIBLE)
        btn_otp_submit.setVisibility(View.GONE);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[2];
                field[0] = "mobile";
                field[1] = "otp";
                //Creating array for data
                String[] data = new String[2];
                data[0] = Variables.phoneNumber;
                data[1] = pinview_4_digits.getValue();
                PutData putData = new PutData(Variables.BaseUrl+"verifyOtp", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        //End ProgressBar (Set visibility to GONE)

                    }
                }
                //End Write and Read data with URL
            }
        });
    }

    private void resendOtp() {



    }

}
package com.prakruthi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.prakruthi.R;

public class Otp_Verification_Animation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification_animation);

        // In your Otp_Verification_Animation activity's onCreate() method
        Intent intent = getIntent();
        int departmentId = intent.getIntExtra("department_id", 0); // Here, 0 is the default value if the extra is not found
        if (departmentId == 2)
        {
            TextView txt_24_hour = findViewById(R.id.txt_24_hour);
            txt_24_hour.setVisibility(View.GONE);
        }
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Otp_Verification_Animation.this,Login.class));
            }
        },1000);
        // You can now use the departmentId value as needed
    }
}
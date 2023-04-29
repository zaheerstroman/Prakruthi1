package com.prakruthi.ui;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.prakruthi.R;
import com.skydoves.powerspinner.PowerSpinnerView;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class RegistrationForm extends AppCompatActivity {

    //    EditText fullname,phone_number,email,password,city;
    EditText name, mobile, email, password, city;


    //    PowerSpinnerView state, district, Type_DropDown;
    PowerSpinnerView state, district, type;

    CheckBox terms;

    AppCompatButton sendotp, backbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_from);
        Objects.requireNonNull(getSupportActionBar()).hide();

//        fullname = findViewById(R.id.edittext_full_name);
        name = findViewById(R.id.edittext_full_name);
//        phone_number = findViewById(R.id.edittext_phone_number);
        mobile = findViewById(R.id.edittext_phone_number);
        email = findViewById(R.id.edittext_email_address);
        password = findViewById(R.id.edittext_pass_word);
        state = findViewById(R.id.State_DropDown);
        district = findViewById(R.id.District_DropDown);
        city = findViewById(R.id.Edittext_City);
//        Type_DropDown = findViewById(R.id.Type_DropDown);
        type = findViewById(R.id.Type_DropDown);
        terms = findViewById(R.id.checkbox);
        sendotp = findViewById(R.id.send_OTP_btn);
        backbtn = findViewById(R.id.registration_back_btn);

        // set an OnTouchListener to the root view
        View root = findViewById(android.R.id.content);
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // check if the touch is outside of the state view
                    int[] location = new int[2];
                    state.getLocationOnScreen(location);
                    int x = location[0];
                    int y = location[1];
                    int width = state.getWidth();
                    int height = state.getHeight();
                    if (!(event.getX() > x && event.getX() < x + width && event.getY() > y && event.getY() < y + height)) {
                        // dismiss the state view
                        state.dismiss();
                        district.dismiss();
//                        Type_DropDown.dismiss();
                        type.dismiss();
                        return true; // consume the event
                    }
                }
                return false; // do not consume the event
            }
        });
        //
        backbtn.setOnClickListener(view -> {
            super.onBackPressed();
        });

        sendotp.setOnClickListener(view -> {
//            if (fullname.getText().toString().trim().isEmpty()) {
//                fullname.setError("Full name is required");
            if (name.getText().toString().trim().isEmpty()) {
                name.setError("Full name is required");
            }
//            else if (phone_number.getText().toString().trim().isEmpty()) {
//                phone_number.setError("Phone number is required");
            else if (mobile.getText().toString().trim().isEmpty()) {
                mobile.setError("Phone number is required");
            } else if (email.getText().toString().trim().isEmpty()) {
                email.setError("Email is required");
            } else if (password.getText().toString().trim().isEmpty()) {
                password.setError("Password is required");
            } else if (city.getText().toString().trim().isEmpty()) {
                city.setError("City is required");
            } else {

//                String fullnameStr = fullname.getText().toString().trim();
//                String phoneStr = phone_number.getText().toString().trim();
                String fullnameStr = name.getText().toString().trim();
                String phoneStr = mobile.getText().toString().trim();
                String emailStr = email.getText().toString().trim();
                String passwordStr = password.getText().toString().trim();
                String cityStr = city.getText().toString().trim();

            }

//            startActivity(new Intent(RegistrationForm.this, OTP_Verification.class));
            Api();


        });

    }

    public void Api() {
        sendotp.setVisibility(View.INVISIBLE);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Creating array for parameters
                String[] field = new String[9];
                field[0] = "name";
                field[1] = "mobile";
                field[2] = "email";
                field[3] = "password";
                field[4] = "city";
                field[5] = "type";
                field[6] = "state";
                field[7] = "district";
                field[8] = "fcm_token";

                //Creating array for data
                String[] data = new String[9];
                data[0] = name.getText().toString();
                data[1] = mobile.getText().toString();
                data[2] = email.getText().toString();
                data[3] = password.getText().toString();
                data[4] = city.getText().toString();
                data[5] = type.getText().toString();
                data[6] = state.getText().toString();
                data[7] = district.getText().toString();
                data[8] = fcm_token.getText().toString();

                PutData putData = new PutData(Variables.BaseUrl + "registration", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        // result = Api Result
                        String result = putData.getResult();

                        try {
                            JSONObject json = new JSONObject(result);
                            boolean statusCode = json.getBoolean("status_code");
//                            int loggedIn = json.getInt("loggedIn");
                            String message = json.getString("message");
                            if (statusCode) {
                                Toast.makeText(RegistrationForm.this, message, Toast.LENGTH_SHORT).show();
                                getUserData(json);
                            } else {
                                Toast.makeText(RegistrationForm.this, message, Toast.LENGTH_SHORT).show();
                                name.setError("Invalid");
                                password.setError("Invalid");
                                sendotp.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            sendotp.setVisibility(View.VISIBLE);
                            Toast.makeText(RegistrationForm.this, "Network Error", Toast.LENGTH_SHORT).show();
                        }


                    }
                } else {
                    sendotp.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void getUserData(JSONObject ResultJson) {
        try {
            String jsonString = "{\"result\":[{\"user_id\":17,\"user_mobile\":\"5\",\"api_token\":\"832c21fd57c9f14b773e2b144d021671542534e4f6f4f5d1ac9002839e3171dd\"}],\"status_code\":true,\"message\":\"Otp Sent Sucessfully\"}";
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray resultArray = jsonObject.getJSONArray("result");

            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject resultObject = resultArray.getJSONObject(i);
                int userId = resultObject.getInt("user_id");
                String userMobile = resultObject.getString("user_mobile");
                String apiToken = resultObject.getString("api_token");
                // Do something with the values (e.g. add to a list)
            }


            boolean statusCode = jsonObject.getBoolean("status_code");
            String message = jsonObject.getString("message");



            sendotp.setVisibility(View.VISIBLE);
        }
        catch (JSONException e) {
            Log.e(TAG, e.toString() );
            Toast.makeText(this, "System Error", Toast.LENGTH_SHORT).show();
            sendotp.setVisibility(View.VISIBLE);
        }

    }
}
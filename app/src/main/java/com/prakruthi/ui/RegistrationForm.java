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
import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RegistrationForm extends AppCompatActivity {

    EditText fullname,phone_number,email,password,city;

    PowerSpinnerView state, district, type;

    CheckBox terms;

    AppCompatButton sendotp, backbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_from);
        Objects.requireNonNull(getSupportActionBar()).hide();

        fullname = findViewById(R.id.edittext_full_name);
        phone_number = findViewById(R.id.edittext_phone_number);
        email = findViewById(R.id.edittext_email_address);
        password = findViewById(R.id.edittext_pass_word);
        state = findViewById(R.id.State_DropDown);
        district = findViewById(R.id.District_DropDown);
        city = findViewById(R.id.Edittext_City);
        type = findViewById(R.id.Type_DropDown);
        terms = findViewById(R.id.checkbox);
        sendotp = findViewById(R.id.send_OTP_btn);
        backbtn = findViewById(R.id.registration_back_btn);

        // Getting DropDown Arrays
        getDropDownData();

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
                        type.dismiss();
                        return true; // consume the event
                    }
                }
                return false; // do not consume the event
            }
        });

        backbtn.setOnClickListener(view -> {
            super.onBackPressed();
        });
        sendotp.setOnClickListener(view -> {
            state.setError(null);
            district.setError(null);
            type.setError(null);
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
            else if (state.getText().toString().isEmpty())
            {
                state.setError("City is required");
            }
            else if (district.getText().toString().isEmpty())
            {
                district.setError("City is required");
            }
            else if (type.getText().toString().isEmpty())
            {
                type.setError("City is required");
            }
            else {
                String fullnameStr = fullname.getText().toString().trim();
                String phoneStr = phone_number.getText().toString().trim();
                String emailStr = email.getText().toString().trim();
                String passwordStr = password.getText().toString().trim();
                String cityStr = city.getText().toString().trim();
//                String stateStr = state.getText().toString().trim();
//                String districtStr = district.getText().toString().trim();
//                String typeStr = type.getText().toString().trim();
//                Api();

            }


        });

    }

    public void Api(String name , String mobile, String email , String password,String city,String type ,String state,String district,String fcm_token) {
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
                data[0] = name;
                data[1] = mobile;
                data[2] = email;
                data[3] = password;
                data[4] = city;
                data[5] = type;
                data[6] = state;
                data[7] = district;
                data[8] = fcm_token;

                PutData putData = new PutData(Variables.BaseUrl + "registration", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        // result = Api Result
                        String result = putData.getResult();

                        try {
                            JSONObject json = new JSONObject(result);
                            boolean statusCode = json.getBoolean("status_code");
                            String message = json.getString("message");
                            if (statusCode) {
                                Toast.makeText(RegistrationForm.this, message, Toast.LENGTH_SHORT).show();
                                getUserData(json);
                            } else {
                                Toast.makeText(RegistrationForm.this, message, Toast.LENGTH_SHORT).show();
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
            JSONArray resultArray = ResultJson.getJSONArray("result");
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject resultObject = resultArray.getJSONObject(i);
                int userId = resultObject.getInt("user_id");
                String userMobile = resultObject.getString("user_mobile");
                String apiToken = resultObject.getString("api_token");
            }



            sendotp.setVisibility(View.VISIBLE);
        }
        catch (JSONException e) {
            Log.e(TAG, e.toString() );
            Toast.makeText(this, "System Error", Toast.LENGTH_SHORT).show();
            sendotp.setVisibility(View.VISIBLE);
        }

    }

    public void getDropDownData()
    {
        //Start ProgressBar first (Set visibility VISIBLE)
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                FetchData fetchData = new FetchData("https://houseofspiritshyd.in/prakruthi/admin/api/getDropdownData");
                if (fetchData.startFetch()) {
                    if (fetchData.onComplete()) {
                        String result = fetchData.getResult();
                        Log.i("FetchData", result);
                        try {
                            // assume jsonStr contains the JSON object
                            JSONObject jsonObj = new JSONObject(result);

                            // convert the "departments" array to a List<String>
                            JSONArray departments = jsonObj.getJSONArray("departments");
                            ArrayList<String> departmentNames = new ArrayList<>();
                            for(int i = 0; i < departments.length(); i++) {
                                JSONObject department = departments.getJSONObject(i);
                                departmentNames.add(department.getString("name"));
                            }

                            // convert the "state" array to a List<String>
                            JSONArray states = jsonObj.getJSONArray("state");
                            ArrayList<String> stateNames = new ArrayList<>();
                            for(int i = 0; i < states.length(); i++) {
                                JSONObject state = states.getJSONObject(i);
                                stateNames.add(state.getString("name"));
                            }
                            state.setItems(stateNames);


                            // convert the "district" array to a List<String>
                            JSONArray districts = jsonObj.getJSONArray("district");
                            ArrayList<String> districtNames = new ArrayList<>();
                            for(int i = 0; i < districts.length(); i++) {
                                JSONObject district = districts.getJSONObject(i);
                                districtNames.add(district.getString("name"));
                            }
                            district.setItems(districtNames);

                        }
                        catch (JSONException e)
                        {
                            Log.e(TAG, e.toString() );
                            Toast.makeText(RegistrationForm.this, "Network Error", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });
    }
}
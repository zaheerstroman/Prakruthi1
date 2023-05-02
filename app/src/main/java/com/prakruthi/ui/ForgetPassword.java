package com.prakruthi.ui;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.prakruthi.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;


public class ForgetPassword extends AppCompatActivity {
    AppCompatButton update_btn,nextbtn , forget_password_backbtn, otp_verfication_submit_btn;
    EditText new_password,confirm_password,email_address, edittext_forgetpassword_email_adrs;

    RelativeLayout via_sms_img,via_mail_img;
    RelativeLayout forget_password_relativelayout,make_selection_relativelayout,otp_verification_relativelayout,new_credential_relativelayout,password_updated_animation_relativelayout;

    AppCompatButton btn_otp_submit, btn_update_text, password_upated_login_btn;

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

    public void Api(String usernameString,String passwordString) {
        btn_otp_submit.setVisibility(View.INVISIBLE);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Creating array for parameters
                String[] field = new String[1];
                field[0] = "mobile";
//                field[1] = "password";
                //Creating array for data
                String[] data = new String[1];
                data[0] = usernameString;
//                data[1] = passwordString;
                PutData putData = new PutData(Variables.BaseUrl + "forgetPassword", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        // result = Api Result
                        String result = putData.getResult();
                        try {
                            JSONObject json = new JSONObject(result);
                            boolean statusCode = json.getBoolean("status_code");
                            int loggedIn = json.getInt("loggedIn");
                            String message = json.getString("message");
                            if (statusCode)
                            {
                                Toast.makeText(ForgetPassword.this, message, Toast.LENGTH_SHORT).show();
                                getUserData(json);
                            }
                            else
                            {
                                Toast.makeText(ForgetPassword.this, message, Toast.LENGTH_SHORT).show();
                                edittext_forgetpassword_email_adrs.setError("Invalid");
//                                password.setError("Invalid");
                                btn_otp_submit.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            btn_otp_submit.setVisibility(View.VISIBLE);
                            Toast.makeText(ForgetPassword.this, "Network Error", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                else {
                    btn_otp_submit.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void getUserData(JSONObject ResultJson)
    {
        try {
            String token = ResultJson.getString("token");
            JSONArray userDetailsArray = ResultJson.getJSONArray("user_details");
            JSONObject userDetails = userDetailsArray.getJSONObject(0);
            int id = userDetails.getInt("id");
            int departmentId = userDetails.getInt("department_id");
            String userCode = userDetails.optString("user_code", "");
            String name = userDetails.optString("name", "");
            String lastName = userDetails.optString("last_name", "");
            String email = userDetails.optString("email", "");
            String password = userDetails.optString("password", "");
            String mobile = userDetails.optString("mobile", "");
            String gender = userDetails.optString("gender", "");
            String dob = userDetails.optString("dob", "");
            String attachment = userDetails.optString("attachment", "");
            String city = userDetails.optString("city", "");
            String postCode = userDetails.optString("postCode", "");
            String address = userDetails.optString("address", "");
            String state = userDetails.optString("state", "");
            String country = userDetails.optString("country", "");
            String district = userDetails.optString("district", "");
            String street = userDetails.optString("street", "");
            String about = userDetails.optString("about", "");
            String status = userDetails.optString("status", "");
            String mobileVerified = userDetails.optString("mobile_verified", "");
            String isVerified = userDetails.optString("is_verified", "");
            String logDateCreated = userDetails.optString("log_date_created", "");
            String createdBy = userDetails.optString("created_by", "");
            String logDateModified = userDetails.optString("log_date_modified", "");
            String modifiedBy = userDetails.optString("modified_by", "");
            String fcmToken = userDetails.optString("fcm_token", "");
            String deviceId = userDetails.optString("device_id", "");
            String allowEmail = userDetails.optString("allow_email", "");
            String allowSms = userDetails.optString("allow_sms", "");
            String allowPush = userDetails.optString("allow_push", "");

            // Store values in static variables
            Variables.token = token;
            Variables.id = id;
            Variables.departmentId = departmentId;
            Variables.userCode = userCode;
            Variables.name = name;
            Variables.lastName = lastName;
            Variables.email = email;
            Variables.password = password;
            Variables.mobile = mobile;
            Variables.gender = gender;
            Variables.dob = dob;
            Variables.attachment = attachment;
            Variables.city = city;
            Variables.postCode = postCode;
            Variables.address = address;
            Variables.state = state;
            Variables.country = country;
            Variables.district = district;
            Variables.street = street;
            Variables.about = about;
            Variables.status = status;
            Variables.mobileVerified = mobileVerified;
            Variables.isVerified = isVerified;
            Variables.logDateCreated = logDateCreated;
            Variables.createdBy = createdBy;
            Variables.logDateModified = logDateModified;
            Variables.modifiedBy = modifiedBy;
            Variables.fcmToken = fcmToken;
            Variables.deviceId = deviceId;
            Variables.allowEmail = allowEmail;
            Variables.allowSms = allowSms;
            Variables.allowPush = allowPush;

            btn_otp_submit.setVisibility(View.VISIBLE);
        }
        catch (JSONException e) {
            Log.e(TAG, e.toString() );
            Toast.makeText(this, "System Error", Toast.LENGTH_SHORT).show();
            btn_otp_submit.setVisibility(View.VISIBLE);
        }

    }

}
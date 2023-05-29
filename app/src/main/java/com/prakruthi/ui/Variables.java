package com.prakruthi.ui;

public class Variables {
    public static String BaseUrl = "https://houseofspiritshyd.in/prakruthi/admin/api/";


    //Login
    public static String token;
    public static Integer id;
    public static Integer departmentId;
    public static String userCode;
    public static String name;
    public static String lastName;
    public static String email;
    public static String password;
    public static String mobile;
    public static String gender;
    public static String dob;
    public static String attachment;
    public static String city;
    public static String postCode;
    public static String address;
    public static String state;
    public static String country;
    public static String district;
    public static String street;
    public static String about;
    public static String status;
    public static String mobileVerified;
    public static String isVerified;
    public static String logDateCreated;
    public static String createdBy;
    public static String logDateModified;
    public static String modifiedBy;
    public static String fcmToken;
    public static String deviceId;
    public static String allowEmail;
    public static String allowSms;
    public static String allowPush;

    public static String status_code;

    public static String loggedIn;

    public static String message;

    //Registration:-----
    public static Integer userId;

    public static String userMobile;

    public static String apiToken;

    public static String RegistrationOTP;

    public static String phoneNumber;

    public static void clear() {
        // Login
        token = "";
        id = null;
        departmentId = null;
        userCode = "";
        name = "";
        lastName = "";
        email = "";
        password = "";
        mobile = "";
        gender = "";
        dob = "";
        attachment = "";
        city = "";
        postCode = "";
        address = "";
        state = "";
        country = "";
        district = "";
        street = "";
        about = "";
        status = "";
        mobileVerified = "";
        isVerified = "";
        logDateCreated = "";
        createdBy = "";
        logDateModified = "";
        modifiedBy = "";
        fcmToken = "";
        deviceId = "";
        allowEmail = "";
        allowSms = "";
        allowPush = "";

        status_code = "";
        loggedIn = "";
        message = "";

        // Registration
        userId = null;
        userMobile = "";
        apiToken = "";
        RegistrationOTP = "";
        phoneNumber = "";
    }

}

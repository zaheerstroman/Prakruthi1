package com.prakruthi.ui.APIs;

import com.prakruthi.ui.Variables;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

public class SaveDeliveryAddressDetails {

    private final OnSaveDeliveryAddressApiHits mListener;

    public String id;
    public String name;
    public String city;
    public String state;
    public String country;
    public String address;
    public String postal_code;
    public int is_default;

    public SaveDeliveryAddressDetails(OnSaveDeliveryAddressApiHits mListener, String id, String name, String city, String state, String country, String address, String postal_code, int is_default) {
        this.mListener = mListener;
        this.id = id;
        this.name = name;
        this.city = city;
        this.state = state;
        this.country = country;
        this.address = address;
        this.postal_code = postal_code;
        this.is_default = is_default;
    }

    private class HitSaveDeliveryAddressDetailsApi implements Runnable {

        @Override
        public void run() {
            // Creating array for parameters
            String[] field = new String[9];
            field[0] = "id";
            field[1] = "user_id";
            field[2] = "token";
            field[3] = "name";
            field[4] = "city";
            field[5] = "state";
            field[6] = "country";
            field[7] = "address";
            field[8] = "postal_code";

            // Creating array for data
            String[] data = new String[9];
            data[0] = String.valueOf(id);
            data[1] = String.valueOf(Variables.id);
            data[2] = Variables.token;
            data[3] = name;
            data[4] = city;
            data[5] = state;
            data[6] = country;
            data[7] = address;
            data[8] = postal_code;

            PutData putData = new PutData(Variables.BaseUrl + "saveDeliveryAddressDetails", "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    try {
                        JSONObject response = new JSONObject(result);

                        // Extract the "message" string
                        String message = response.getString("message");

                        // Use the "message" string as needed
                        handleResponse(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    handleError("Failed to fetch data");
                }
            } else {
                handleError("Failed to connect to the server");
            }
        }
    }

    private void handleError(String error) {
        mListener.onSaveDeliveryAddressApiError(error);
    }

    private void handleResponse(String message) {
        mListener.onSaveDeliveryAddress(message);
    }

    public interface OnSaveDeliveryAddressApiHits {
        void onSaveDeliveryAddress(String message);
        void onSaveDeliveryAddressApiError(String error);
    }
}
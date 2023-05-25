package com.prakruthi.ui.APIs;

import com.prakruthi.ui.Variables;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

public class DeleteDeliveryAddressDetails {

    private OnDeleteDeliveryAddressApiHits mListner;

    public String id;

    public DeleteDeliveryAddressDetails(OnDeleteDeliveryAddressApiHits mListner , String id)
    {
        this.mListner = mListner;
        this.id = id;
    }

    
    private class HitDeleteDeliveryAddressDetailsApi implements Runnable {

        @Override
        public void run() {
            //Creating array for parameters
            String[] field = new String[3];
            field[0] = "user_id";
            field[1] = "token";
            field[2] = "id";
            //Creating array for data
            String[] data = new String[3];
            data[0] = String.valueOf(Variables.id);
            data[1] = Variables.token;
            data[2] = id;
            PutData putData = new PutData(Variables.BaseUrl+"deleteCartDetails", "POST", field, data);
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
                handleError("Failed to connect to server");
            }
        }
    }

    private void handleError(String error) {
        mListner.OnOnDeleteDeliveryAddressApiGivesError(error);
    }

    private void handleResponse(String message) {

    }

    public interface OnDeleteDeliveryAddressApiHits
    {
        void OnDeleteDeliveryAddress(String message);
        void OnOnDeleteDeliveryAddressApiGivesError(String Errpr);
    }
}

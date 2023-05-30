package com.prakruthi.ui.APIs;

import static com.google.firebase.messaging.Constants.TAG;
import static com.prakruthi.ui.Variables.message;

import android.util.Log;

import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.profile.myorders.MyOrdersModal;
import com.prakruthi.ui.ui.profile.mypayments.MyPaymentsModal;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MyPayments {

    private MyPayments.OnProfileMyPaymentsApiHitFetchedListener mListener;

    public MyPayments(MyPayments.OnProfileMyPaymentsApiHitFetchedListener listener) {
        mListener = listener;
    }

    public void HitAPi() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new MyPayments.GetMyPaymentsData());
    }

    private class GetMyPaymentsData implements Runnable {

        @Override
        public void run() {
            //Creating array for parameters
            String[] field = new String[2];
            field[0] = "user_id";
            field[1] = "token";
            //Creating array for data
            String[] data = new String[2];
            data[0] = String.valueOf(Variables.id);
            data[1] = Variables.token;
            PutData putData = new PutData(Variables.BaseUrl + "myPayments", "POST", field, data);
            if (putData.startPut()) {
                String result = putData.getResult();
                Log.e(TAG, result);
                if (putData.onComplete()) {
                    result = putData.getResult();
                    Log.e(TAG, result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        ArrayList<MyPaymentsModal> myPaymentsModals = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject myPaymentsModalData = jsonArray.getJSONObject(i);
                            String id = myPaymentsModalData.getString("id");
                            String user_id = myPaymentsModalData.getString("user_id");
                            String invoice_id = myPaymentsModalData.getString("invoice_id");
                            String transaction_id = myPaymentsModalData.getString("transaction_id");
                            String cf_transaction_id = myPaymentsModalData.getString("cf_transaction_id");
                            String payment_mode = myPaymentsModalData.getString("payment_mode");
                            String payment_date = myPaymentsModalData.getString("payment_date");
                            String is_paid = myPaymentsModalData.getString("is_paid");
                            myPaymentsModals.add(new MyPaymentsModal(id,user_id,invoice_id,transaction_id,cf_transaction_id,payment_mode,payment_date,is_paid));
                        }
                        handleResponse(myPaymentsModals);
                    }catch (JSONException e)
                    {
                        e.printStackTrace();
                        handleError("Internal Error");
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
        mListener.OnProfileItemMyPaymentsAPiGivesError(error);

    }

    private void handleResponse(ArrayList<MyPaymentsModal> myPaymentsModals) {
        mListener.OnProfileItemMyOrders(myPaymentsModals);

    }

    public interface OnProfileMyPaymentsApiHitFetchedListener {
        void OnProfileItemMyOrders(ArrayList<MyPaymentsModal> myPaymentsModals);

        void OnProfileItemMyPaymentsAPiGivesError(String error);

    }



}
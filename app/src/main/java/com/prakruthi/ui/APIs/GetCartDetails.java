package com.prakruthi.ui.APIs;

import android.content.Context;

import com.prakruthi.Response.CartDataResponse;
import com.prakruthi.ui.Variables;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GetCartDetails {

    private Context mContext;
    private GetCartDetails.OnDataFetchedListener mListener;

    public GetCartDetails(Context context, GetCartDetails.OnDataFetchedListener listener) {
        mContext = context;
        mListener = listener;
    }

    public void fetchData() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new GetCartDetails.GetDataTask());
    }

    private class GetDataTask implements Runnable {

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
//            PutData putData = new PutData(Variables.BaseUrl+"getDashboardDetails", "POST", field, data);
            PutData putData = new PutData(Variables.BaseUrl+"getCartDetails", "POST", field, data);

            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    handleResponse(result);
                } else {
                    handleError("Failed to fetch data");
                }
            } else {
                handleError("Failed to connect to server");
            }
        }

        private void handleResponse(String result) {
            if (result != null) {
                try {
                    JSONObject jsonResponse = new JSONObject(result);

                    JSONArray cartList = jsonResponse.getJSONArray("cart_data");

                    // Create lists
//                    List<CartModal.CartData> cartModal = new ArrayList<>();
                    ArrayList<CartDataResponse> cartModal = new ArrayList<>();


                    // Populate lists
                    for (int i = 0; i < cartList.length(); i++) {
                        JSONObject cartList1 = cartList.getJSONObject(i);
                        int id = cartList1.getInt("id");
                        String name = cartList1.getString("name");
                        String attachment = cartList1.getString("attachment");
                        String description = cartList1.getString("description");

//                        cartModal.add(new CartModal.CartData(id, name, attachment, description));
                        cartModal.add(new CartDataResponse(id, name, attachment, description));

                    }


                    // Call listener with all three lists
                    mListener.onCartListFetched(cartModal);
                } catch (JSONException e) {
                    e.printStackTrace();
                    handleError("Failed to parse data");
                }
            } else {
                handleError("Failed to fetch data");
            }
        }




        private void handleError(String error) {
            mListener.onDataFetchError(error);
        }
    }



    public interface OnDataFetchedListener {
        void onCartListFetched(ArrayList<CartDataResponse> cartModals);
        void onDataFetchError(String error);
    }



}

package com.prakruthi.ui.ui.home.address;

import static com.google.firebase.messaging.Constants.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.prakruthi.R;
import com.prakruthi.ui.APIs.DefaultDeliveryAddressDetails;
import com.prakruthi.ui.Variables;
import com.prakruthi.ui.misc.Loading;
import com.prakruthi.ui.ui.home.HomeFragment;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class Address_BottomSheet_Recycler_Adaptor extends RecyclerView.Adapter<Address_BottomSheet_Recycler_Adaptor.ViewHolder> {
    private final List<Address_BottomSheet_Recycler_Adaptor_Model> mList;
    private final Context context;

    public Address_BottomSheet_Recycler_Adaptor(List<Address_BottomSheet_Recycler_Adaptor_Model> list,Context context) {
        mList = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_location_bottom_dialog_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Address_BottomSheet_Recycler_Adaptor_Model model = mList.get(position);
        holder.nameTextView.setText(model.getName());
        holder.addressTextView.setText(model.getAddress());
        if (model.getDefualt() == 0)
        {
            holder.DefualtAddress.setVisibility(View.GONE);
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.Grey));
        }
        holder.itemView.setOnClickListener(v -> {
            defaultDeliveryAddressDetails(String.valueOf(model.getId()), context, success -> {
                if (success) {
                    // Do something on success
                        for (int i = 0; i < mList.size(); i++) {
                            ViewHolder tempHolder = (ViewHolder) HomeFragment.addressRecyclerView.findViewHolderForAdapterPosition(i);
                            tempHolder.DefualtAddress.setVisibility(View.GONE);
                            tempHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.Grey));
                        }
                        holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.Secondary_less));
                        holder.DefualtAddress.setVisibility(View.VISIBLE);
                        HomeFragment.HomeAddress.setText(holder.addressTextView.getText());
                        Variables.address = holder.DefualtAddress.getText().toString();
                } else {
                    // Do something on failure
                }
            });

        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView addressTextView;
        private final TextView DefualtAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.choose_location_bottom_dialog_recycler_name);
            addressTextView = itemView.findViewById(R.id.choose_location_bottom_dialog_recycler_address);
            DefualtAddress = itemView.findViewById(R.id.choose_location_bottom_dialog_recycler_Default);
        }
    }

    public static void defaultDeliveryAddressDetails(String id, Context context, OnTaskCompleted listener) {
        new AsyncTask<String, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(String... params) {
                //Creating array for parameters
                String[] field = new String[4];
                field[0] = "id";
                field[1] = "user_id";
                field[2] = "token";
                field[3] = "is_default";

                //Creating array for data
                String[] data = new String[4];
                data[0] = params[0];
                data[1] = String.valueOf(Variables.id);
                data[2] = Variables.token;
                data[3] = "1";

                //Creating PutData object and executing the request
                PutData putData = new PutData(Variables.BaseUrl + "saveDeliveryAddressDetails", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        if (result != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                boolean status = jsonObject.getBoolean("status_code");
                                return status;
                            } catch (JSONException e) {
                                Log.e("JSON", "Error parsing JSON", e);
                                return false;
                            }
                        }
                    }
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                listener.onTaskCompleted(success);
            }
        }.execute(id);
    }
    public interface OnTaskCompleted {
        void onTaskCompleted(boolean success);
    }



}

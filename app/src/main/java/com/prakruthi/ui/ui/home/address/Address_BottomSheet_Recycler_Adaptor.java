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
import com.prakruthi.ui.Variables;
import com.prakruthi.ui.misc.Loading;

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

}

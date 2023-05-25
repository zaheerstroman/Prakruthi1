package com.prakruthi.ui.ui.myaddress;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prakruthi.R;
import com.prakruthi.ui.ui.home.address.Address_BottomSheet_Recycler_Adaptor_Model;

import java.util.List;

public class MyAddressesAdaptor extends RecyclerView.Adapter<MyAddressesAdaptor.ViewHolder> {

    private List<Address_BottomSheet_Recycler_Adaptor_Model> addressModelsList;

    public MyAddressesAdaptor(List<Address_BottomSheet_Recycler_Adaptor_Model> addressModelsList) {
        this.addressModelsList = addressModelsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.personal_myaddress_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Address_BottomSheet_Recycler_Adaptor_Model addressModel = addressModelsList.get(position);

        holder.txtName.setText(addressModel.getName());
        holder.txtAddress.setText(addressModel.getAddress());
    }

    @Override
    public int getItemCount() {
        return addressModelsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtAddress;
        TextView txtRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.myAddresses_Name);
            txtAddress = itemView.findViewById(R.id.txt_personal_my_address);
            txtRemove = itemView.findViewById(R.id.txt_remove);
        }
    }
}

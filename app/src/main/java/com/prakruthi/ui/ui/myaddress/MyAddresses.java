package com.prakruthi.ui.ui.myaddress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.prakruthi.R;
import com.prakruthi.ui.APIs.GetDeliveryAddressDetails;
import com.prakruthi.ui.ui.home.address.Address_BottomSheet_Recycler_Adaptor_Model;

import java.util.ArrayList;

public class MyAddresses extends AppCompatActivity implements GetDeliveryAddressDetails.DeliveryAddressListener {

    RecyclerView myAddresses_personal_address_recyclerview_List;
    AppCompatButton btn_add_new_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addresses);
        myAddresses_personal_address_recyclerview_List = findViewById(R.id.myAddresses_personal_address_recyclerview_List);
        btn_add_new_address = findViewById(R.id.btn_add_new_address);
        btn_add_new_address.setOnClickListener(v -> {

        });
        GetDeliveryAddressDetails getDeliveryAddressDetails = new GetDeliveryAddressDetails(this);
        getDeliveryAddressDetails.execute();
    }

    @Override
    public void onDeliveryAddressLoaded(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressList) {
        myAddresses_personal_address_recyclerview_List.setLayoutManager(new LinearLayoutManager(this));
        myAddresses_personal_address_recyclerview_List.setAdapter(new MyAddressesAdaptor(addressList));
    }
}
package com.prakruthi.ui.ui.profile.myaddress;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.prakruthi.R;
import com.prakruthi.ui.APIs.GetDeliveryAddressDetails;
import com.prakruthi.ui.APIs.SaveDeliveryAddressDetails;
import com.prakruthi.ui.misc.Loading;
import com.prakruthi.ui.ui.home.address.Address_BottomSheet_Recycler_Adaptor_Model;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;

import java.util.ArrayList;

public class MyAddresses extends AppCompatActivity implements GetDeliveryAddressDetails.DeliveryAddressListener , SaveDeliveryAddressDetails.OnSaveDeliveryAddressApiHits {

    RecyclerView myAddresses_personal_address_recyclerview_List;
    AppCompatButton btn_add_new_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addresses);
        myAddresses_personal_address_recyclerview_List = findViewById(R.id.myAddresses_personal_address_recyclerview_List);
        btn_add_new_address = findViewById(R.id.btn_add_new_address);
        btn_add_new_address.setOnClickListener(v -> {
            // Create an AlertDialog Builder
            AlertDialog.Builder builder = new AlertDialog.Builder(MyAddresses.this);
            builder.setTitle("Add New Address");

            // Set the custom layout for the dialog
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_address, null);
            builder.setView(dialogView);

            // Find the EditText fields and checkbox in the dialog layout
            EditText nameEditText = dialogView.findViewById(R.id.edittext_name);
            EditText cityEditText = dialogView.findViewById(R.id.edittext_city);
            EditText stateEditText = dialogView.findViewById(R.id.edittext_state);
            EditText countryEditText = dialogView.findViewById(R.id.edittext_country);
            EditText addressEditText = dialogView.findViewById(R.id.edittext_address);
            EditText postalCodeEditText = dialogView.findViewById(R.id.edittext_postal_code);
            CheckBox defaultCheckBox = dialogView.findViewById(R.id.checkbox_default);

            // Set the positive and negative buttons
            builder.setPositiveButton("Save", null); // Set null initially

            builder.setNegativeButton("Cancel", (dialog, which) -> {
                // Handle the cancel button click
                // Dismiss the dialog
                dialog.dismiss();
            });

            // Create and show the dialog
            AlertDialog dialog = builder.create();

            // Override the onClickListener for the positive button
            dialog.setOnShowListener(dialogInterface -> {
                Button saveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                saveButton.setOnClickListener(view -> {
                    // Handle the save button click
                    String name = nameEditText.getText().toString();
                    String city = cityEditText.getText().toString();
                    String state = stateEditText.getText().toString();
                    String country = countryEditText.getText().toString();
                    String address = addressEditText.getText().toString();
                    String postalCode = postalCodeEditText.getText().toString();
                    boolean isDefault = defaultCheckBox.isChecked();

                    // Check if any of the fields are empty
                    boolean hasError = false;
                    if (name.isEmpty()) {
                        nameEditText.setError("Name is required");
                        hasError = true;
                    }
                    if (city.isEmpty()) {
                        cityEditText.setError("City is required");
                        hasError = true;
                    }
                    if (state.isEmpty()) {
                        stateEditText.setError("State is required");
                        hasError = true;
                    }
                    if (country.isEmpty()) {
                        countryEditText.setError("Country is required");
                        hasError = true;
                    }
                    if (address.isEmpty()) {
                        addressEditText.setError("Address is required");
                        hasError = true;
                    }
                    if (postalCode.isEmpty()) {
                        postalCodeEditText.setError("Postal Code is required");
                        hasError = true;
                    }

                    if (!hasError) {
                        // All fields are properly filled, perform the save operation
                        SaveDeliveryAddressDetails saveDeliveryAddressDetails = new SaveDeliveryAddressDetails(this, name, city, state, country, address, postalCode, boolToInt(isDefault));
                        saveDeliveryAddressDetails.HitApi();
                        Loading.show(this);
                        // Dismiss the dialog
                        dialog.dismiss();
                    }
                });
            });

            dialog.show();
        });


        GetDeliveryAddressDetails getDeliveryAddressDetails = new GetDeliveryAddressDetails(this);
        getDeliveryAddressDetails.execute();
    }

    public int boolToInt(boolean value) {
        return value ? 1 : 0;
    }

    @Override
    public void onDeliveryAddressLoaded(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressList) {
        myAddresses_personal_address_recyclerview_List.setLayoutManager(new LinearLayoutManager(this));
        myAddresses_personal_address_recyclerview_List.setAdapter(new MyAddressesAdaptor(addressList));
    }

    @Override
    public void onSaveDeliveryAddress(String message) {
        runOnUiThread( () -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            GetDeliveryAddressDetails getDeliveryAddressDetails = new GetDeliveryAddressDetails(this);
            getDeliveryAddressDetails.execute();
            PopupDialog.getInstance(this)
                    .setStyle(Styles.SUCCESS)
                    .setHeading("Well Done")
                    .setDescription("Successfully"+
                            " Added New Address")
                    .setCancelable(false)
                    .showDialog(new OnDialogButtonClickListener() {
                        @Override
                        public void onDismissClicked(Dialog dialog) {
                            super.onDismissClicked(dialog);
                        }
                    });
            Loading.hide();
        });
    }

    @Override
    public void onSaveDeliveryAddressApiError(String error) {
        runOnUiThread( () -> Toast.makeText(this, error, Toast.LENGTH_SHORT).show());
    }
}
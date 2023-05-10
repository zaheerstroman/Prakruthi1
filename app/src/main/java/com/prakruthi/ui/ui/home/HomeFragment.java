package com.prakruthi.ui.ui.home;

import static com.google.firebase.messaging.Constants.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.prakruthi.R;
import com.prakruthi.databinding.FragmentHomeBinding;
import com.prakruthi.ui.Variables;
import com.prakruthi.ui.misc.Loading;
import com.prakruthi.ui.ui.home.category.HomeCategoryRecyclerAdaptor;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class HomeFragment extends Fragment {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private FragmentHomeBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        SetScreenViews();
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void SetScreenViews() {
        if (Variables.address.isEmpty() || Variables.address.equals("null")) {
            binding.DeleverHomeLocation.setText("Choose Location");
            binding.DeleverHomeLocation.setOnClickListener(v -> {
                showGpsDialog();
                if (GetPermission()) {
                    if (IsGpsEnabled()) {
                        getCurrentLocation(locationString -> {
                            binding.DeleverHomeLocation.setText(locationString);
                        });
                    }

                }
            });
        }
        else
            binding.DeleverHomeLocation.setText(Variables.address);
        binding.HomeCategoryRecyclerview.showShimmerAdapter();
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            binding.HomeCategoryRecyclerview.hideShimmerAdapter();
        },2000);
    }

    public boolean GetPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
            return false;
        }
        else return true;
    }
    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission granted, do something
                IsGpsEnabled();
            } else {
                Toast.makeText(requireContext(), "Location Permission Required", Toast.LENGTH_SHORT).show();
                // permission denied, do something else
            }
        }
    }

    public boolean IsGpsEnabled() {
        LocationManager locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);
        boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!isGpsEnabled) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Enable GPS");
            builder.setMessage("Please enable GPS to use this feature.");
            builder.setPositiveButton("Settings", (dialog, which) -> {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return isGpsEnabled;
    }
    @SuppressLint("MissingPermission")
    public void getCurrentLocation(Consumer<String> callback) {
        Loading.show(requireContext());
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (addresses != null && addresses.size() > 0) {
                        Address address = addresses.get(0);
                        String area = address.getSubLocality();
                        String city = address.getLocality();
                        String pincode = address.getPostalCode();
                        String locationString = "Deliver to " + Variables.name + " - " + area + ", " + city + " " + pincode;
                        callback.accept(locationString);
                        Loading.hide();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Loading.hide();
                }
            } else {
                Log.d(TAG, "Location is null");
                Toast.makeText(requireContext(), "Error Fetching Location", Toast.LENGTH_SHORT).show();
                Loading.hide();
            }
        });
    }

    private void showGpsDialog() {
        // Inflate the layout for the dialog
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.choose_location_bottom_dialog, null);

        // Create the bottom sheet dialog
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        dialog.setContentView(dialogView);

        // Show the dialog
        dialog.show();
    }





}
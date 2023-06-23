package com.prakruthi.ui.ui.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.prakruthi.R;
import com.prakruthi.databinding.FragmentProfileBinding;
import com.prakruthi.ui.APIs.FeedBackApi;
import com.prakruthi.ui.APIs.GetRecentViewProductsAPI;
import com.prakruthi.ui.APIs.MyOrders;
import com.prakruthi.ui.Login;
import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.profile.myaddress.MyAddresses;
import com.prakruthi.ui.ui.profile.myorders.MyOrdersActivity;
import com.prakruthi.ui.ui.profile.mypayments.MyPaymentsActivity;
import com.prakruthi.ui.ui.profile.mypayments.MyPaymentsModal;
import com.prakruthi.ui.ui.profile.order_qty.OrderQtyActivity;
import com.prakruthi.ui.ui.profile.recentProducts.RecentProductAdaptor;
import com.prakruthi.ui.ui.profile.recentProducts.RecentProductModel;

import java.util.ArrayList;

public class ProfileFragment extends Fragment implements FeedBackApi.OnFeedbackItemAPiHit , GetRecentViewProductsAPI.OnGetRecentViewProductsAPIHit {


    private FragmentProfileBinding binding;
    public SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false);

        sharedPreferences = requireActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        SetTextViews();
        SetClickListeners();

        binding.Logout.setOnClickListener(v -> {
            Variables.clear();
            // Get SharedPreferences.Editor object
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("rememberMe",false);
            // Apply changes
            editor.apply();
            startActivity(new Intent(requireContext(), Login.class));
            requireActivity().finish();
        });

       return binding.getRoot();
    }

    public void FeedBackDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Feedback");

        // Create the EditText
        final EditText editText = new EditText(requireContext());
        builder.setView(editText);

        // Add the submit button
        builder.setPositiveButton("Submit", null); // Set the click listener to null for now

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        // Get the button from the dialog's view
        Button submitButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        submitButton.setOnClickListener(view -> {
            // Handle the submit button click
            String feedback = editText.getText().toString();

            if (feedback.isEmpty()) {
                editText.setError("Feedback cannot be empty");
            } else {
                // Do something with the feedback
                FeedBackApi feedBackApi = new FeedBackApi(this, feedback);
                feedBackApi.FeedbackHitApi();
                dialog.dismiss(); // Dismiss the dialog after handling the click
            }
        });
    }
    public void SetTextViews()
    {
        binding.txtId.setText("");
        binding.txtId.append(String.valueOf(Variables.name));
        binding.ProfileHomeProductsRecycler.showShimmerAdapter();
        GetRecentViewProductsAPI getRecentViewProductsAPI = new GetRecentViewProductsAPI(this);
        getRecentViewProductsAPI.HitRecentApi();
    }
    public void SetClickListeners()
    {
        binding.tvMyAddress.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), MyAddresses.class));
        });
        binding.tvFeedback.setOnClickListener(v -> {
            FeedBackDialog();
        });
        if (Variables.departmentId != 2)
        {
            binding.tvMyWishlist.setText("quantity");
            binding.tvMyWishlist.setOnClickListener(v -> {
                startActivity(new Intent(requireContext(), OrderQtyActivity.class));
            });
        }
        else
        {
            binding.tvMyWishlist.setOnClickListener(v -> {
                BottomNavigationView bottomNavigationView;
                bottomNavigationView = (BottomNavigationView) requireActivity().findViewById(R.id.nav_view);
                bottomNavigationView.setSelectedItemId(R.id.navigation_wishlist);
            });
        }
        binding.tvMyOrders.setOnClickListener(v -> startActivity(new Intent(requireContext(), MyOrdersActivity.class)));
        binding.tvPayments.setOnClickListener(v -> startActivity(new Intent(requireContext(), MyPaymentsActivity.class)));
    }
    @Override
    public void OnProfileItemFeedback(String description) {
        try {
            requireActivity().runOnUiThread( () -> {
                Toast.makeText(requireContext(), description, Toast.LENGTH_SHORT).show();
            } );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void OnProfileItemFeedbackAPiGivesError(String error) {
        try {
            requireActivity().runOnUiThread( () -> {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            } );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void OnGetRecentViewProductsAPIGivesResult(ArrayList<RecentProductModel> recentProductModels) {
        requireActivity().runOnUiThread( () -> {
            binding.ProfileHomeProductsRecycler.setLayoutManager(new GridLayoutManager(requireContext(),2));
            binding.ProfileHomeProductsRecycler.setAdapter(new RecentProductAdaptor(recentProductModels));
            binding.ProfileHomeProductsRecycler.hideShimmerAdapter();
        } );
    }

    @Override
    public void OnGetRecentViewProductsAPIGivesError(String error) {
        requireActivity().runOnUiThread( () -> {
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            binding.ProfileHomeProductsRecycler.hideShimmerAdapter();
            binding.tvRecentProducts.setVisibility(View.GONE);
            binding.tvViewAll.setVisibility(View.GONE);
        } );

    }
}
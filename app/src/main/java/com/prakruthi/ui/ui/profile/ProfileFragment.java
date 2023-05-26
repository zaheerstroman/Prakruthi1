package com.prakruthi.ui.ui.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.prakruthi.R;
import com.prakruthi.databinding.FragmentProfileBinding;
import com.prakruthi.databinding.FragmentWishlistBinding;
import com.prakruthi.ui.APIs.FeedBackApi;
import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.myaddress.MyAddresses;
import com.prakruthi.ui.ui.wishlist.WishlistFragment;

import java.util.Objects;
import java.util.Set;

public class ProfileFragment extends Fragment implements FeedBackApi.OnFeedbackItemAPiHit {


    private FragmentProfileBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false);

        SetTextViews();
        SetClickListeners();


       return binding.getRoot();
    }


    public void FeedBackDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Feedback");

        // Create the EditText
        final EditText editText = new EditText(requireContext());
        builder.setView(editText);

        // Add the submit button
        builder.setPositiveButton("Submit", (dialog, which) -> {
            // Handle the submit button click
            String feedback = editText.getText().toString();
            // Do something with the feedback
            FeedBackApi feedBackApi = new FeedBackApi(this,feedback);
            feedBackApi.FeedbackHitApi();
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void SetTextViews()
    {
        binding.txtId.setText("ID : #");
        binding.txtId.append(String.valueOf(Variables.id));
    }
    public void SetClickListeners()
    {
        binding.tvMyAddress.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), MyAddresses.class));
        });
        binding.tvFeedback.setOnClickListener(v -> {
            FeedBackDialog();
        });
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
}
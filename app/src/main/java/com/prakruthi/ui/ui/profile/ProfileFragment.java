package com.prakruthi.ui.ui.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prakruthi.R;
import com.prakruthi.databinding.FragmentProfileBinding;
import com.prakruthi.ui.Variables;

public class ProfileFragment extends Fragment {


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
        binding.txtId.setText("ID : #");
        binding.txtId.append(String.valueOf(Variables.id));
       return binding.getRoot();
    }
}
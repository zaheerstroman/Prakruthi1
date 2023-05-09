package com.prakruthi.ui.ui.home;

import static com.google.firebase.messaging.Constants.TAG;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.prakruthi.databinding.FragmentHomeBinding;
import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.home.category.HomeCategoryRecyclerAdaptor;

public class HomeFragment extends Fragment {

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

    public void SetScreenViews()
    {
        if (Variables.address.isEmpty() || Variables.address.equals("null")) {
            binding.DeleverHomeLocation.setText("Choose Location");
            binding.DeleverHomeLocation.setOnClickListener(v -> {

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
    @Override
    public void onResume() {
        super.onResume();

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
package com.prakruthi.ui.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.prakruthi.databinding.FragmentHomeBinding;
import com.prakruthi.ui.ui.home.category.HomeCategoryRecyclerAdaptor;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.HomeCategoryRecyclerview.showShimmerAdapter();
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed((Runnable) () -> {
            binding.HomeCategoryRecyclerview.hideShimmerAdapter();
        },2000);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
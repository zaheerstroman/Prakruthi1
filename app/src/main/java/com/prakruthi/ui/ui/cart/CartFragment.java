package com.prakruthi.ui.ui.cart;

import static androidx.fragment.app.FragmentManager.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prakruthi.databinding.FragmentCartBinding;
import com.prakruthi.ui.APIs.GetCartDetails;

import java.util.ArrayList;

public class CartFragment extends Fragment implements GetCartDetails.OnDataFetchedListener{

    private FragmentCartBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentCartBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        final RecyclerView recyclerviewList = binding.cartRecyclerviewList;

        getCartDetails();

        return root;
    }

    public void getCartDetails()
    {
        binding.cartRecyclerviewList.showShimmerAdapter();
        GetCartDetails getCartDetails = new GetCartDetails(this);
        getCartDetails.fetchData();
    }


    public void onStart() {
        super.onStart();
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
    public void onCartListFetched(ArrayList<CartModal> cartModals) {
        try {
            requireActivity().runOnUiThread(() -> {
                binding.cartRecyclerviewList.hideShimmerAdapter();
                binding.cartRecyclerviewList.setLayoutManager(new LinearLayoutManager(requireContext()));
                binding.cartRecyclerviewList.setAdapter(new CartRecyclerAdaptor(requireContext(),cartModals));
            });
        }
        catch (Exception e)
        {
            Log.e("TAG", e.toString() );
        }

    }

    @Override
    public void onDataFetchError(String error) {
        requireActivity().runOnUiThread(()->{
            binding.cartRecyclerviewList.hideShimmerAdapter();
            Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        });
    }
}
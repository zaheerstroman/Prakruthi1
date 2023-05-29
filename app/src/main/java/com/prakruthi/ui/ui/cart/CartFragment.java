package com.prakruthi.ui.ui.cart;

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
import com.prakruthi.ui.APIs.AddToCart;
import com.prakruthi.ui.APIs.DeleteCartDetails;
import com.prakruthi.ui.APIs.GetCartDetails;
import com.prakruthi.ui.APIs.RemoveAllCartDetails;
import com.prakruthi.ui.misc.Loading;
import java.util.ArrayList;

public class CartFragment extends Fragment implements GetCartDetails.OnDataFetchedListener , AddToCart.OnDataFetchedListner , DeleteCartDetails.OnCartItemDeleteAPiHit , RemoveAllCartDetails.OnCartItemRemoveAllAPiHit {

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

        binding.txtRemoveall.setOnClickListener(v -> {
            RemoveAllCartDetails removeAllCartDetails = new RemoveAllCartDetails(this);
            removeAllCartDetails.RemoveAllHitApi();
        });

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
                binding.cartRecyclerviewList.setAdapter(new CartRecyclerAdaptor(requireContext(),cartModals,this, this));
                binding.SubtotalPrice.setText(String.valueOf(CartModal.cartAmount));
                binding.CheckoutButton.setVisibility(View.VISIBLE);
            });
        }
        catch (Exception e)
        {
            Log.e("TAG", e.toString() );
        }

    }
    @Override
    public void onDataFetchError(String error) {
        try {
            requireActivity().runOnUiThread(()->{
                try {
                    binding.cartRecyclerviewList.hideShimmerAdapter();
                    binding.NesterScrollViewCart.setVisibility(View.GONE);
                    binding.CheckoutButton.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    @Override
    public void OnCarteditDataFetched(String Message) {
        requireActivity().runOnUiThread(() -> {
            Loading.hide();
            getCartDetails();
        });

    }

    @Override
    public void OnAddtoCartDataFetched(String Message) {
        requireActivity().runOnUiThread(Loading::hide);
    }

    @Override
    public void OnErrorFetched(String Error) {
        requireActivity().runOnUiThread(() -> {
            Loading.hide();
            Toast.makeText(requireContext(), Error, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void OnCartItemDeleted(String message) {
        requireActivity().runOnUiThread(() -> {
            Loading.hide();
            getCartDetails();
        });
    }

    @Override
    public void OnCartItemDeleteAPiGivesError(String error) {
        requireActivity().runOnUiThread(() -> {
            Loading.hide();
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            getCartDetails();
        });
    }

    @Override
    public void OnCartItemRemoveAll(String message) {
        requireActivity().runOnUiThread(this::getCartDetails);
    }

    @Override
    public void OnCartItemRemoveAllAPiGivesError(String error) {

    }
}
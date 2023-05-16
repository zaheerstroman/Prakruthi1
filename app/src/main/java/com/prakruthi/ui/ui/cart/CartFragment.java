package com.prakruthi.ui.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.prakruthi.Response.CartDataResponse;
import com.prakruthi.databinding.FragmentCartBinding;
import com.prakruthi.network.APIClient;
import com.prakruthi.network.ApiInterface;
import com.prakruthi.ui.APIs.GetCartDetails;
import com.prakruthi.ui.ui.cart.category.CartModal;
import com.prakruthi.ui.ui.cart.category.CartRecyclerAdaptor;
import com.prakruthi.ui.ui.home.category.HomeCategoryModal;
import com.prakruthi.ui.ui.home.category.HomeCategoryRecyclerAdaptor;
import com.prakruthi.utils.CommonUtils;
import com.prakruthi.utils.Constants;
import com.prakruthi.utils.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment implements GetCartDetails.OnDataFetchedListener{

    private FragmentCartBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentCartBinding.inflate(inflater, container, false);

//        setContentView(binding.getRoot());
        View root = binding.getRoot();


//        final TextView textView = binding.textNotifications;
        final RecyclerView recyclerviewList = binding.cartRecyclerviewList;

//        getReportsData();
        getCartDetails();

        return root;
    }

    private void getReportsData() {

        CommonUtils.showLoading(requireContext(), "Please Wait", false);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", SharedPrefs.getInstance(requireContext()).getString(Constants.USER_ID));
        jsonObject.addProperty("token", SharedPrefs.getInstance(requireContext()).getString(Constants.TOKEN));
//        Call<GetCartDetailsResponse> call = apiInterface.getReportsData(jsonObject);
        Call<CartModal> call = apiInterface.getReportsData(jsonObject);
//        Call<CartDataResponse> call = apiInterface.getReportsData(jsonObject);

//
        call.enqueue(new Callback<CartModal>() {
            @Override
            public void onResponse(Call<CartModal> call, Response<CartModal> response) {
                try {
                    CommonUtils.hideLoading();
//                    binding.recyclerviewList.setAdapter(new UploadAdapter(UploadActivity.this,response.body().getData()));
                    binding.cartRecyclerviewList.setLayoutManager(new LinearLayoutManager(requireContext()));
                    assert response.body() != null;
//                    binding.cartRecyclerviewList.setAdapter(new CartRecyclerAdaptor(requireContext(),response.body().getCartData()));


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<CartModal> call, Throwable t) {
                CommonUtils.hideLoading();
            }
        });

//        getHomeDetails();
        getCartDetails();

    }

    public void getCartDetails()
    {
//        binding.CartRecyclerview.showShimmerAdapter();
        binding.cartRecyclerviewList.showShimmerAdapter();
        GetCartDetails getCartDetails = new GetCartDetails(requireContext(),this);
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
    public void onCartListFetched(ArrayList<CartDataResponse> cartModals) {
        requireActivity().runOnUiThread(() -> {
            binding.cartRecyclerviewList.hideShimmerAdapter();
            binding.cartRecyclerviewList.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false));
            binding.cartRecyclerviewList.setAdapter(new CartRecyclerAdaptor(cartModals));
        });

    }

    @Override
    public void onDataFetchError(String error) {
        requireActivity().runOnUiThread(()->{
            binding.cartRecyclerviewList.hideShimmerAdapter();
            Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        });
    }
}
package com.prakruthi.ui.APIs;

import android.content.Context;
import android.os.AsyncTask;

import com.prakruthi.ui.ui.home.banners.HomeBannerModel;
import com.prakruthi.ui.ui.home.category.HomeCategoryModal;
import com.prakruthi.ui.ui.home.products.HomeProductModel;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GetHomeDetails {
    private Context mContext;
    private OnDataFetchedListener mListener;

    public GetHomeDetails(Context context, OnDataFetchedListener listener) {
        mContext = context;
        mListener = listener;
    }

    public void fetchData() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new GetDataTask());
    }

    private class GetDataTask implements Runnable {

        @Override
        public void run() {
            //Creating array for parameters
            String[] field = new String[2];
            field[0] = "param-1";
            field[1] = "param-2";
            //Creating array for data
            String[] data = new String[2];
            data[0] = "data-1";
            data[1] = "data-2";
            PutData putData = new PutData("https://projects.vishnusivadas.com/AdvancedHttpURLConnection/putDataTest.php", "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    handleResponse(result);
                } else {
                    handleError("Failed to fetch data");
                }
            } else {
                handleError("Failed to connect to server");
            }
        }

        private void handleResponse(String result) {
            if (result != null) {
                try {
                    JSONObject jsonResponse = new JSONObject(result);
                    JSONArray bannerList = jsonResponse.getJSONArray("banner_list");
                    JSONArray categoryList = jsonResponse.getJSONArray("category_list");
                    JSONArray productList = jsonResponse.getJSONArray("products_list");

                    // Create lists
                    List<HomeCategoryModal> homeCategoryModals = new ArrayList<>();
                    List<HomeBannerModel> homeBannerModels = new ArrayList<>();
                    List<HomeProductModel> homeProductModels = new ArrayList<>();

                    // Populate lists
                    for (int i = 0; i < categoryList.length(); i++) {
                        JSONObject category = categoryList.getJSONObject(i);
                        int id = category.getInt("id");
                        String name = category.getString("name");
                        String attachment = category.getString("attachment");
                        homeCategoryModals.add(new HomeCategoryModal(id, name, attachment));
                    }

                    for (int i = 0; i < bannerList.length(); i++) {
                        JSONObject banners = bannerList.getJSONObject(i);
                        int id = banners.getInt("id");
                        String attachement = banners.getString("attachment");
                        homeBannerModels.add(new HomeBannerModel(id,attachement));
                    }

                    for (int i = 0; i < productList.length(); i++) {
                        JSONObject product = productList.getJSONObject(i);
                        int id = product.getInt("id");
                        String name = product.getString("name");
                        String description = product.getString("description");
                        String attachment = product.getString("attachment");
                        homeProductModels.add(new HomeProductModel(id, name, description, attachment));
                    }

                    // Call listener with all three lists
                    mListener.onDataFetched(homeCategoryModals);
                    mListener.onBannerListFetched(homeBannerModels);
                    mListener.onProductListFetched(homeProductModels);
                } catch (JSONException e) {
                    e.printStackTrace();
                    handleError("Failed to parse data");
                }
            } else {
                handleError("Failed to fetch data");
            }
        }


        private void handleError(String error) {
            mListener.onDataFetchError(error);
        }
    }

    public interface OnDataFetchedListener {
        void onDataFetched(List<HomeCategoryModal> homeCategoryModals);
        void onBannerListFetched(List<HomeBannerModel> homeBannerModels);
        void onProductListFetched(List<HomeProductModel> homeProductModels);
        void onDataFetchError(String error);
    }


}

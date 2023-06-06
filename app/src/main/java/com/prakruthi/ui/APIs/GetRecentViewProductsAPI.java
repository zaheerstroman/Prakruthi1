package com.prakruthi.ui.APIs;

import com.prakruthi.ui.ui.profile.recentProducts.RecentProductModel;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GetRecentViewProductsAPI {

    private OnGetRecentViewProductsAPIHit mListner;

    public GetRecentViewProductsAPI(OnGetRecentViewProductsAPIHit mListner)
    {
        this.mListner = mListner;
    }

    public void HitRecentApi()
    {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new HitAPi());
    }

    private static class HitAPi implements Runnable
    {

        @Override
        public void run() {

        }
    }


    public interface OnGetRecentViewProductsAPIHit
    {
        void OnGetRecentViewProductsAPIGivesResult(ArrayList<RecentProductModel> recentProductModels);
        void OnGetRecentViewProductsAPIGivesError(String recentProductModels);
    }
}

package com.prakruthi.ui.ui.search;

import static com.google.firebase.messaging.Constants.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.prakruthi.R;
import com.prakruthi.ui.APIs.SearchProductApi;
import com.prakruthi.ui.ui.productPage.ProductModel;

import java.util.List;

public class SearchPage extends AppCompatActivity implements SearchProductApi.OnSearchResultApiHit{

    EditText editText;
    ShimmerRecyclerView searchRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        AppCompatButton back = findViewById(R.id.search_back_btn);
        back.setOnClickListener(v -> super.onBackPressed());
        searchRecyclerView = findViewById(R.id.SearchRecyclerView);
        editText = findViewById(R.id.Search);
        editText.requestFocus();
        if (editText.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        editText.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            performSearch();
            return false;
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                performSearch();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void performSearch() {
        if (editText.getText().toString().isEmpty())
        {
            ObjectAnimator.ofFloat(editText, "translationX", 0, -20, 20, -10, 10, -20, 10, -20, 20, 0).start();
            editText.requestFocus();
        }
        else
        {
            searchRecyclerView.setVisibility(View.VISIBLE);
            searchRecyclerView.showShimmerAdapter();
            SearchProductApi searchProductApi = new SearchProductApi(this,editText.getText().toString());
            searchProductApi.HitSearchApi();
        }
    }

    @Override
    public void OnSearchResult(List<SearchModle> product) {
        runOnUiThread(() -> {
            searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            searchRecyclerView.hideShimmerAdapter();
            searchRecyclerView.setAdapter(new SearchAdaptor(product));
        });
    }

    @Override
    public void OnSearchResultApiGivesError(String error) {
        runOnUiThread(() -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            searchRecyclerView.hideShimmerAdapter();
        });
    }
}
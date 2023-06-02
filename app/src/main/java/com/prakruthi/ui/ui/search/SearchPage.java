package com.prakruthi.ui.ui.search;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.prakruthi.R;
import com.prakruthi.ui.APIs.SearchProductApi;
import java.util.List;

import co.ceryle.radiorealbutton.RadioRealButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;

public class SearchPage extends AppCompatActivity implements SearchProductApi.OnSearchResultApiHit{

    EditText editText;

    AppCompatButton back;
    TextView SortBy , filters;
    ShimmerRecyclerView searchRecyclerView;
    String order = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        SetViews();
        ClickListners();
        SearchLogic();

    }

    private void SetViews()
    {
        back = findViewById(R.id.search_back_btn);
        searchRecyclerView = findViewById(R.id.SearchRecyclerView);
        editText = findViewById(R.id.Search);
        SortBy = findViewById(R.id.SortBy);
        filters = findViewById(R.id.filters);
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private void ClickListners()
    {
        back.setOnClickListener(v -> super.onBackPressed());
        SortBy.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(SearchPage.this);
            View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_layout, null);


            RadioButton radioRecommended = bottomSheetView.findViewById(R.id.radioRecommended);
            RadioButton radioAscending = bottomSheetView.findViewById(R.id.radioAscending);
            RadioButton radioDescending = bottomSheetView.findViewById(R.id.radioDescending);

            radioAscending.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    // Handle the "Sort by Ascending" radio button selection
                    // Perform sorting operation in ascending order
                    order = "asc";
                    performSearch();
                    bottomSheetDialog.dismiss();
                }
            });

            radioDescending.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    // Handle the "Sort by Descending" radio button selection
                    // Perform sorting operation in descending order
                    order = "desc";
                    performSearch();
                    bottomSheetDialog.dismiss();
                }
            });

            radioRecommended.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    // Handle the "Sort by Descending" radio button selection
                    // Perform sorting operation in descending order
                    order = "";
                    performSearch();
                    bottomSheetDialog.dismiss();
                }
            });

            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
        });
        filters.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(SearchPage.this);
            View bottomSheetView = getLayoutInflater().inflate(R.layout.filters_bottom_sheet, null);

            RadioRealButtonGroup group = bottomSheetView.findViewById(R.id.group);

            RadioRealButton radioRealButton = bottomSheetView.findViewById(R.id.button5);
            RadioRealButton radioRealButton2 = bottomSheetView.findViewById(R.id.button5);
            RadioRealButton radioRealButton3 = bottomSheetView.findViewById(R.id.button5);
            ViewGroup parent = (ViewGroup) radioRealButton.getParent();
            if (parent != null) {
                parent.removeView(radioRealButton);
            }

            radioRealButton.setId(View.generateViewId());
            radioRealButton.setVisibility(View.VISIBLE);
            group.addView(radioRealButton);
            group.addView(radioRealButton2);
            group.addView(radioRealButton3);

            // onClickButton listener detects any click performed on buttons by touch
            group.setOnClickedButtonListener((button, position) -> Toast.makeText(SearchPage.this, "Clicked! Position: " + button.getText(), Toast.LENGTH_SHORT).show());

            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
        });
    }
    private void SearchLogic()
    {
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
            SearchProductApi searchProductApi = new SearchProductApi(this,editText.getText().toString(),order);
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
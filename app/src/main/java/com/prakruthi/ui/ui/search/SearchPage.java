package com.prakruthi.ui.ui.search;

import static com.google.firebase.messaging.Constants.MessageNotificationKeys.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.prakruthi.R;
import com.prakruthi.ui.APIs.GetFilterApi;
import com.prakruthi.ui.APIs.SearchProductApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import co.ceryle.radiorealbutton.RadioRealButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;

public class SearchPage extends AppCompatActivity implements SearchProductApi.OnSearchResultApiHit, GetFilterApi.OnFilterAPiHit {

    EditText editText;

    AppCompatButton back;
    TextView SortBy, filters;
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

    private void SetViews() {
        back = findViewById(R.id.search_back_btn);
        searchRecyclerView = findViewById(R.id.SearchRecyclerView);
        editText = findViewById(R.id.Search);
        SortBy = findViewById(R.id.SortBy);
        filters = findViewById(R.id.filters);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void ClickListners() {
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
            GetFilterApi getFilterApi = new GetFilterApi(this);
            getFilterApi.HitApi();
        });
    }

    private void SearchLogic() {
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
        if (editText.getText().toString().isEmpty()) {
            ObjectAnimator.ofFloat(editText, "translationX", 0, -20, 20, -10, 10, -20, 10, -20, 20, 0).start();
            editText.requestFocus();
        } else {
            searchRecyclerView.setVisibility(View.VISIBLE);
            searchRecyclerView.showShimmerAdapter();
            SearchProductApi searchProductApi = new SearchProductApi(this, editText.getText().toString(), order);
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

    public void ShowFiltrType(JSONObject result, RadioGroup group) {
        try
        {
            JSONArray types = result.getJSONArray("product_type_details");
            for (int i = 0; i < types.length(); i++) {
                JSONObject type = types.getJSONObject(i);
                RadioButton radioButton = new RadioButton(this);
                radioButton.setId(View.generateViewId());
                radioButton.setText(type.getString("type"));
                radioButton.setButtonDrawable(null);

                // Create LayoutParams with desired width and height
                int backgroundWidth = 200; // Example value, adjust as needed
                int backgroundHeight = 80; // Example value, adjust as needed
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(backgroundWidth, backgroundHeight);

                // Set margins
                int leftMargin = 16; // Example value, adjust as needed
                int topMargin = 8; // Example value, adjust as needed
                int rightMargin = 16; // Example value, adjust as needed
                int bottomMargin = 8; // Example value, adjust as needed

                // Create MarginLayoutParams and set margins
                ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(layoutParams);
                marginLayoutParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

                radioButton.setLayoutParams(marginLayoutParams);

                // Set the selector drawable as the background
                radioButton.setBackgroundResource(R.drawable.custom_button_selector);
                radioButton.setTextSize(20);

                radioButton.setGravity(Gravity.CENTER_HORIZONTAL);
                // Customize background tint for selected state
                int selectedTint = ContextCompat.getColor(this, R.color.Secondary_less);
                radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        buttonView.setBackgroundTintList(ColorStateList.valueOf(selectedTint));
                    } else {
                        buttonView.setBackgroundTintList(null);
                    }
                });

                // Add the RadioButton to the RadioGroup
                group.addView(radioButton);


            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void OnFilterApiSuccess(JSONObject result) {
        runOnUiThread(() -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(SearchPage.this);
            View bottomSheetView = getLayoutInflater().inflate(R.layout.filters_bottom_sheet, null);

            RadioGroup group = bottomSheetView.findViewById(R.id.group);


            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();

            ShowFiltrType(result, group, bottomSheetView);
        });
    }

    @Override
    public void OnFilterApiFailed(String result) {

    }
}
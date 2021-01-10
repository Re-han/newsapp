package com.r.newsapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.ChipGroup;
import com.r.newsapp.ApiClient;
import com.r.newsapp.Article;
import com.r.newsapp.Details;
import com.r.newsapp.R;
import com.r.newsapp.adapter.CustomAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.TELEPHONY_SERVICE;


public class Category_frag extends Fragment {
    private List<Article> list;
    String s = "health";
    Retrofit retrofit;
    String KEY = "YOUR_KEY";
    ProgressBar p;
    RecyclerView r;
    int pages = 20;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.category_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ChipGroup chipGroup = view.findViewById(R.id.categoryChips);
        p = view.findViewById(R.id.everything_progress);

        r = (RecyclerView) view.findViewById(R.id.everything_recycle);
        r.hasFixedSize();
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(TELEPHONY_SERVICE);
        String countryCode = tm.getSimCountryIso();
        r.setLayoutManager(new LinearLayoutManager(getContext()));
        request(s, countryCode, pages);

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @SuppressLint({"ResourceType", "NonConstantResourceId"})
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.health:
                        s = "health";
                        request(s, countryCode, pages);
                        break;
                    case R.id.business:
                        s = "business";
                        request(s, countryCode, pages);
                        break;
                    case R.id.science:
                        s = "science";
                        request(s, countryCode, pages);
                        break;
                    case R.id.technology:
                        s = "technology";
                        request(s, countryCode, pages);
                        break;
                    case R.id.sports:
                        s = "sports";
                        request(s, countryCode, pages);
                        break;
                    case R.id.entertainment:
                        s = "entertainment";
                        request(s, countryCode, pages);
                        break;
                }
            }
        });
    }

    public void request(String s, String countryCode, int numberOfPages) {
        ApiClient apiClient = new ApiClient(retrofit);
        Call<Details> detailsCall = apiClient.newsapi.getCategory(countryCode, s, numberOfPages, KEY);
        detailsCall.enqueue(new Callback<Details>() {
            @Override
            public void onResponse(Call<Details> call, Response<Details> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    p.setVisibility(View.INVISIBLE);
                    list = response.body().getArticles();
                    r.setAdapter(new CustomAdapter(list, getContext()));
                }
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), String.valueOf(response.code()) + "Server Error. Please try again Later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Details> call, Throwable t) {
                Toast.makeText(getContext(), "Unable to connect to server, Make sure you are connected to Internet", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
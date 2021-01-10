package com.r.newsapp.fragments;

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

import com.r.newsapp.Article;
import com.r.newsapp.Details;
import com.r.newsapp.R;
import com.r.newsapp.adapter.CustomAdapter;
import com.r.newsapp.newsapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.TELEPHONY_SERVICE;


public class Headlines_frag extends Fragment {
    private List<Article> list;
    String KEY = "YOUR_KEY";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.headlines_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        ProgressBar p = view.findViewById(R.id.headline_progressBar);
        RecyclerView r = view.findViewById(R.id.headlines_recycle);
        r.hasFixedSize();
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(TELEPHONY_SERVICE);
        String countryCode = tm.getSimCountryIso();
        r.setLayoutManager(new LinearLayoutManager(getContext()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        newsapi newsapi = retrofit.create(newsapi.class);
        Call<Details> detailsCall = newsapi.getHeadlines(countryCode.toLowerCase(), KEY);
        detailsCall.enqueue(new Callback<Details>() {
            @Override
            public void onResponse(Call<Details> call, Response<Details> response) {
                p.setIndeterminate(true);
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    p.setVisibility(View.INVISIBLE);
                    list = response.body().getArticles();
                    r.setAdapter(new CustomAdapter(list,getContext()));
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
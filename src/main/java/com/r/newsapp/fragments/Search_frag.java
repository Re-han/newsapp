package com.r.newsapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import static android.content.Context.MODE_PRIVATE;


public class Search_frag extends Fragment {
    private List<Article> list;
    ImageView imageView;
    TextView textView;
    EditText editText;
    Retrofit retrofit;
    RecyclerView r;
    SharedPreferences sp;
    String KEY = "YOUR_KEY";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.search_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.search);
        r = (RecyclerView) view.findViewById(R.id.search_recycle);
        r.hasFixedSize();
        textView = view.findViewById(R.id.search_textView);
        imageView = view.findViewById(R.id.search_imageView);
        r.setLayoutManager(new LinearLayoutManager(getContext()));
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId & EditorInfo.IME_MASK_ACTION) == EditorInfo.IME_ACTION_DONE) {
                    if (editText.getText().toString().length() == 0) {
                        Toast t = Toast.makeText(getContext(), "Search field cannot be Empty", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.CENTER, 0, 0);
                        t.show();
                    } else {
                        String s = editText.getText().toString();
                        request(s);
                    }

                }
                return true;
            }

        });

    }

    public void request(String s) {

        ApiClient apiClient = new ApiClient(retrofit);
        Call<Details> detailsCall = apiClient.newsapi.getEverything(s, KEY);
        detailsCall.enqueue(new Callback<Details>() {
            @Override
            public void onResponse(Call<Details> call, Response<Details> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    sp = getContext().getSharedPreferences("search_pref", MODE_PRIVATE);
                    SharedPreferences.Editor ed = sp.edit();
                    ed.clear().apply();
                    imageView.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                    list = response.body().getArticles();
                    r.setAdapter(new CustomAdapter(list,getContext()));
                  if(list.size()==0)
                  {
                      imageView.setVisibility(View.VISIBLE);
                      textView.setVisibility(View.VISIBLE);
                      Toast.makeText(getContext(), "No \"keyword\" or \"Phrase\" found", Toast.LENGTH_SHORT).show();
                      editText.setText(null);
                  }
                } else {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getContext(), String.valueOf(response.code()) + " " + "Server Error. Please try again Later", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Details> call, Throwable t) {
                Toast.makeText(getContext(), "Unable to connect to server, Make sure you are connected to Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
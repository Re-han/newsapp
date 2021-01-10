package com.r.newsapp.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.r.newsapp.DatabaseHelper;
import com.r.newsapp.R;
import com.r.newsapp.adapter.saved_dataAdapter;

import java.util.ArrayList;


public class Saved_frag extends Fragment {
    private ProgressBar p;
    private RecyclerView r;
    private DatabaseHelper dbHelper;
    ArrayList<String> title, image, desc, url;
    ImageView imageView;
    TextView textView;
saved_dataAdapter saved_dataAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.saved_frag, container, false);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem item = menu.findItem(R.id.delete);
        if (item != null) {
            item.setVisible(true);
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = new DatabaseHelper(getContext());
        p = view.findViewById(R.id.saved_recycler_progess);
        r = view.findViewById(R.id.saved_recycler);
        imageView = view.findViewById(R.id.imageView_nodata);
        textView = view.findViewById(R.id.nodata);
        title = new ArrayList<>();
        image = new ArrayList<>();
        desc = new ArrayList<>();
        url = new ArrayList<>();
        getValues();
        r.computeVerticalScrollOffset();
        r.setAdapter(new saved_dataAdapter(getContext(),
                title,
                image,
                desc,
                url));
        r.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void getValues() {
        Cursor c = dbHelper.getAllArticles();
        if (c.getCount() == 0) {
            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            p.setVisibility(View.GONE);

        } else {
            p.setVisibility(View.GONE);
            while (c.moveToNext()) {
                title.add(c.getString(1));
                image.add(c.getString(2));
                desc.add(c.getString(3));
                url.add(c.getString(4));
            }
        }
    }

}



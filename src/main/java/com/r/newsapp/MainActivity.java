package com.r.newsapp;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.r.newsapp.fragments.Category_frag;
import com.r.newsapp.fragments.Headlines_frag;
import com.r.newsapp.fragments.Saved_frag;
import com.r.newsapp.fragments.Search_frag;
import com.r.newsapp.fragments.Settings_frag;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        BottomNavigationView bottomNavigationView = findViewById(R.id.frag_replacer);
        toolbar.setTitle("Headlines");
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frag_holder, new Headlines_frag()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Menu menu = bottomNavigationView.getMenu();
                menu.findItem(R.id.category).setIcon(getResources().getDrawable(R.drawable.category));
                menu.findItem(R.id.headlines).setIcon(getResources().getDrawable(R.drawable.headlines));
                menu.findItem(R.id.saved).setIcon(getResources().getDrawable(R.drawable.saved));
                if (item.getItemId() == R.id.category) {
                    toolbar.setTitle("Category");
                    item.setIcon(getResources().getDrawable(R.drawable.category_selected));
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frag_holder, new Category_frag()).commit();
                }
                if (item.getItemId() == R.id.search) {
                    toolbar.setTitle("Search");
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frag_holder, new Search_frag()).commit();
                }
                if (item.getItemId() == R.id.headlines) {
                    toolbar.setTitle("Headlines");
                    item.setIcon(getResources().getDrawable(R.drawable.headlines_selected));
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frag_holder, new Headlines_frag()).commit();
                }
                if (item.getItemId() == R.id.saved) {
                    toolbar.setTitle("Saved");
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    item.setIcon(getResources().getDrawable(R.drawable.saved_selected));
                    fragmentManager.beginTransaction().replace(R.id.frag_holder, new Saved_frag()).commit();
                }
                if (item.getItemId() == R.id.settings) {
                    toolbar.setTitle("Settings");
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frag_holder, new Settings_frag()).commit();
                }
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.delete);
        if (item != null) {
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete) {
            DatabaseHelper db = new DatabaseHelper(this);
            Cursor c = db.getAllArticles();
            if (c.getCount() == 0) {
                Toast.makeText(this, "No Data Exist", Toast.LENGTH_SHORT).show();
            } else {
                db.deleteAll();
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }
    public boolean isOnline(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final android.net.NetworkInfo mobile = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isConnected()||mobile.isConnected()) {
            return true;
        }
        return false;
    }


}
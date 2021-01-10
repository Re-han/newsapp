package com.r.newsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.r.newsapp.Article;
import com.r.newsapp.DatabaseHelper;
import com.r.newsapp.R;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    List<Article> mylist;
    Context c;
    Animation animation;

    public CustomAdapter(List<Article> list, Context context) {
        this.c = context;
        this.mylist = list;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        DatabaseHelper databaseHelper = new DatabaseHelper(c);
        Article a = mylist.get(position);
        holder.desc.setText((a.getDescription()));
        holder._images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, a.getUrl());
                c.startActivity(Intent.createChooser(i, null));
            }
        });
        holder.title.setText(a.getTitle());
        Glide.with(c).load(a.getUrlToImage()).into(holder.i);
        holder._newslink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder customTabsIntent = new CustomTabsIntent.Builder();
                CustomTabsIntent tabsIntent = customTabsIntent.build();
                tabsIntent.launchUrl(c, Uri.parse(a.getUrl()));
            }
        });
        holder.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = databaseHelper.addItem(a.getTitle(), a.getUrlToImage(), a.getDescription(), a.getUrl());
                if (result) {
                    Toast.makeText(c, "Saved!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(c, "Article already saved", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView desc;
        TextView title;
        ConstraintLayout _newslink, constraintLayout;
        ImageView i;
        Button b;
        ImageView _images;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            animation = AnimationUtils.loadAnimation(c, R.anim.trasition_from_bottom);
            b = itemView.findViewById(R.id.save_img);
            i = itemView.findViewById(R.id.main_image);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
            _images = itemView.findViewById(R.id.share_img);
            desc = itemView.findViewById(R.id.description);
            title = itemView.findViewById(R.id.titleArticle);
            _newslink = itemView.findViewById(R.id.adapterLayout);
            i.setClipToOutline(true);
            animation.setDuration(500);
            constraintLayout.setAnimation(animation);
        }
    }
}

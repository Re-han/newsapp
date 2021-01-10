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
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.r.newsapp.DatabaseHelper;
import com.r.newsapp.R;
import com.r.newsapp.fragments.Saved_frag;

import java.util.ArrayList;

public class saved_dataAdapter extends RecyclerView.Adapter<saved_dataAdapter.MyHolder> {
    private Context context;
    private ArrayList title, image, desc, url;
    Animation animation;

    public saved_dataAdapter(Context context, ArrayList title, ArrayList image, ArrayList desc, ArrayList url) {
        this.context = context;
        this.title = title;
        this.image = image;
        this.desc = desc;
        this.url = url;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.saved_adapter, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        DatabaseHelper db = new DatabaseHelper(context);

        holder.title.setText(String.valueOf(title.get(position)));
        Glide.with(context).load(String.valueOf(image.get(position))).into(holder.i);
        holder.desc.setText(String.valueOf(desc.get(position)));

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, String.valueOf(url.get(position)));
                context.startActivity(Intent.createChooser(i, null));
            }
        });
        holder._newsLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder customTabsIntent = new CustomTabsIntent.Builder();
                CustomTabsIntent tabsIntent = customTabsIntent.build();
                tabsIntent.launchUrl(context, Uri.parse((String) url.get(position)));
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean deleted = db.deleteItem((String) title.get(position));
                if (deleted) {
                    notifyDataSetChanged();
                    AppCompatActivity a = (AppCompatActivity) context;
                    a.getSupportFragmentManager().beginTransaction().replace(R.id.frag_holder, new Saved_frag()).commit();
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView desc, title;
        ConstraintLayout _newsLink, constraintLayout;
        ImageView i;
        Button delete, share;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            animation = AnimationUtils.loadAnimation(context, R.anim.trasition_from_bottom);
            delete = itemView.findViewById(R.id.delete_img);
            constraintLayout = itemView.findViewById(R.id.save_constraintLayout);
            i = itemView.findViewById(R.id.main_image1);
            share = itemView.findViewById(R.id.share_img1);
            desc = itemView.findViewById(R.id.description1);
            title = itemView.findViewById(R.id.titleArticle1);
            _newsLink = itemView.findViewById(R.id.adapterLayout1);
            i.setClipToOutline(true);
            animation.setDuration(500);
            constraintLayout.setAnimation(animation);

        }
    }
}

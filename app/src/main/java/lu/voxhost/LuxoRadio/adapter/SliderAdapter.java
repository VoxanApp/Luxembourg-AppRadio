package com.andromob.amradio.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.andromob.amradio.R;
import com.andromob.amradio.models.Channels;
import com.andromob.amradio.utils.AdsManager;
import com.andromob.amradio.utils.Methods;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.MyViewHolder> {
    private final List<Channels> dataList;
    private ViewPager2 viewPager2;
    Activity activity;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView radio_icon, bg_blur_img;
        CardView sliderCard;
        ProgressBar progressBar;
        TextView radio_name;

        public MyViewHolder(View view) {
            super(view);
            bg_blur_img = view.findViewById(R.id.bg_blur_img);
            radio_icon = view.findViewById(R.id.radio_icon);
            sliderCard = view.findViewById(R.id.sliderCard);
            progressBar = view.findViewById(R.id.progressBar);
            radio_name = view.findViewById(R.id.radio_name);
        }
    }

    public SliderAdapter(Activity activity, List<Channels> addataList, ViewPager2 viewPager2) {
        this.activity = activity;
        this.dataList = addataList;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_banner_slider, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Channels row = dataList.get(position);
        Methods.loadImage(holder.radio_icon, row.getChannel_icon(), holder.progressBar);
        Glide.with(activity)
                .load(row.getChannel_icon())
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.radio_name.setText(row.getChannel_name());
                        return false;
                    }
                })
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(20, 2)))
                .into(holder.bg_blur_img);

        holder.sliderCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] strings = {
                        String.valueOf(row.getId()),
                        row.getChannel_name(),
                        row.getFrequency(),
                        row.getSource_url(),
                        row.getChannel_icon(),
                        row.getDescription(),
                        String.valueOf(row.getViews())
                };
                AdsManager.showInterAdOnClick(activity, strings);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

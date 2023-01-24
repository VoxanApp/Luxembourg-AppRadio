package com.andromob.amradio.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.andromob.amradio.R;
import com.andromob.amradio.fragments.ChannelFragment;
import com.andromob.amradio.models.Category;
import com.andromob.amradio.utils.AdsManager;
import com.andromob.amradio.utils.Methods;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> implements Filterable {
    private List<Category> dataList;
    private List<Category> dataListFiltered;
    Context context;
    private int type;

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView cat_name;
        CardView card_cat;
        ImageView cat_image;
        ProgressBar progressBar;

        MyViewHolder(View view) {
            super(view);
            cat_name = view.findViewById(R.id.cat_name);
            card_cat = view.findViewById(R.id.card_cat);
            cat_image = view.findViewById(R.id.cat_image);
            progressBar = view.findViewById(R.id.progressBar);
        }
    }

    public CategoryAdapter(Context context, List<Category> CategoryList, int type) {
        this.context = context;
        this.dataList = CategoryList;
        this.dataListFiltered = CategoryList;
        this.type = type;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        if (type == 0) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_category_recent, parent, false);
        }else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_category, parent, false);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Category row = dataListFiltered.get(position);
        holder.cat_name.setText(row.getCategory_name());
        Methods.loadImage(holder.cat_image, row.getCategory_image(), holder.progressBar);

        holder.card_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdsManager.showInterAd((Activity) context);
                Bundle bundle = new Bundle();
                bundle.putString("type", "Category");
                bundle.putInt("cat_id", row.getCat_id());
                bundle.putString("cat_name", row.getCategory_name());
                ChannelFragment frgmt = new ChannelFragment();
                frgmt.setArguments(bundle);
                Methods.addSelectedFragment(frgmt,(Activity) context);
            }
        });
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    dataListFiltered = dataList;
                } else {
                    List<Category> filteredList = new ArrayList<>();
                    for (Category row : dataList) {
                        if (row.getCategory_name().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    dataListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dataListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dataListFiltered = (ArrayList<Category>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return dataListFiltered.size();
    }
}

package lu.voxhost.LuxoRadio.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import lu.voxhost.LuxoRadio.R;
import lu.voxhost.LuxoRadio.models.Channels;
import lu.voxhost.LuxoRadio.utils.AdsManager;
import lu.voxhost.LuxoRadio.utils.Methods;

import java.util.ArrayList;
import java.util.List;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.MyViewHolder> implements Filterable {
    private List<Channels> dataList;
    private List<Channels> dataListFiltered;
    Activity activity;

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView channel_name;
        LinearLayout channel_lyt;
        ImageView channel_icon;
        ProgressBar progressBar;

        MyViewHolder(View view) {
            super(view);
            channel_name = view.findViewById(R.id.channel_name);
            channel_lyt = view.findViewById(R.id.channel_lyt);
            channel_icon = view.findViewById(R.id.channel_icon);
            progressBar = view.findViewById(R.id.progressBar);
        }
    }

    public ChannelAdapter(Activity activity, List<Channels> ChannelsList) {
        this.activity = activity;
        this.dataList = ChannelsList;
        this.dataListFiltered = ChannelsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_channel, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Channels row = dataListFiltered.get(position);
        holder.channel_name.setText(row.getChannel_name());
        Methods.loadImageNoCrop(holder.channel_icon, row.getChannel_icon(), holder.progressBar);

        holder.channel_lyt.setOnClickListener(new View.OnClickListener() {
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
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    dataListFiltered = dataList;
                } else {
                    List<Channels> filteredList = new ArrayList<>();
                    for (Channels row : dataList) {
                        if (row.getChannel_name().toLowerCase().contains(charString.toLowerCase())) {
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
                dataListFiltered = (ArrayList<Channels>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return dataListFiltered.size();
    }
}

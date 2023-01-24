package com.andromob.amradio.fragments;

import static com.andromob.amradio.utils.Methods.getActionBar;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import com.andromob.amradio.R;
import com.andromob.amradio.adapter.ChannelAdapter;
import com.andromob.amradio.databinding.FragmentChannelBinding;
import com.andromob.amradio.models.Channels;
import com.andromob.amradio.utils.Methods;
import com.andromob.amradio.utils.Prefs;
import com.andromob.amradio.utils.Presenter;
import com.andromob.amradio.views.ChannelViews;

import java.util.List;

public class ChannelFragment extends Fragment implements ChannelViews {
    FragmentChannelBinding binding;
    Presenter presenter;
    ChannelAdapter adapter;
    SearchView searchView;

    public ChannelFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChannelBinding.inflate(inflater, container, false);
        presenter = new Presenter(this);

        binding.noInternetInc.btnRetry.setOnClickListener(view -> {
            if (Methods.isNetworkAvailable(getContext())) {
                initChannels();
            }
        });

        initChannels();
        return binding.getRoot();
    }

    private void initChannels() {
        if (getContext() != null) {
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                if (bundle.containsKey("type")) {
                    switch (bundle.getString("type")) {
                        case "Category":
                            presenter.getChannelsByCat(bundle.getInt("cat_id"));
                            getActionBar(getContext()).setTitle(bundle.getString("cat_name"));
                            break;
                        case "Fav":
                            getActionBar(getContext()).setTitle(getString(R.string.favorites));
                            String allfav = Prefs.getPreference(getContext(), Prefs.PREFERENCES, Prefs.FAV_ID, "");
                            if (allfav != null) {
                                if (allfav.equals(",")) {
                                    binding.channelLoading.loadingLayout.setVisibility(View.GONE);
                                    binding.emptyList.setVisibility(View.VISIBLE);
                                } else if (allfav.isEmpty()) {
                                    binding.channelLoading.loadingLayout.setVisibility(View.GONE);
                                    binding.emptyList.setVisibility(View.VISIBLE);
                                } else {
                                    presenter.getFavChannels(allfav);
                                }
                            } else {
                                binding.channelLoading.loadingLayout.setVisibility(View.GONE);
                                binding.emptyList.setVisibility(View.VISIBLE);
                            }
                            break;
                        case "CatNotification":
                            presenter.getChannelsByCat(bundle.getInt("cat_id"));
                            getActionBar(getContext()).setTitle(getString(R.string.app_name));
                            break;
                        case "ChannelNotification":
                            presenter.getFavChannels(String.valueOf(bundle.getInt("channel_id")));
                            getActionBar(getContext()).setTitle(getString(R.string.app_name));
                            break;
                        default:
                            presenter.getLatestChannels();
                            getActionBar(getContext()).setTitle(getString(R.string.latest_channels));
                            break;
                    }
                } else {
                    presenter.getLatestChannels();
                    getActionBar(getContext()).setTitle(getString(R.string.latest_channels));
                }
            } else {
                presenter.getLatestChannels();
                getActionBar(getContext()).setTitle(getString(R.string.latest_channels));
            }
        }
    }

    @Override
    public void showLoading() {
        binding.recyclerView.setVisibility(View.GONE);
        binding.channelLoading.loadingLayout.setVisibility(View.VISIBLE);
        binding.noInternetInc.noInternet.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        binding.recyclerView.setVisibility(View.VISIBLE);
        binding.channelLoading.loadingLayout.setVisibility(View.GONE);
        binding.noInternetInc.noInternet.setVisibility(View.GONE);
    }

    @Override
    public void onErrorLoading(String message) {
        if (getContext() != null) {
            if (!Methods.isNetworkAvailable(getContext())) {
                message = getString(R.string.check_internet);
            } else {
                message = getString(R.string.try_again);
            }
            binding.channelLoading.loadingLayout.setVisibility(View.GONE);
            binding.noInternetInc.txtErorMsg.setText(message);
            binding.noInternetInc.noInternet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setChannels(List<Channels> dataList) {
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext()
                , 3));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new ChannelAdapter(getActivity(), dataList);
        binding.recyclerView.setAdapter(adapter);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            if (bundle.containsKey("type")) {
                if (bundle.getString("type").equals("CatNotification")) {
                    String cat_title = dataList.get(0).getCategory_name();
                    if (cat_title.isEmpty()) {
                        cat_title = getString(R.string.app_name);
                    }
                    getActionBar(getActivity()).setTitle(cat_title);
                }
            }
        }

        if (dataList.isEmpty()) {
            binding.channelLoading.loadingLayout.setVisibility(View.GONE);
            binding.emptyList.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if (getContext() != null) {
            menu.clear();
            inflater.inflate(R.menu.menu_main, menu);
            SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
            searchView = (SearchView) menu.findItem(R.id.action_search)
                    .getActionView();
            if (searchManager != null && getActivity() != null) {
                searchView.setSearchableInfo(searchManager
                        .getSearchableInfo(getActivity().getComponentName()));
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        searchView.clearFocus();
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String query) {
                        if (adapter != null) {
                            adapter.getFilter().filter(query);
                        }
                        return true;
                    }
                });
            }
            super.onCreateOptionsMenu(menu, inflater);
        }
    }

}
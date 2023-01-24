package lu.voxhost.LuxoRadio.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import lu.voxhost.LuxoRadio.R;
import lu.voxhost.LuxoRadio.adapter.CategoryAdapter;
import lu.voxhost.LuxoRadio.adapter.ChannelAdapter;
import lu.voxhost.LuxoRadio.adapter.SliderAdapter;
import lu.voxhost.LuxoRadio.databinding.FragmentHomeBinding;
import lu.voxhost.LuxoRadio.models.Category;
import lu.voxhost.LuxoRadio.models.Channels;
import lu.voxhost.LuxoRadio.utils.AdsManager;
import lu.voxhost.LuxoRadio.utils.Config;
import lu.voxhost.LuxoRadio.utils.Methods;
import lu.voxhost.LuxoRadio.utils.Presenter;
import lu.voxhost.LuxoRadio.views.ChannelViews;
import lu.voxhost.LuxoRadio.views.HomeDashViews;

import java.util.List;

public class HomeFragment extends Fragment implements HomeDashViews, ChannelViews {
    FragmentHomeBinding binding;
    Presenter presenter;
    ChannelAdapter channelAdapter, channelAdapter_mostview;
    CategoryAdapter categoryAdapter;
    private final Handler sliderHandler = new Handler();
    SliderAdapter sliderAdapter;
    int currentPosition;

    public HomeFragment() {
        // Required empty public constructor
    }

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        Methods.getActionBar(getActivity()).setTitle(getString(R.string.app_name));
        presenter = new Presenter(this, this);
        initButtons();
        initViews();
        updateAds();

        return binding.getRoot();
    }

    private void initViews() {
        presenter.getslider();
        presenter.getCategory("home");
        presenter.getLatestChannels();
        presenter.getMostViewedChannels();
    }

    private void initButtons() {
        binding.noInternetInc.btnRetry.setOnClickListener(view -> {
            if (Methods.isNetworkAvailable(getContext())) {
                initViews();
            }
        });

        binding.viewAllCat.setOnClickListener(view -> {
            Methods.addSelectedFragment(new CategoryFragment(), getActivity());
        });

        binding.viewAllLatest.setOnClickListener(view -> {
            Methods.addSelectedFragment(new ChannelFragment(), getActivity());
        });
    }

    @Override
    public void showLoading() {
        binding.sliderLoading.sliderLyt.setVisibility(View.VISIBLE);
        binding.recentCatLoading.categoryLoading.setVisibility(View.VISIBLE);
        binding.latestChannelLoading.channelLoading.setVisibility(View.VISIBLE);
        binding.mostviewLoading.channelLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        binding.sliderLoading.sliderLyt.setVisibility(View.GONE);
        binding.noInternetInc.noInternet.setVisibility(View.GONE);
        binding.recentCatLoading.categoryLoading.setVisibility(View.GONE);
        binding.latestChannelLoading.channelLoading.setVisibility(View.GONE);
        binding.mostviewLoading.channelLoading.setVisibility(View.GONE);
    }

    @Override
    public void onErrorLoading(String message) {
        if (getContext() != null) {
            if (!Methods.isNetworkAvailable(getContext())) {
                message = getString(R.string.check_internet);
            } else {
                message = getString(R.string.try_again);
            }
            binding.noInternetInc.txtErorMsg.setText(message);
            binding.noInternetInc.noInternet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setChannels(List<Channels> dataList) {
        binding.recyclerViewLatest.setLayoutManager(new LinearLayoutManager(getContext()
                , LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewLatest.setHasFixedSize(true);
        binding.recyclerViewLatest.setItemAnimator(new DefaultItemAnimator());
        channelAdapter = new ChannelAdapter(getActivity(), dataList);
        binding.recyclerViewLatest.setAdapter(channelAdapter);
    }

    @Override
    public void setSlider(List<Channels> dataList) {
        sliderAdapter = new SliderAdapter(getActivity(), dataList, binding.channelSlider);
        binding.channelSlider.setAdapter(sliderAdapter);
        Methods.setupSlider(getContext(), binding.channelSlider, sliderHandler, sliderRunnable);
        currentPosition = dataList.size() - 1;
    }

    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            if (binding.channelSlider.getCurrentItem() == currentPosition) {
                binding.channelSlider.setCurrentItem(0);
            } else {
                binding.channelSlider.setCurrentItem(binding.channelSlider.getCurrentItem() + 1);
            }
        }
    };

    @Override
    public void setRecentCategory(List<Category> dataList) {
        binding.recyclerViewCat.setLayoutManager(new LinearLayoutManager(getContext()
                , LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewCat.setHasFixedSize(true);
        binding.recyclerViewCat.setItemAnimator(new DefaultItemAnimator());
        categoryAdapter = new CategoryAdapter(getContext(), dataList, 0);
        binding.recyclerViewCat.setAdapter(categoryAdapter);
    }

    @Override
    public void setMostViewed(List<Channels> dataList) {
        binding.recyclerViewMostview.setLayoutManager(new LinearLayoutManager(getContext()
                , LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewMostview.setHasFixedSize(true);
        binding.recyclerViewMostview.setItemAnimator(new DefaultItemAnimator());
        channelAdapter_mostview = new ChannelAdapter(getActivity(), dataList);
        binding.recyclerViewMostview.setAdapter(channelAdapter_mostview);
    }

    private void updateAds() {
        AdsManager.showNativeAd(getActivity(), binding.adContainerNative);
    }

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, Config.SLIDER_TIMER);
    }

    @Override
    public void onDestroy() {
        sliderHandler.removeCallbacks(sliderRunnable);
        AdsManager.destroyNativeAds();
        super.onDestroy();
    }
}

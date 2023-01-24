package lu.voxhost.LuxoRadio.fragments;

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
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import lu.voxhost.LuxoRadio.R;
import lu.voxhost.LuxoRadio.adapter.CategoryAdapter;
import lu.voxhost.LuxoRadio.databinding.FragmentCategoryBinding;
import lu.voxhost.LuxoRadio.models.Category;
import lu.voxhost.LuxoRadio.utils.Methods;
import lu.voxhost.LuxoRadio.utils.Presenter;
import lu.voxhost.LuxoRadio.views.CategoryViews;

public class CategoryFragment extends Fragment implements CategoryViews {
    FragmentCategoryBinding binding;
    CategoryAdapter adapter;
    Presenter presenter;
    SearchView searchView;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        Methods.getActionBar(getContext()).setTitle(getString(R.string.categories));
        presenter = new Presenter(this);
        presenter.getCategory("main");

        binding.noInternetInc.btnRetry.setOnClickListener(view -> {
            if (Methods.isNetworkAvailable(getContext())) {
                presenter.getCategory("main");
            }
        });
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void showLoading() {
        binding.recyclerView.setVisibility(View.GONE);
        binding.categoryLoading.loadingLayout.setVisibility(View.VISIBLE);
        binding.noInternetInc.noInternet.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        binding.recyclerView.setVisibility(View.VISIBLE);
        binding.categoryLoading.loadingLayout.setVisibility(View.GONE);
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
            binding.noInternetInc.txtErorMsg.setText(message);
            binding.noInternetInc.noInternet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setCategory(List<Category> dataList) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()
                , LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new CategoryAdapter(getActivity(), dataList,1);
        binding.recyclerView.setAdapter(adapter);
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
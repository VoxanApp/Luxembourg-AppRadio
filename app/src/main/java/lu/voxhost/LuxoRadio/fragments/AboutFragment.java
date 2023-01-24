package lu.voxhost.LuxoRadio.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import lu.voxhost.LuxoRadio.BuildConfig;
import lu.voxhost.LuxoRadio.R;
import lu.voxhost.LuxoRadio.utils.Methods;

public class AboutFragment extends Fragment {
    public View view;
    TextView textView_about_version;


    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_about, container, false);
        Methods.getActionBar(getActivity()).setTitle(getString(R.string.about_us));
        textView_about_version = view.findViewById(R.id.textView_about_version);
        textView_about_version.setText(BuildConfig.VERSION_NAME);
        return view;
    }

}

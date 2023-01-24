package com.andromob.amradio.fragments;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.andromob.amradio.R;
import com.andromob.amradio.utils.Methods;

import java.io.IOException;
import java.io.InputStream;

public class PrivacyFragment extends Fragment {
    private TextView textview_privacy_policy;
    private String str;


    public PrivacyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_privacy, container, false);

        textview_privacy_policy = view.findViewById(R.id.textview_privacy_policy);

        Methods.getActionBar(getActivity()).setTitle(getString(R.string.privacy_policy));
        try {
            InputStream is = getContext().getAssets().open("privarcypolicy.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            str = new String(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        textview_privacy_policy.setText(Html.fromHtml(str));

    return view;
    }


}

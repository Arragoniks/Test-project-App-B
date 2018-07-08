package com.example.komputer.app_b.Photo;

import android.app.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.komputer.app_b.R;


public class DefaultFragment extends Fragment {
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_default, null);
        // Задержка перед отключением
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().finish();
            }
        }, 10000);

        return v;
    }
}
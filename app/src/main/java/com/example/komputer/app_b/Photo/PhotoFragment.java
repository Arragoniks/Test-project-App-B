package com.example.komputer.app_b.Photo;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.komputer.app_b.Photo.PhotoTask;
import com.example.komputer.app_b.R;

public class PhotoFragment extends Fragment {
    ImageView photo_image;
    TextView errorImageText;
    public static PhotoFragment setDataFragment(String link){
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putString("LINK",link);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.activity_photo, null);
        errorImageText=(TextView)v.findViewById(R.id.textErrorImage);
        photo_image = (ImageView)v.findViewById(R.id.imageView);
        String link = getArguments().getString("LINK");
        new PhotoTask(photo_image,errorImageText,getActivity()).execute(link);
        return v;
    }
}

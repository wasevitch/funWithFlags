package com.example.nicolas.drapeaux.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.nicolas.drapeaux.R;

public class MainFragment extends Fragment {

    ImageView imageViewFlagRoller;
    Button playButton;

    public MainFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        playButton = (Button)view.findViewById(R.id.play);
        playButton.setClickable(false);
        imageViewFlagRoller = (ImageView)view.findViewById(R.id.imageViewFlagRoller);
        return view;
    }

    public ImageView getImageViewFlagRoller() {
        return imageViewFlagRoller;
    }

    public Button getPlayButton() {
        return playButton;
    }
}

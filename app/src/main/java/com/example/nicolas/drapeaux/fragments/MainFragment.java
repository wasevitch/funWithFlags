package com.example.nicolas.drapeaux.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.nicolas.drapeaux.controller.CountryController;
import com.example.nicolas.drapeaux.controller.FragmentController;
import com.example.nicolas.drapeaux.controller.QuizzController;
import com.example.nicolas.drapeaux.R;

public class MainFragment extends Fragment {

    private FragmentController fragmentController;
    private CountryController countryController;
    private QuizzController quizzController;

    private ImageView imageViewFlagRoller;
    private Button playButton;

    private FlagRollerHandler flagRollerHandler;
    private FlagRollerThread flagRollerThread;

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

    @Override
    public void onStart() {

        super.onStart();

        flagRollerHandler = new FlagRollerHandler(this);
        flagRollerThread = new FlagRollerThread(flagRollerHandler);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagRollerHandler.setMainFragment(null);
                fragmentController.showNextQuestion();
            }
        });

        flagRollerHandler.post(flagRollerThread);
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        fragmentController = (FragmentController)args.getSerializable("fragmentcontroller");
        countryController = (CountryController)args.getSerializable("countrycontroller");
        quizzController = (QuizzController)args.get("quizzcontroller");
    }

    public void updateImageRoller(ImageView imageViewFlagRoller) {
        if(imageViewFlagRoller != null) {
            int id = (int) (Math.random() * countryController.getSize());
            imageViewFlagRoller.setImageBitmap(countryController.getCountryFlag(id).getBitmap());
        }
    }

    public ImageView getImageViewFlagRoller() {
        return imageViewFlagRoller;
    }

    public Button getPlayButton() {
        return playButton;
    }
}

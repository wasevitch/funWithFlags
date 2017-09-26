package com.example.nicolas.drapeaux.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.nicolas.drapeaux.CountryController;
import com.example.nicolas.drapeaux.FlagRollerHandler;
import com.example.nicolas.drapeaux.FlagRollerThread;
import com.example.nicolas.drapeaux.FragmentController;
import com.example.nicolas.drapeaux.R;
import com.example.nicolas.drapeaux.db.model.Country;
import com.example.nicolas.drapeaux.db.model.Question;

public class MainFragment extends Fragment {

    private FragmentController fragmentController;
    private CountryController countryController;

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
                Question question = new Question();

                Country country1 = countryController.getCountry(50);
                Country country2 = countryController.getCountry(156);
                Country country3 = countryController.getCountry(220);
                Country country4 = countryController.getCountry(78);

                question.setFirstAnswer(country1);
                question.setSecondAnwser(country2);
                question.setThirdAnswer(country3);
                question.setFourthAnswer(country4);

                question.setCountry(country2);

                question.setType(0);

                flagRollerHandler.setMainFragment(null);

                fragmentController.showQuizzFragment(question);
            }
        });

        flagRollerHandler.post(flagRollerThread);
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        fragmentController = (FragmentController)args.getSerializable("fragmentcontroller");
        countryController = (CountryController)args.getSerializable("countrycontroller");
    }

    public void updateImageRoller(ImageView imageViewFlagRoller) {
        if(imageViewFlagRoller != null) {
            int id = (int) (Math.random() * countryController.getSize());
            imageViewFlagRoller.setImageBitmap(countryController.getCountryFlag(id));
        }
    }

    public ImageView getImageViewFlagRoller() {
        return imageViewFlagRoller;
    }

    public Button getPlayButton() {
        return playButton;
    }
}

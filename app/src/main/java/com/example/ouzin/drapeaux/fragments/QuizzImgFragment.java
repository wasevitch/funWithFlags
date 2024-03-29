package com.example.ouzin.drapeaux.fragments;

import android.graphics.BitmapFactory;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ouzin.drapeaux.controller.FragmentController;
import com.example.ouzin.drapeaux.R;
import com.example.ouzin.drapeaux.db.model.Country;
import com.example.ouzin.drapeaux.db.model.Question;

import java.util.HashMap;
import java.util.Map;

public class QuizzImgFragment extends Fragment implements ProgressBarHandler {

    private FragmentController fragmentController;

    private Button titleButton;

    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;

    private ProgressBar progressBar;

    private Question question = null;

    private ProgressThread progressThread;
    private ProgressHandler progressHandler;

    Map<ImageView, String> imgButtons;

    public View onCreateView(LayoutInflater inflater, ViewGroup vg,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quizz_img, vg, false);

        titleButton = (Button)view.findViewById(R.id.buttonCountryToFind);

        imageView1 = (ImageView)view.findViewById(R.id.imageViewImg1);
        imageView2 = (ImageView)view.findViewById(R.id.imageViewImg2);
        imageView3 = (ImageView)view.findViewById(R.id.imageViewImg3);
        imageView4 = (ImageView)view.findViewById(R.id.imageViewImg4);

        progressBar = (ProgressBar)view.findViewById(R.id.progressBar2);

        imgButtons = new HashMap<>();

        if(question != null) {
            titleButton.setText(question.getCountry().getCountry());

            imageView1.setImageBitmap(BitmapFactory.decodeByteArray(question.getFirstAnswer().getImage(), 0, question.getFirstAnswer().getImage().length));
            imageView2.setImageBitmap(BitmapFactory.decodeByteArray(question.getSecondAnswer().getImage(), 0, question.getSecondAnswer().getImage().length));
            imageView3.setImageBitmap(BitmapFactory.decodeByteArray(question.getThirdAnswer().getImage(), 0, question.getThirdAnswer().getImage().length));
            imageView4.setImageBitmap(BitmapFactory.decodeByteArray(question.getFourthAnswer().getImage(), 0, question.getFourthAnswer().getImage().length));

            imgButtons.put(imageView1, question.getFirstAnswer().getCountry());
            imgButtons.put(imageView2, question.getSecondAnswer().getCountry());
            imgButtons.put(imageView3, question.getThirdAnswer().getCountry());
            imgButtons.put(imageView4, question.getFourthAnswer().getCountry());
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        progressThread = new ProgressThread();
        progressHandler = new ProgressHandler(this, progressThread);
        progressThread.setHandler(progressHandler);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView button = (ImageView) v;

                String countryName = imgButtons.get(button);

                Country country = question.getCountryByName(countryName);

                question.setPlayerAnwser(country);
                if (question.getPlayerAnwser() ==question.getCountry()){
                    Toast t =Toast.makeText(getContext().getApplicationContext(),
                            "Bonne réponse",
                            Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.TOP, 0,0);
                    t.show();
                }else {
                    Toast t = Toast.makeText(getContext().getApplicationContext(),
                            "Mauvaise réponse",
                            Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.TOP, 0,0);
                    t.show();
                }
                progressHandler.setProgressBarHandler(null);

                fragmentController.showNextQuestion();
            }
        };

        imageView1.setOnClickListener(listener);
        imageView2.setOnClickListener(listener);
        imageView3.setOnClickListener(listener);
        imageView4.setOnClickListener(listener);

        progressHandler.setMaxTime(5000);
        progressHandler.start();
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        question = (Question)args.getSerializable("question");
        fragmentController = (FragmentController)args.getSerializable("fragmentcontroller");
    }

    @Override
    public void updateProgressBar(int value) {
        progressBar.setProgress(value);
        if(value > 99) {
            progressHandler.setProgressBarHandler(null);
            fragmentController.showNextQuestion();
        }
    }
}

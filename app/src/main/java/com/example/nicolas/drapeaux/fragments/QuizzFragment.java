package com.example.nicolas.drapeaux.fragments;

import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.nicolas.drapeaux.FragmentController;
import com.example.nicolas.drapeaux.R;
import com.example.nicolas.drapeaux.db.model.Question;

public class QuizzFragment extends Fragment implements ProgressBarHandler {

    private FragmentController fragmentController;

    private ImageView imageViewFragmentButtons;

    private Button buttonPays1;
    private Button buttonPays2;
    private Button buttonPays3;
    private Button buttonPays4;

    private ProgressBar progressBar;

    private Question question = null;

    private ProgressThread progressThread;
    private ProgressHandler progressHandler;

    public View onCreateView(LayoutInflater inflater, ViewGroup vg,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_quizz, vg, false);

        imageViewFragmentButtons = (ImageView)view.findViewById(R.id.imageViewFragmentButtons);

        buttonPays1 = (Button)view.findViewById(R.id.buttonPays1);
        buttonPays2 = (Button)view.findViewById(R.id.buttonPays2);
        buttonPays3 = (Button)view.findViewById(R.id.buttonPays3);
        buttonPays4 = (Button)view.findViewById(R.id.buttonPays4);

        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);

        if(question != null) {
            imageViewFragmentButtons.setImageBitmap(BitmapFactory.decodeByteArray(question.getCountry().getImage(), 0, question.getCountry().getImage().length));

            buttonPays1.setText(question.getFirstAnswer().getCountry());
            buttonPays2.setText(question.getSecondAnswer().getCountry());
            buttonPays3.setText(question.getThirdAnswer().getCountry());
            buttonPays4.setText(question.getFourthAnswer().getCountry());
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
                Button button = (Button)v;

                String countryName = button.getText().toString();

                progressHandler.setProgressBarHandler(null);

                fragmentController.showNextQuestion();
            }
        };

        buttonPays1.setOnClickListener(listener);
        buttonPays2.setOnClickListener(listener);
        buttonPays3.setOnClickListener(listener);
        buttonPays4.setOnClickListener(listener);

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

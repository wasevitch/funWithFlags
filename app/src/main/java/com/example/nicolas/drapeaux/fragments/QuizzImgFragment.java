package com.example.nicolas.drapeaux.fragments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.nicolas.drapeaux.FragmentController;
import com.example.nicolas.drapeaux.R;
import com.example.nicolas.drapeaux.db.model.Question;

import java.util.HashMap;
import java.util.Map;

public class QuizzImgFragment extends Fragment {

    private FragmentController fragmentController;

    private Button titleButton;

    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;

    private Question question = null;

    Map<ImageView, String> imgButtons;

    public View onCreateView(LayoutInflater inflater, ViewGroup vg,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quizz_img, vg, false);

        titleButton = (Button)view.findViewById(R.id.buttonCountryToFind);

        imageView1 = (ImageView)view.findViewById(R.id.imageViewImg1);
        imageView2 = (ImageView)view.findViewById(R.id.imageViewImg2);
        imageView3 = (ImageView)view.findViewById(R.id.imageViewImg3);
        imageView4 = (ImageView)view.findViewById(R.id.imageViewImg4);

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
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView button = (ImageView) v;

                String countryName = imgButtons.get(button);

                fragmentController.showNextQuestion();
            }
        };

        imageView1.setOnClickListener(listener);
        imageView2.setOnClickListener(listener);
        imageView3.setOnClickListener(listener);
        imageView4.setOnClickListener(listener);
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        question = (Question)args.getSerializable("question");
        fragmentController = (FragmentController)args.getSerializable("fragmentcontroller");
    }
}

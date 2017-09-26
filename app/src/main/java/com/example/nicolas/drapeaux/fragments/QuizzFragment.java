package com.example.nicolas.drapeaux.fragments;

import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.nicolas.drapeaux.R;
import com.example.nicolas.drapeaux.db.model.Question;

public class QuizzFragment extends Fragment {

    private ImageView imageViewFragmentButtons;

    private Button buttonPays1;
    private Button buttonPays2;
    private Button buttonPays3;
    private Button buttonPays4;

    private Question question = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup vg,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_quizz, vg, false);

        imageViewFragmentButtons = (ImageView)view.findViewById(R.id.imageViewFragmentButtons);

        buttonPays1 = (Button)view.findViewById(R.id.buttonPays1);
        buttonPays2 = (Button)view.findViewById(R.id.buttonPays2);
        buttonPays3 = (Button)view.findViewById(R.id.buttonPays3);
        buttonPays4 = (Button)view.findViewById(R.id.buttonPays4);

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
    public void setArguments(Bundle args) {
        super.setArguments(args);
        question = (Question)args.getSerializable("question");
    }

    public ImageView getImageViewFragmentButtons() {
        return imageViewFragmentButtons;
    }

    public Button getButtonPays1() {
        return buttonPays1;
    }

    public Button getButtonPays2() {
        return buttonPays2;
    }

    public Button getButtonPays3() {
        return buttonPays3;
    }

    public Button getButtonPays4() {
        return buttonPays4;
    }
}

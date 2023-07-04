package com.example.ouzin.drapeaux.fragments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ouzin.drapeaux.R;
import com.example.ouzin.drapeaux.db.model.Question;

import java.util.List;

public class QuestionAdapter extends ArrayAdapter<Question> {

    public QuestionAdapter(@NonNull Context context, @NonNull List<Question> questions) {
        super(context, 0, questions);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Question question = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_question, parent, false);
        }

        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);
        TextView answerTextView = (TextView)convertView.findViewById(R.id.textView);
        TextView correctTextView = (TextView)convertView.findViewById(R.id.textView1);
        TextView capitalTextView = (TextView)convertView.findViewById(R.id.textView2);

        answerTextView.setTextColor(Color.BLACK);
        correctTextView.setTextColor(Color.BLACK);
        capitalTextView.setTextColor(Color.BLACK);

        imageView.setImageBitmap(BitmapFactory.decodeByteArray(question.getCountry().getImage(), 0, question.getCountry().getImage().length));

        answerTextView.setText("Your answer: " + ((question.getPlayerAnwser() == null) ? " none" : question.getPlayerAnwser().getCountry()));
        correctTextView.setText("Correct answer: " + question.getCountry().getCountry());
        capitalTextView.setText("Capital : " + question.getCountry().getCapital());

        if(question.getCountry() == question.getPlayerAnwser())
            convertView.setBackgroundColor(Color.rgb(0x00, 0x88, 0x00));
        else
            convertView.setBackgroundColor(Color.rgb(0xFF, 0xAA, 0x00));

        return convertView;
    }
}

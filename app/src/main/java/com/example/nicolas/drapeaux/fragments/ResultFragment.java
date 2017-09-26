package com.example.nicolas.drapeaux.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nicolas.drapeaux.FragmentController;
import com.example.nicolas.drapeaux.QuizzController;
import com.example.nicolas.drapeaux.R;
import com.example.nicolas.drapeaux.db.model.Question;

import java.util.List;


public class ResultFragment extends Fragment {

    private FragmentController fragmentController;
    private QuizzController quizzController;

    private Button newQuizzButton;
    private Button backToHomeButton;

    private TextView resultsTextView;

    private ListView resultsListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);

        newQuizzButton = (Button)view.findViewById(R.id.newQuizzButton);
        backToHomeButton = (Button)view.findViewById(R.id.backToHomeButton);

        resultsTextView = (TextView)view.findViewById(R.id.resultTextView);
        resultsListView = (ListView)view.findViewById(R.id.listQuestions);

        return view;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);

        quizzController = (QuizzController)args.getSerializable("quizzcontroller");
        fragmentController = (FragmentController)args.getSerializable("fragmentcontroller");
    }

    @Override
    public void onStart() {
        super.onStart();

        List<Question> questions = quizzController.getQuizz().getQuestions();

        QuestionAdapter questionAdapter = new QuestionAdapter(this.getActivity().getApplicationContext(), questions);

        resultsListView.setAdapter(questionAdapter);

        String resultStr = "";
        resultStr += "Your quizz result: ";
        resultStr += countCorrectAnswers(questions);
        resultStr += "/";
        resultStr += questions.size();

        resultsTextView.setText(resultStr);

        newQuizzButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizzController.newQuizz();
                quizzController.saveQuizz();

                fragmentController.showNextQuestion();
            }
        });

        backToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentController.showMainMenu();
            }
        });
    }

    public int countCorrectAnswers(List<Question> questions) {
        int count = 0;

        for(Question question : questions) {
            if(question.getCountry() == question.getPlayerAnwser())
                count++;
        }

        return count;
    }
}

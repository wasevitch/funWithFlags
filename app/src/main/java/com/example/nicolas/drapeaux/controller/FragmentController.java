package com.example.nicolas.drapeaux.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.example.nicolas.drapeaux.HomeActivity;
import com.example.nicolas.drapeaux.R;
import com.example.nicolas.drapeaux.db.model.Question;
import com.example.nicolas.drapeaux.fragments.MainFragment;
import com.example.nicolas.drapeaux.fragments.QuizzFragment;
import com.example.nicolas.drapeaux.fragments.QuizzImgFragment;
import com.example.nicolas.drapeaux.fragments.ResultFragment;

import java.io.Serializable;
import java.util.Iterator;

public class FragmentController implements Serializable {
    private FragmentManager fragmentManager;

    private HomeActivity homeActivity;

    private CountryController countryController;
    private QuizzController quizzController;

    public FragmentController(HomeActivity homeActivity, CountryController countryController, QuizzController quizzController) {
        this.homeActivity = homeActivity;
        this.countryController = countryController;
        this.quizzController = quizzController;

        fragmentManager = homeActivity.getSupportFragmentManager();
    }

    public void showMainMenu() {
        quizzController.newQuizz();
        quizzController.saveQuizz();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle args = new Bundle();

        args.putSerializable("fragmentcontroller", this);
        args.putSerializable("countrycontroller", countryController);
        args.putSerializable("quizzcontroller", quizzController);

        MainFragment mainFragment = new MainFragment();

        mainFragment.setArguments(args);

        fragmentTransaction.replace(R.id.fragmentcontainer, mainFragment);
        fragmentTransaction.commit();
    }

    public void showNextQuestion() {
        Iterator<Question> iterator = quizzController.getIterator();

        if(iterator.hasNext())
            showQuizzFragment(iterator.next());
        else
            showResults();
    }

    public void showQuizzFragment(Question question) {
        int type = question.getType();

        Fragment currentFragment;

        if(type == 0) {
            currentFragment = new QuizzFragment();
        } else if(type == 1) {
            currentFragment = new QuizzImgFragment();
        } else {
            Log.i("Quizz", "Type inconnu");
            return;
        }

        Bundle args = new Bundle();

        args.putSerializable("question", question);
        args.putSerializable("fragmentcontroller", this);

        currentFragment.setArguments(args);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragmentcontainer, currentFragment);
        fragmentTransaction.commit();
    }

    public void showResults() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle args = new Bundle();

        args.putSerializable("fragmentcontroller", this);
        args.putSerializable("quizzcontroller", quizzController);

        ResultFragment resultFragment = new ResultFragment();

        resultFragment.setArguments(args);

        fragmentTransaction.replace(R.id.fragmentcontainer, resultFragment);
        fragmentTransaction.commit();
    }
}

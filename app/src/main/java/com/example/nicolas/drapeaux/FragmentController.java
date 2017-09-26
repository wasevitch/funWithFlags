package com.example.nicolas.drapeaux;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.nicolas.drapeaux.db.model.Question;
import com.example.nicolas.drapeaux.fragments.MainFragment;
import com.example.nicolas.drapeaux.fragments.QuizzFragment;
import com.example.nicolas.drapeaux.fragments.QuizzImgFragment;

import java.io.Serializable;

public class FragmentController implements Serializable {
    private FragmentManager fragmentManager;

    private MainFragment mainFragment;
    private QuizzFragment quizzFragment;
    private QuizzImgFragment quizzImgFragment;

    private HomeActivity homeActivity;

    private CountryController countryController;
    private QuizzController quizzController;

    public FragmentController(HomeActivity homeActivity, CountryController countryController, QuizzController quizzController) {
        this.homeActivity = homeActivity;
        this.countryController = countryController;
        this.quizzController = quizzController;

        fragmentManager = homeActivity.getSupportFragmentManager();

        mainFragment = new MainFragment();
        quizzFragment = new QuizzFragment();
        quizzImgFragment = new QuizzImgFragment();
    }

    public void showMainMenu() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle args = new Bundle();

        args.putSerializable("fragmentcontroller", this);
        args.putSerializable("countrycontroller", countryController);

        mainFragment.setArguments(args);

        fragmentTransaction.add(R.id.fragmentcontainer, mainFragment);
        fragmentTransaction.commit();
    }

    public void showQuizzFragment(Question question) {
        int type = question.getType();

        Fragment currentFragment;

        if(type == 0) {
            currentFragment = quizzFragment;
        } else if(type == 1) {
            currentFragment = quizzImgFragment;
        } else {
            Log.i("Quizz", "Type inconnu");
            return;
        }

        Bundle args = new Bundle();

        args.putSerializable("question", question);

        currentFragment.setArguments(args);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragmentcontainer, currentFragment);
        fragmentTransaction.commit();
    }

    public MainFragment getMainFragment() {
        return mainFragment;
    }

    public QuizzFragment getQuizzFragment() {
        return quizzFragment;
    }

    public QuizzImgFragment getQuizzImgFragment() {
        return quizzImgFragment;
    }
}

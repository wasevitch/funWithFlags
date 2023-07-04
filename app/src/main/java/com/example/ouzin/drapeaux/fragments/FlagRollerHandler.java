package com.example.ouzin.drapeaux.fragments;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.example.ouzin.drapeaux.R;
import com.example.ouzin.drapeaux.fragments.MainFragment;

public class FlagRollerHandler extends Handler {

    private MainFragment mainFragment;

    public FlagRollerHandler(MainFragment mainFragment) {
        this.mainFragment = mainFragment;
    }

    public void setMainFragment(MainFragment mainFragment) {
        this.mainFragment = mainFragment;
    }

    @Override
    public void handleMessage(Message msg) {
        if(mainFragment != null) {
            mainFragment.updateImageRoller((ImageView) mainFragment.getView().findViewById(R.id.imageViewFlagRoller));
        }
    }

}

package com.example.nicolas.drapeaux;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

public class FlagRollerHandler extends Handler {

    private HomeActivity homeActivity;

    public FlagRollerHandler(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    @Override
    public void handleMessage(Message msg) {
        homeActivity.updateImageRoller((ImageView) homeActivity.findViewById(R.id.imageViewFlagRoller));
    }

}

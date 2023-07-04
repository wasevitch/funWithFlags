package com.example.ouzin.drapeaux.fragments;

import android.os.Message;

public class FlagRollerThread extends Thread {

    FlagRollerHandler flagRollerHandler;

    private int frequency = 100;

    public FlagRollerThread(FlagRollerHandler flagRollerHandler) {
        this.flagRollerHandler = flagRollerHandler;
    }

    @Override
    public void run() {
        flagRollerHandler.sendMessage(new Message());

        if(frequency > 0 ) {
            int time = 1000 / frequency;
            frequency -= 1;
            flagRollerHandler.postDelayed(this, time);
        }
    }

}

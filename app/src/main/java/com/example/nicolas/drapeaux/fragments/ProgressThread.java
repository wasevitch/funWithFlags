package com.example.nicolas.drapeaux.fragments;

import android.os.Message;

public class ProgressThread extends Thread {

    private ProgressHandler handler;

    public void setHandler(ProgressHandler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        handler.sendMessage(new Message());
    }
}

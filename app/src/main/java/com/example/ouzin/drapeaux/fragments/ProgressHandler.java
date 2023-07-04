package com.example.ouzin.drapeaux.fragments;

import android.os.Handler;
import android.os.Message;

public class ProgressHandler extends Handler {

    private ProgressThread progressThread;
    private ProgressBarHandler progressBarHandler;

    private long maxTime;

    private long startTime;

    public ProgressHandler(ProgressBarHandler progressBarHandler, ProgressThread progressThread) {
        this.progressBarHandler = progressBarHandler;
        this.progressThread = progressThread;
    }

    public long getCurrentTime() {
        return getSystemTime() - startTime;
    }

    public long getSystemTime() {
        return System.currentTimeMillis();
    }

    public void start() {
        startTime = getCurrentTime();
        postDelayed(progressThread, 16);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        double coef = (double)getCurrentTime() / (double)getMaxTime();
        double percent = coef*100;

        if(progressBarHandler != null) progressBarHandler.updateProgressBar((int)percent);

        if(coef < 1.0) {
            postDelayed(progressThread, 16);
        }
    }

    public long getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(long maxTime) {
        this.maxTime = maxTime;
    }

    public void setProgressBarHandler(ProgressBarHandler progressBarHandler) {
        this.progressBarHandler = progressBarHandler;
    }
}

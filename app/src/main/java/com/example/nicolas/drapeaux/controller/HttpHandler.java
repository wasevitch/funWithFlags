package com.example.nicolas.drapeaux.controller;

import android.os.Handler;
import android.os.Message;

import com.example.nicolas.drapeaux.HomeActivity;
import com.example.nicolas.drapeaux.controller.DatabaseController;

/**
 * Created by Moi on 20/09/2017.
 */
public class HttpHandler extends Handler {

    private HomeActivity homeActivity;
    private DatabaseController databaseController;

    public HttpHandler(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        homeActivity.game();
    }

    public DatabaseController getDatabaseController() {
        return databaseController;
    }

    public void setDatabaseController(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }
}

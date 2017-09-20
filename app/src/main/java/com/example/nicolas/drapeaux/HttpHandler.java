package com.example.nicolas.drapeaux;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Moi on 20/09/2017.
 */
class HttpHandler extends Handler {

    private HomeActivity homeActivity;
    private DatabaseController databaseController;

    public HttpHandler(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
        databaseController = homeActivity.getDatabaseController();
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

    }

    public DatabaseController getDatabaseController() {
        return databaseController;
    }
}

package com.example.nicolas.drapeaux;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

class HttpHandler extends Handler {

    private HomeActivity homeActivity;

    public HttpHandler(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

    }
}

class HttpThread extends Thread {
    private HttpHandler httpHandler;

    public HttpThread(HttpHandler handler) {
        httpHandler = handler;
    }

    @Override
    public void run() {
        try {
            Log.i("flagDatabase", "Connecting to flag webservice");
            URL url = new URL("http://www.geognos.com/api/en/countries/info/all.json");
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.connect();
            Log.i("flagDatabase", "Response code : " + urlConnection.getResponseCode());
            BufferedReader receivedWebContent = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            Gson gson = new Gson();

            //List countries = Array.asList(gson.fromJson(receivedWebContent, ))
        } catch(IOException e) {
            Log.i("flagDatabase", "Exception :" + e.getLocalizedMessage());
        } finally {
            Log.i("flagDatabase", "Finished!");
        }
    }
}

public class HomeActivity extends AppCompatActivity {

    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //prefs = getSharedPreferences("com.example.nicolas.drapeaux", MODE_PRIVATE);
        checkPermissions();
        initDatabase();
        checkFirstRun();
    }

    private boolean checkPermissions() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            Log.i("flagActivity", "Internet ON");
            return true;
        } else {
            Log.i("flagActivity", "Internet OFF");
            return false;
        }
    }

    private void initDatabase() {
        HttpHandler httpHandler = new HttpHandler(this);
        HttpThread httpThread = new HttpThread(httpHandler);

        //httpHandler.postAtTime(httpThread, 0);

        httpThread.start();
    }

    // merci google
    private void checkFirstRun() {

        final String PREFS_NAME = "com.example.nicolas.drapeaux";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;

        // Get current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {
            return;
        } else if (savedVersionCode == DOESNT_EXIST) {

        } else if (currentVersionCode > savedVersionCode) {

        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
    }
}

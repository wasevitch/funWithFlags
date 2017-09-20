package com.example.nicolas.drapeaux;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.nicolas.drapeaux.db.model.Country;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.j256.ormlite.dao.Dao;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private SharedPreferences prefs = null;

    private DatabaseController databaseController;
    private FlagController flagController;
    private QuizzController quizzController;

    private HttpHandler httpHandler;
    private HttpThread httpThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(checkPermissions()) {
            initDatabase();
            checkFirstRun();
            initFlags();
            imageTest();
        } else {
            Log.i("Error", "No permission, cannot download country flags");
        }
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

    private void imageTest() {
        ImageView imageViewTest = (ImageView)findViewById(R.id.imageViewTest);
        imageViewTest.setImageBitmap(flagController.getFlag("Belgium"));
    }

    private void initDatabase() {
        databaseController = new DatabaseController(this);
    }

    private void initFlags() {
        flagController = new FlagController(databaseController);
        flagController.initFlags();
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

        httpHandler = new HttpHandler(this);
        httpThread = new HttpThread(httpHandler);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {
            return;
        } else if (savedVersionCode == DOESNT_EXIST) {
            httpThread.start();
        } else if (currentVersionCode > savedVersionCode) {
            httpThread.start();
        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
    }

    public DatabaseController getDatabaseController() {
        return databaseController;
    }
}

package com.example.ouzin.drapeaux;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.ouzin.drapeaux.controller.CountryController;
import com.example.ouzin.drapeaux.controller.DatabaseController;
import com.example.ouzin.drapeaux.controller.FragmentController;
import com.example.ouzin.drapeaux.controller.HttpHandler;
import com.example.ouzin.drapeaux.controller.HttpThread;
import com.example.ouzin.drapeaux.controller.QuizzController;

import java.sql.SQLException;

public class HomeActivity extends AppCompatActivity {

    private DatabaseController databaseController;
    private CountryController countryController;
    private QuizzController quizzController;
    private FragmentController fragmentController;

    private HttpHandler httpHandler;
    private HttpThread httpThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onStart() {
        super.onStart();

        httpHandler = new HttpHandler(this);
        httpThread = new HttpThread(httpHandler);

        if(checkPermissions()) {
            initDatabase();
            //if(!checkdb() || !checkFirstRun()) {
            if(!checkdb()) {
                httpThread.start();
            } else {
                game();
            }
        } else {
            Log.i("Error", "No permission, cannot download country flags");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        quizzController = null;
        databaseController = null;
        fragmentController = null;
        countryController = null;
    }

    public void game() {
        initFlags();

        quizzController = new QuizzController(databaseController, countryController);

        fragmentController = new FragmentController(this, countryController, quizzController);

        fragmentController.showMainMenu();

        Log.i("oui", "non");
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
        databaseController = new DatabaseController(this);
        httpHandler.setDatabaseController(databaseController);
    }

    private void initFlags() {
        countryController = new CountryController(databaseController);
        countryController.initFlags();
    }

    private boolean checkdb() {
        try {
            if(databaseController.getDaoCountry().queryForAll().size() > 0)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean checkFirstRun() {

        final String PREFS_NAME = "com.example.ouzin.drapeaux";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;

        int currentVersionCode = BuildConfig.VERSION_CODE;

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        if (currentVersionCode == savedVersionCode) {
            return false;
        } else if (savedVersionCode == DOESNT_EXIST) {
            httpThread.start();
        } else if (currentVersionCode > savedVersionCode) {
            httpThread.start();
        }

        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();

        return true;
    }

}

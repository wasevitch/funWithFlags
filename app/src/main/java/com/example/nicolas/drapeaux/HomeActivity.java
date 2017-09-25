package com.example.nicolas.drapeaux;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.nicolas.drapeaux.db.model.Question;
import com.example.nicolas.drapeaux.db.model.Quizz;

import java.sql.SQLException;

public class HomeActivity extends AppCompatActivity {

    private SharedPreferences prefs = null;

    private DatabaseController databaseController;
    private CountryController countryController;
    private QuizzController quizzController;

    private HttpHandler httpHandler;
    private HttpThread httpThread;

    private FlagRollerHandler flagRollerHandler;
    private FlagRollerThread flagRollerThread;

    private ImageView imageViewFlagRoller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imageViewFlagRoller = (ImageView)findViewById(R.id.imageViewFlagRoller);

        httpHandler = new HttpHandler(this);
        httpThread = new HttpThread(httpHandler);

        if(checkPermissions()) {
            initDatabase();
            if(!checkdb() && !checkFirstRun()) {
                httpThread.start();
            } else {
                game();
            }
        } else {
            Log.i("Error", "No permission, cannot download country flags");
        }
    }

    public void game() {
        initFlags();

        quizzController = new QuizzController(databaseController, countryController);

        updateImageRoller();

        flagRollerHandler = new FlagRollerHandler(this);
        flagRollerThread = new FlagRollerThread(flagRollerHandler);

        imageViewFlagRoller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagRollerHandler.post(flagRollerThread);
            }
        });

        flagRollerHandler.post(flagRollerThread);

        quizzController.newQuizz();
        quizzController.saveQuizz();

        Question question = quizzController.newQuestion();
        Quizz quizz = quizzController.getQuizz();

        Log.i("oui", "non");
    }

    public void updateImageRoller() {
        int id = (int)(Math.random()* countryController.getSize());
        imageViewFlagRoller.setImageBitmap(countryController.getCountryFlag(id));
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

        final String PREFS_NAME = "com.example.nicolas.drapeaux";
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

    public DatabaseController getDatabaseController() {
        return databaseController;
    }
}

package com.example.nicolas.drapeaux;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.nicolas.drapeaux.fragments.MainFragment;
import com.example.nicolas.drapeaux.fragments.QuizzFragment;

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

/* ***Moi *** */

    /*    FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();*/

        Button play = (Button) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                QuizzFragment Qf1 = new QuizzFragment();
                fragmentTransaction.replace(android.R.id.content, Qf1);
                fragmentTransaction.commit();
            }
        });

        // get the display mode et switcher les frags en retournant le tel
     /*   int displaymode = getResources().getConfiguration().orientation;
        if (displaymode == 1) { // it portrait mode
            QuizzFragment Qf1 = new QuizzFragment();
            fragmentTransaction.replace(android.R.id.content, Qf1);
        } else {// its landscape
            MainFragment Mf2 = new MainFragment();
            fragmentTransaction.replace(android.R.id.content, Mf2);
        }*/



 /* *** Moi *** */


        imageViewFlagRoller = (ImageView)findViewById(R.id.imageViewFlagRoller);

        if(checkPermissions()) {
            initDatabase();
            if(checkFirstRun() == false) {
                game();
            }
        } else {
            Log.i("Error", "No permission, cannot download country flags");
        }
    }

    public void game() {
        initFlags();
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
    }

    private void initFlags() {
        countryController = new CountryController(databaseController);
        countryController.initFlags();
    }

    // merci google
    private boolean checkFirstRun() {

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
            return false;
        } else if (savedVersionCode == DOESNT_EXIST) {
            httpThread.start();
        } else if (currentVersionCode > savedVersionCode) {
            httpThread.start();
        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();

        return true;
    }

    public DatabaseController getDatabaseController() {
        return databaseController;
    }
}

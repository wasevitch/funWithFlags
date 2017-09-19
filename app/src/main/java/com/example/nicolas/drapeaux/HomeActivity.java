package com.example.nicolas.drapeaux;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.nicolas.drapeaux.db.model.Country;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

class HttpThread extends Thread {
    private HttpHandler httpHandler;

    public HttpThread(HttpHandler handler) {
        httpHandler = handler;
    }

    private byte[] getCountryImage(String countryCode) {
        HttpURLConnection urlConnection = null;
        try {
            URL uri = new URL("http://www.geognos.com/api/en/countries/flag/"+countryCode+".png");
            urlConnection = (HttpURLConnection) uri.openConnection();
            int statusCode = urlConnection.getResponseCode();
            if (statusCode != 200) {
                return null;
            }
            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                return baos.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("flagDatabase", "Error downloading image from " + countryCode);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
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

            JsonObject jsonObject = gson.fromJson(receivedWebContent, JsonObject.class);

            JsonObject array = jsonObject.getAsJsonObject("Results");

            List<Country> countries = new ArrayList<>();

            for(String key : array.keySet()) {
                Log.i("Country : ", key);

                JsonObject jsonCountry = array.getAsJsonObject(key);
                String jsonCountryName = jsonCountry.get("Name").getAsString();

                Country country = new Country();

                country.setCountry(jsonCountryName);
                country.setImage(getCountryImage(key));

                countries.add(country);
            }

            Dao<Country, String> daoCountry = httpHandler.getDatabaseController().getDatabaseHelper().getDao(Country.class);

            daoCountry.create(countries);

            Log.i("flagDatabase", "Size : " + daoCountry.countOf());
            Log.i("flagDatabase", daoCountry.queryForId("France").toString());

            Log.i("Super", "rigolo");
        } catch(IOException e) {
            Log.i("flagDatabase", "Exception :" + e.getLocalizedMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Log.i("flagDatabase", "Finished!");
        }
    }
}

public class HomeActivity extends AppCompatActivity {

    SharedPreferences prefs = null;

    DatabaseController databaseController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(checkPermissions()) {
            initDatabase();
            checkFirstRun();
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

    private void initDatabase() {
        databaseController = new DatabaseController(this);

        HttpHandler httpHandler = new HttpHandler(this);
        HttpThread httpThread = new HttpThread(httpHandler);

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

    public DatabaseController getDatabaseController() {
        return databaseController;
    }
}

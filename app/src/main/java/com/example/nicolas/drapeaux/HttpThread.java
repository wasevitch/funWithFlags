package com.example.nicolas.drapeaux;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

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

/**
 * Created by Moi on 20/09/2017.
 */
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

            Dao<Country, String> daoCountry = httpHandler.getDatabaseController().getDaoCountry();

            daoCountry.create(countries);

            Log.i("flagDatabase", "Size : " + daoCountry.countOf());
            Log.i("flagDatabase", daoCountry.queryForId("France").toString());
        } catch(IOException e) {
            Log.i("flagDatabase", "Exception :" + e.getLocalizedMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Log.i("flagDatabase", "Finished!");
        }
    }
}

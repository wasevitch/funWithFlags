package com.example.nicolas.drapeaux;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.nicolas.drapeaux.db.model.Country;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountryController {

    DatabaseController databaseController;

    Map<String, Bitmap> countryFlags;
    List<Country> countries;

    public CountryController(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    public void initFlags() {
        countryFlags = new HashMap<>();

        try {
            Dao<Country, String> daoCountry = databaseController.getDaoCountry();

            countries = daoCountry.queryForAll();

            for(Country country : countries) {
                Bitmap bitmap = getBitmapFromBytes(country.getImage());
                countryFlags.put(country.getCountry(), bitmap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getFlag(String countryCode) {
        return countryFlags.get(countryCode);
    }

    public Country getCountry(int entry)
    {
        return countries.get(entry);
    }

    public Bitmap getCountryFlag(int entry) {
        Country country = getCountry(entry);

        if(country == null)
            return null;

        return getFlag(country.getCountry());
    }

    private Bitmap getBitmapFromBytes(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    public int getSize() {
        return countries.size();
    }

}

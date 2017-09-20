package com.example.nicolas.drapeaux;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.nicolas.drapeaux.db.model.Country;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlagController {

    DatabaseController databaseController;

    Map<String, Bitmap> countryFlags;

    public FlagController(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    public void initFlags() {
        countryFlags = new HashMap<>();

        try {
            Dao<Country, String> daoCountry = databaseController.getDaoCountry();

            List<Country> countries = daoCountry.queryForAll();

            for(Country country : countries) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(country.getImage(), 0, country.getImage().length);
                countryFlags.put(country.getCountry(), bitmap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getFlag(String countryCode) {
        return countryFlags.get(countryCode);
    }

}

package com.example.nicolas.drapeaux.controller;

import com.example.nicolas.drapeaux.db.model.SerialBitmap;
import com.example.nicolas.drapeaux.db.model.Country;
import com.j256.ormlite.dao.Dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountryController implements Serializable {

    DatabaseController databaseController;

    Map<String, SerialBitmap> countryFlags;
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
                SerialBitmap bitmap = getBitmapFromBytes(country.getImage());
                countryFlags.put(country.getCountry(), bitmap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SerialBitmap getFlag(String countryCode) {
        return countryFlags.get(countryCode);
    }

    public Country getCountry(int entry)
    {
        return countries.get(entry);
    }

    public SerialBitmap getCountryFlag(int entry) {
        Country country = getCountry(entry);

        if(country == null)
            return null;

        return getFlag(country.getCountry());
    }

    private SerialBitmap getBitmapFromBytes(byte[] data) {
        return new SerialBitmap(data);
    }

    public int getSize() {
        return countries.size();
    }

}

package com.example.ouzin.drapeaux.controller;

import android.content.Context;
import android.util.Pair;

import com.example.ouzin.drapeaux.db.model.Capital;
import com.example.ouzin.drapeaux.db.model.Question;
import com.example.ouzin.drapeaux.db.model.Country;
import com.example.ouzin.drapeaux.db.model.Quizz;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

/**
 * Created by Moi on 19/09/2017.
 */

public class DatabaseController {

    private DatabaseHelper databaseHelper;
    private ConnectionSource sqliteConnection;

    private Dao<Country, String> daoCountry = null;
    //private Dao<Capital, String> daoCapital = null;
    private Dao<Question, Pair<Country, Quizz>> daoBind = null;
    private Dao<Quizz, Integer> daoQuizz = null;

    public DatabaseController(Context context) {
        databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        sqliteConnection = new AndroidConnectionSource(databaseHelper);
    }

    public DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }

    public ConnectionSource getSqliteConnection() {
        return sqliteConnection;
    }

    public Dao<Country, String> getDaoCountry() throws SQLException {
        if(daoCountry == null) {
            daoCountry = databaseHelper.getDao(Country.class);
        }
        return daoCountry;
    }

    /*
    public Dao<Capital, String> getDaoCapital() throws SQLException {
        if(daoCapital == null){
            daoCapital = databaseHelper.getDao(Capital.class);
        }
        return daoCapital;
    }
    */

    public Dao<Question, Pair<Country, Quizz>> getDaoBind() throws SQLException {
        if(daoBind == null) {
            daoBind = databaseHelper.getDao(Question.class);
        }
        return daoBind;
    }

    public Dao<Quizz, Integer> getDaoQuizz() throws SQLException {
        if(daoQuizz == null) {
            daoQuizz = databaseHelper.getDao(Quizz.class);
        }
        return daoQuizz;
    }

}

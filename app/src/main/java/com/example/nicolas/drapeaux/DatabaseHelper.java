package com.example.nicolas.drapeaux;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nicolas.drapeaux.db.model.Bind;
import com.example.nicolas.drapeaux.db.model.Country;
import com.example.nicolas.drapeaux.db.model.Quizz;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "funwithflags.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Country, String> countryDao = null;

    private RuntimeExceptionDao<Country, String> countryRuntimeDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Country.class);
            TableUtils.createTable(connectionSource, Bind.class);
            TableUtils.createTable(connectionSource, Quizz.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }

        /* // here we try inserting data in the on-create as a test
        RuntimeExceptionDao<SimpleData, Integer> dao = getSimpleDataDao();
        long millis = System.currentTimeMillis();
        // create some entries in the onCreate
        SimpleData simple = new SimpleData(millis);
        dao.create(simple);
        simple = new SimpleData(millis + 1);
        dao.create(simple);
        Log.i(DatabaseHelper.class.getName(), "created new entries in onCreate: " + millis);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Country.class, true);
            TableUtils.dropTable(connectionSource, Bind.class, true);
            TableUtils.dropTable(connectionSource, Quizz.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        super.close();
        countryDao = null;
        countryRuntimeDao = null;
    }
}

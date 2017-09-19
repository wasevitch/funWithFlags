package com.example.nicolas.drapeaux;

import android.content.Context;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.support.ConnectionSource;

/**
 * Created by Moi on 19/09/2017.
 */

public class DatabaseController {

    private DatabaseHelper databaseHelper;
    private ConnectionSource sqliteConnection;

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
}

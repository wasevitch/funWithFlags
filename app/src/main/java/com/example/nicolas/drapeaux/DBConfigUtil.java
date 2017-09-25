package com.example.nicolas.drapeaux;

import android.database.SQLException;

import com.example.nicolas.drapeaux.db.model.Question;
import com.example.nicolas.drapeaux.db.model.Country;
import com.example.nicolas.drapeaux.db.model.Quizz;
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;

public class DBConfigUtil extends OrmLiteConfigUtil {
    private static final Class<?>[] classes = new Class[] {Country.class, Question.class, Quizz.class};

    public static void main(String[] args) throws IOException, SQLException, java.sql.SQLException {
        writeConfigFile("ormlite_config.txt", classes);
    }

}
package com.example.ouzin.drapeaux.controller;

import android.database.SQLException;

import com.example.ouzin.drapeaux.db.model.Capital;
import com.example.ouzin.drapeaux.db.model.Question;
import com.example.ouzin.drapeaux.db.model.Country;
import com.example.ouzin.drapeaux.db.model.Quizz;
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;

public class DBConfigUtil extends OrmLiteConfigUtil {
    private static final Class<?>[] classes = new Class[] {Country.class, Capital.class, Question.class, Quizz.class};

    public static void main(String[] args) throws IOException, SQLException, java.sql.SQLException {
        writeConfigFile("ormlite_config.txt", classes);
    }

}
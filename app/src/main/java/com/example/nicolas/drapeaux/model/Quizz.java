package com.example.nicolas.drapeaux.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "quizz")
public class Quizz {

    @DatabaseField(generatedId = true)
    private int id;

}

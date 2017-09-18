package com.example.nicolas.drapeaux.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "bind")
public class Bind {

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true, uniqueCombo = true)
    private Country country;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true, uniqueCombo = true)
    private Quizz quizz;

    @DatabaseField
    private int direction;

}

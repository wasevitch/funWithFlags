package com.example.nicolas.drapeaux.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "bind")
public class Bind implements Serializable {

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true, uniqueCombo = true)
    private Country country;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true, uniqueCombo = true)
    private Quizz quizz;

    @DatabaseField
    private int direction;

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Quizz getQuizz() {
        return quizz;
    }

    public void setQuizz(Quizz quizz) {
        this.quizz = quizz;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}

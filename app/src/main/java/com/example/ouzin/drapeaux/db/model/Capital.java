package com.example.ouzin.drapeaux.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "capital")
public class Capital implements Serializable {
    @DatabaseField(id = true)
    private String capital;

    public Capital() {

    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }
}



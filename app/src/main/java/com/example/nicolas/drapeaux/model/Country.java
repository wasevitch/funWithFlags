package com.example.nicolas.drapeaux.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "countries")
public class Country {

    @DatabaseField(id = true)
    private String country;

    @DatabaseField
    private String image;

    public Country() {

    }

}

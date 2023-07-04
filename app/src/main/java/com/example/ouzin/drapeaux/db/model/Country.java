package com.example.ouzin.drapeaux.db.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "countries")
public class Country implements Serializable {

    @DatabaseField(id = true)
    private String country;
    @DatabaseField(id = true)
    private String capital;

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] image;

    public Country() {

    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}

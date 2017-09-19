package com.example.nicolas.drapeaux.db.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "countries")
public class Country implements Serializable {

    @DatabaseField(id = true)
    private String country;

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

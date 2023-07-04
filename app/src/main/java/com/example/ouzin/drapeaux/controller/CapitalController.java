package com.example.ouzin.drapeaux.controller;

import com.example.ouzin.drapeaux.db.model.Capital;
import com.example.ouzin.drapeaux.db.model.Country;
import com.j256.ormlite.dao.Dao;

import java.io.Serializable;
import java.util.List;

public class CapitalController implements Serializable {
    DatabaseController databaseController;
    List<Capital> capitales;

    public CapitalController(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }


}

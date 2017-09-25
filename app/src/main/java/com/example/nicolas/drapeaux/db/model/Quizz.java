package com.example.nicolas.drapeaux.db.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@DatabaseTable(tableName = "quizz")
public class Quizz implements Serializable {

    @DatabaseField(generatedId = true)
    private int id;

    @ForeignCollectionField(columnName = "questions")
    private ForeignCollection<Question> questions;

    List<Question> localQuestions;

    public Quizz() {
        localQuestions = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Question> getQuestions() {
        return localQuestions;
    }

    public void addQuestion(Question question) {
        localQuestions.add(question);
    }

    public void updateLocalList() {

    }

    public void saveRemoteList() {
        for(Question question : localQuestions) {

        }
    }
}

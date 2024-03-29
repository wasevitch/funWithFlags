package com.example.ouzin.drapeaux.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "question")
public class Question implements Serializable {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Country country;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Quizz quizz;

    @DatabaseField
    private int type;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Country firstAnswer;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Country secondAnwser;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Country thirdAnswer;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Country fourthAnswer;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Country playerAnwser;

    final public int BUTTONS = 0;
    final public int FLAGS = 1;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Country getFirstAnswer() {
        return firstAnswer;
    }

    public void setFirstAnswer(Country firstAnswer) {
        this.firstAnswer = firstAnswer;
    }

    public Country getSecondAnswer() {
        return secondAnwser;
    }

    public void setSecondAnwser(Country secondAnwser) {
        this.secondAnwser = secondAnwser;
    }

    public Country getThirdAnswer() {
        return thirdAnswer;
    }

    public void setThirdAnswer(Country thirdAnswer) {
        this.thirdAnswer = thirdAnswer;
    }

    public Country getFourthAnswer() {
        return fourthAnswer;
    }

    public void setFourthAnswer(Country fourthAnswer) {
        this.fourthAnswer = fourthAnswer;
    }

    public Country getPlayerAnwser() {
        return playerAnwser;
    }

    public void setPlayerAnwser(Country playerAnwser) {
        this.playerAnwser = playerAnwser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Country getCountryByName(String name) {
        if(firstAnswer.getCountry().equals(name))
            return firstAnswer;
        else if(secondAnwser.getCountry().equals(name))
            return secondAnwser;
        else if(thirdAnswer.getCountry().equals(name))
            return thirdAnswer;
        else if(fourthAnswer.getCountry().equals(name))
            return fourthAnswer;

        return null;
    }
}

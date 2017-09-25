package com.example.nicolas.drapeaux.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "question")
public class Question implements Serializable {

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true, uniqueCombo = true)
    private Country country;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true, uniqueCombo = true)
    private Quizz quizz;

    @DatabaseField
    private int type;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Country firstAnswer;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Country secondAnwser;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Country thirdAnswer;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Country fourthAnswer;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
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

    public Country getSecondAnwser() {
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
}

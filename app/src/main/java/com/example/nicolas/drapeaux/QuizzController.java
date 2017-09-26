package com.example.nicolas.drapeaux;

import com.example.nicolas.drapeaux.db.model.Question;
import com.example.nicolas.drapeaux.db.model.Quizz;
import com.j256.ormlite.dao.Dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class QuizzController implements Serializable {

    private DatabaseController databaseController;
    private CountryController countryController;

    private Quizz quizz = null;
    private Dao<Quizz, Integer> daoQuizz;

    private final int nbCountries = 4;
    private final int nbQuestions = 10;

    Iterator<Question> iterator;

    public QuizzController(DatabaseController databaseController, CountryController countryController) {
        this.databaseController = databaseController;
        try {
            daoQuizz = databaseController.getDaoQuizz();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.countryController = countryController;
    }

    public void newQuizz() {
        quizz = new Quizz();

        try {
            daoQuizz.create(quizz);
            daoQuizz.assignEmptyForeignCollection(quizz, "questions");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < nbQuestions; ++i) {
            newQuestion();
        }

        quizz.saveRemoteList();
        quizz.updateLocalList();

        iterator = quizz.getQuestions().iterator();
    }

    public Question newQuestion() {
        if(quizz == null)
            return null;

        Question question = new Question();

        Random randomGenerator = new Random();
        Set<Integer> numbers = new LinkedHashSet<>();

        while(numbers.size() < nbCountries) {
            Integer next = randomGenerator.nextInt(countryController.getSize());
            numbers.add(next);
        }

        Object[] numbersArray = numbers.toArray();

        int selectedAnwser = (int)(Math.random() * nbCountries);

        question.setCountry(countryController.getCountry((int)numbersArray[selectedAnwser]));

        question.setFirstAnswer(countryController.getCountry((int)numbersArray[0]));
        question.setSecondAnwser(countryController.getCountry((int)numbersArray[1]));
        question.setThirdAnswer(countryController.getCountry((int)numbersArray[2]));
        question.setFourthAnswer(countryController.getCountry((int)numbersArray[3]));

        int questionType = (int)(Math.random() * 2);

        question.setType(questionType);
        question.setQuizz(quizz);

        quizz.addQuestion(question);

        return question;
    }

    public void saveQuizz() {
        try {
            daoQuizz.create(quizz);
            if(quizz.getQuestions() == null) {
                daoQuizz.assignEmptyForeignCollection(quizz, "questions");
            }
            daoQuizz.update(quizz);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Question getNext() {
        return iterator.next();
    }

    public Iterator<Question> getIterator() {
        return iterator;
    }

    public Quizz getQuizz() {
        return quizz;
    }
}

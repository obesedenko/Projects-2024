package org.example.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Question {
    protected String question;
    protected String correctAnswer;
    protected String type;
    protected String category;
    protected String difficulty;
    protected ArrayList<String> incorrectAnswers=new ArrayList<>();

    public Question(String question, String correctAnswer, String type, String category, String difficulty) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.type = type;
        this.category = category;
        this.difficulty = difficulty;
        this.incorrectAnswers = new ArrayList<>();
    }

    public void addIncorrectAnswer(String incorrectAnswer) {
        this.incorrectAnswers.add(incorrectAnswer);
    }

    // Getter methods
    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public ArrayList<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public String getDifficulty() {
        return difficulty;
    }


    @Override
    public String toString() {
        return "Category: " + category + "\nDifficulty: " + difficulty + "\nQuestion: " + question + "\nCorrect Answer: " + correctAnswer + "\nIncorrect Answers: " + incorrectAnswers;
    }


    public abstract ArrayList<String> printQuestion();
}
package org.example.Model;

import java.util.ArrayList;
import java.util.Collections;

public class TrueFalse extends Question{

    public TrueFalse(String question, String correctAnswer, String type, String category, String difficulty) {
        super(question, correctAnswer, type, category, difficulty);
    }

    @Override
    public ArrayList<String> printQuestion(){
        StringBuilder sb=new StringBuilder();;

        sb.append("Category: ").append(category).append(" difficulty: ").append(difficulty).append("\n");
        sb.append("Question :").append(question).append("\n");
        ArrayList<String> answers = new ArrayList<>(getIncorrectAnswers());


        answers.add("True");
        answers.add("False");
        sb.append("1. True \n2.False");
        System.out.println(sb.toString());
        return answers;
    }
}

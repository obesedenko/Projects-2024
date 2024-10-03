package org.example.Model;

import java.util.ArrayList;
import java.util.Collections;

public class MCQs extends Question{


    public MCQs(String question, String correctAnswer, String type, String category, String difficulty) {
        super(question, correctAnswer, type, category, difficulty);
    }

    @Override
    public ArrayList<String> printQuestion(){
        StringBuilder sb=new StringBuilder();;

        sb.append("Category: ").append(category).append(" difficulty: ").append(difficulty).append("\n");
        sb.append("Question :").append(question).append("\n");
        ArrayList<String> answers = new ArrayList<>(getIncorrectAnswers());

        answers.add(correctAnswer);
        Collections.shuffle(answers);

        for (int i = 0; i < answers.size(); i++) {
            sb.append(i + 1).append(". ").append(answers.get(i)).append("\n");
        }
        System.out.println(sb.toString());
        return answers;
    }
}

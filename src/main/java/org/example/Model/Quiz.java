package org.example.Model;

import java.time.LocalDateTime;

public class Quiz {
    public int num_of_question;
    public int correct_answers;
    public String dateTime;


    public Quiz(){
        dateTime = LocalDateTime.now().toString();
    }
    public void solveQuestion(boolean correct_answer) {
        if(correct_answer) {
            correct_answers++;
        }
        num_of_question++;
    }

    @Override
    public String toString() {
        return  num_of_question+ "             "+ correct_answers +"         "+dateTime;
    }
}

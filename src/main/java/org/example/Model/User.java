package org.example.Model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String userpassword;
    private ArrayList<Quiz> quizzes=new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }


    public User(String username, String userpassword) {
        this.username = username;
        this.userpassword = userpassword;
    }

    public void register() {
        // Validate username and password
        if (isValidUsername(username) && isValidPassword(userpassword)) {
            // Register the user successfully
            System.out.println("User registered successfully!");
        } else {
            // Notify observers of an error
            // notifyObservers("Invalid username or password.");
        }
    }

    private boolean isValidUsername(String username) {
        // Implement your username validation logic here
        return true; // Placeholder for validation
    }

    private boolean isValidPassword(String password) {
        // Implement your password validation logic here
        return true; // Placeholder for validation
    }


    public void addQuiz(Quiz quiz){
        this.quizzes.addFirst(quiz);
    }

    public ArrayList<Quiz> getQuizzes() {
        return quizzes;
    }

    public double calculateAvg() {
        double sum=0;
        for (Quiz quiz : quizzes) {
            sum += quiz.correct_answers;
        }
        return sum>0 ? sum/quizzes.size() : 0;
    }
}

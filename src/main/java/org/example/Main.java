package org.example;

import org.example.Api.OpenDbApi;
import org.example.DataLayer.Users; // brings the datalayer package
import org.example.Model.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ErrorLogger errorLogger = new ErrorLogger();

    public static void main(String[] args) {
        // Add observers to the error logger
        errorLogger.addObserver(new ErrorDisplay("Console Display"));

        Scanner scanner = new Scanner(System.in);
        User user = null;

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    user = new User(username, password);
                    user.register();

                    Users.getInstance().add(user);
                    System.out.println("User registered and saved successfully!");
                    break;

                case 2:
                    System.out.print("Enter username: ");
                    String username2 = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password2 = scanner.nextLine();
                    User user1 = Users.getInstance().loginUser(username2, password2);

                    if (user1 != null) {
                        showOptions(user1);
                    } else {
                        System.out.println("Login failed! Incorrect username or password.");
                    }
                    break;

                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void showOptions(User user) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            // Display the menu options
            System.out.println("1. Solve test");
            System.out.println("2. Show history");
            System.out.println("3. Show analytics");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Solving test...");
                    try {
                        Quiz quiz = doTest(user);
                        user.addQuiz(quiz);
                        Users.getInstance().saveUsersToFile();
                    } catch (IOException e) {
                        errorLogger.logError("IOException occurred: " + e.getMessage());
                    } catch (Exception e) {
                        errorLogger.logError("Unexpected error: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Showing history...");
                    showHistory(user);
                    break;
                case 3:
                    System.out.println("Showing analytics...");
                    String analytics = Users.getInstance().showAnalytics();
                    System.out.println(analytics);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    errorLogger.logError("Unexpected Input: Enter invalid option");
                    System.out.println("Invalid option. Please try again.");
            }

        } while (choice != 4); // Loop until the user chooses to exit
    }

    private static void showHistory(User user) {
        ArrayList<Quiz> quizzes = user.getQuizzes();
        int i = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("#").append(".  ").append("Questions      ").append("Correct").append("         ").append("Date & time").append("\n");
        for (Quiz q : quizzes) {
            sb.append(i).append(".  ").append(q.toString()).append("\n");
            i++;
        }
        System.out.println(sb.toString());
    }

    public static Quiz doTest(User user) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Quiz quiz = new Quiz();
        ArrayList<Question> questions = OpenDbApi.getQuestionsV2();
        int score = 0;

        for (Question question : questions) {
            ArrayList<String> answers = question.printQuestion();
            System.out.print("Enter your choice (1-" + answers.size() + "): ");
            int userChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            String selectedAnswer = answers.get(userChoice - 1);
            if (selectedAnswer.equals(question.getCorrectAnswer())) {
                System.out.println("Correct!");
                quiz.solveQuestion(true);
            } else {
                quiz.solveQuestion(false);
                System.out.println("Incorrect. The correct answer was: " + question.getCorrectAnswer());
            }
        }

        System.out.println("Test completed. Your score: " + score + "/" + questions.size());
        return quiz;
    }
}

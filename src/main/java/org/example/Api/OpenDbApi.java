package org.example.Api;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.example.Model.MCQs;
import org.example.Model.Question;
import org.example.Model.TrueFalse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class OpenDbApi {
    public static void testException() throws IOException{
        throw new IOException("Excepton");
    }
    public static ArrayList<Question> getQuestionsV2() throws IOException {
        ArrayList<Question> questionsList = new ArrayList<>();
        int retries = 5; // Number of retries
        int delay = 1000; // Initial delay in milliseconds

        for (int attempt = 0; attempt < retries; attempt++) {
            try {
                HttpURLConnection conn = null;
                Scanner sc = null;
                URL url = new URL("https://opentdb.com/api.php?amount=10");
                StringBuilder inline = null;

                // Establish the connection
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
                conn.connect();

                sc = new Scanner(conn.getInputStream());

                inline = new StringBuilder();
                while (sc.hasNext()) {
                    inline.append(sc.nextLine());
                }
                sc.close();

                // Parse the JSON response
                JsonObject jsonResponse = JsonParser.parseString(inline.toString()).getAsJsonObject();
                JsonArray resultsArray = jsonResponse.getAsJsonArray("results");

                // Iterate over the JSON array and add to the Question objects
                for (int i = 0; i < resultsArray.size(); i++) {
                    JsonObject questionObj = resultsArray.get(i).getAsJsonObject();
                    Question question;
                    if (questionObj.get("type").getAsString().equals("boolean")) {
                        question = new TrueFalse(
                                questionObj.get("question").getAsString(),
                                questionObj.get("correct_answer").getAsString(),
                                questionObj.get("type").getAsString(),
                                questionObj.get("category").getAsString(),
                                questionObj.get("difficulty").getAsString()
                        );
                    } else {
                        question = new MCQs(
                                questionObj.get("question").getAsString(),
                                questionObj.get("correct_answer").getAsString(),
                                questionObj.get("type").getAsString(),
                                questionObj.get("category").getAsString(),
                                questionObj.get("difficulty").getAsString()
                        );
                    }

                    // Add incorrect answers if the type is multiple-choice
                    JsonArray incorrectAnswersArray = questionObj.getAsJsonArray("incorrect_answers");
                    for (int j = 0; j < incorrectAnswersArray.size(); j++) {
                        question.addIncorrectAnswer(incorrectAnswersArray.get(j).getAsString());
                    }

                    questionsList.add(question);
                }
                break;

            } catch (IOException e) {
                throw e;
            }
        }

        return questionsList;
    }

}

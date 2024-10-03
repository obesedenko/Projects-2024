package org.example.DataLayer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.Model.User;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Users {
    private ArrayList<User> users;
    private static Users usersIns=new Users();
    private static final String FILE_PATH = "users.json";

    private Users() {
        createFileIfNotExists();
        users = loadUsersFromFile();
        if (users == null) {
            users = new ArrayList<>();
        }
    }

    public static Users getInstance() {
         if (usersIns == null) {
            usersIns = new Users();
        }
        return usersIns;
    }

    public void add(User user) {
        users.add(user);
        saveUsersToFile();
    }

    public User loginUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getUserpassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public void saveUsersToFile() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            Gson gson = new Gson();
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<User> loadUsersFromFile() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Gson gson = new Gson();
            Type userListType = new TypeToken<ArrayList<User>>() {}.getType();
            return gson.fromJson(reader, userListType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void createFileIfNotExists() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getAbsolutePath());
                } else {
                    System.out.println("Failed to create file.");
                }
            } else {
                System.out.println("File already exists at: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String showAnalytics() {
        User bestUser=null;
        double bestAvg=0;


        User mostQuizUser=null;
        for (User user : users) {
            double avg = user.calculateAvg();
            if(avg > bestAvg) {
                bestAvg = avg;
                bestUser = user;
            }

            if(mostQuizUser ==null || user.getQuizzes().size() > mostQuizUser.getQuizzes().size()){
                mostQuizUser = user;
            }
        }
        StringBuilder sb=new StringBuilder();
        if(bestUser==null) {
            sb.append("No user exist");
            return sb.toString();
        }

        sb.append("\n\n").append("Best Score").append('\n');
        sb.append("Username: ").append(bestUser.getUsername()).append('\n');
        sb.append("# of Quizzes: ").append(bestUser.getQuizzes().size()).append('\n');
        sb.append("Average Score: ").append(bestAvg).append('\n');

        sb.append("\n\n").append("Most Solved Quiz").append('\n');
        sb.append("Username: ").append(mostQuizUser.getUsername()).append('\n');
        sb.append("# of Quizzes: ").append(mostQuizUser.getQuizzes().size()).append('\n');
        return sb.toString();
    }
}

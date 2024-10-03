package org.example.Model;
import org.example.Interface.Observer;
import org.example.Interface.Subject;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ErrorLogger implements Subject {
    private final List<Observer> observers = new ArrayList<>();
    private final List<String> errorMessages = new ArrayList<>();
    private final String LOG_FILE_PATH="error.log";
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String errorMessage) {
        for (Observer observer : observers) {
            observer.update(errorMessage);
        }
    }

    public void logError(String errorMessage) {
        errorMessages.add(errorMessage);
        notifyObservers(errorMessage);
        appendErrorToFile(errorMessage);
    }


    private void appendErrorToFile(String errorMessage) {
        try (FileWriter fileWriter = new FileWriter(LOG_FILE_PATH, true)) { // 'true' enables append mode
            // Get current timestamp
            LocalDateTime currentTime = LocalDateTime.now();
            // Format the timestamp
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = currentTime.format(formatter);

            // Write the timestamp and the error message to the file
            fileWriter.write("[" + timestamp + "] " + errorMessage + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


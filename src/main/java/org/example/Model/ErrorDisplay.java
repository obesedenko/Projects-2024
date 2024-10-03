package org.example.Model;

import org.example.Interface.Observer;

public class ErrorDisplay implements Observer {
    private final String displayName;

    public ErrorDisplay(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public void update(String errorMessage) {
        System.out.println(displayName + " received error: " + errorMessage);
    }
}

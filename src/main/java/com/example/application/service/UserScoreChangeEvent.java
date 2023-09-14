package com.example.application.service;

import org.springframework.context.ApplicationEvent;

public class UserScoreChangeEvent extends ApplicationEvent {
    private final int newUserScore;

    public UserScoreChangeEvent(Object source, int newUserScore) {
        super(source);
        this.newUserScore = newUserScore;
    }

    public int getNewUserScore() {
        return newUserScore;
    }
}

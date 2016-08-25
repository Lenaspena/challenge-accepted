package com.challengeaccepted.loggers;

public class HerokuLoggerException extends Exception {

    private static final String DEFAULT_MESSAGE = "Heroku log error!";

    public HerokuLoggerException() {
        super(DEFAULT_MESSAGE);
    }

    public HerokuLoggerException(String message) {
        super(message);
    }

}
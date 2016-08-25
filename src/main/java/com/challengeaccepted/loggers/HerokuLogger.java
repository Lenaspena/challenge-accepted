package com.challengeaccepted.loggers;

public class HerokuLogger {

    private static final String INFO = "INFO: ";
    private static final String WARNING = "WARNING: ";
    private static final String ERROR = "ERROR: ";

    public void writeToInfoLog(String message) throws HerokuLoggerException {
        if (message == null) {
            throw new HerokuLoggerException();
        }
        System.out.println(INFO + message);
    }

    public void writeToWarningLog(String message) throws HerokuLoggerException {
        if (message == null) {
            throw new HerokuLoggerException();
        }
        System.out.println(WARNING + message);
    }

    public void writeToErrorLog(String message) throws HerokuLoggerException {
        if (message == null) {
            throw new HerokuLoggerException();
        }
        System.err.println(ERROR + message);
    }

}
package com.challengeaccepted.loggers;

import com.challengeaccepted.services.UserService;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerUserService {

    private static final Logger LOG = Logger.getLogger(UserService.class.getName());
    private FileHandler handler;

    public LoggerUserService() {
        if(handler == null) {
            try {
                handler = new FileHandler("loggs/logg-user-service.txt", true);
                LOG.addHandler(handler);
                LOG.setLevel(Level.ALL);
                handler.setFormatter(new SimpleFormatter());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Logger getLOG() {
        return LOG;
    }
}

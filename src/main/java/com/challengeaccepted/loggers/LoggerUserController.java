package com.challengeaccepted.loggers;

import com.challengeaccepted.controllers.UserRestController;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerUserController {

    private static final Logger LOG = Logger.getLogger(UserRestController.class.getName());
    private FileHandler handler;

    public LoggerUserController() {
        if(handler == null) {
            try {
                handler = new FileHandler("loggs/logg-user-controller.txt", true);
                LOG.addHandler(handler);
                LOG.setLevel(Level.ALL);
                handler.setFormatter(new SimpleFormatter());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Logger getLogger() {
        return LOG;
    }
}

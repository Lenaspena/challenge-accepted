package com.challengeaccepted.loggers;

import com.challengeaccepted.controllers.ChallengeRestController;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerChallengeController {

    private static final Logger LOG = Logger.getLogger(ChallengeRestController.class.getName());
    private FileHandler handler;

    public LoggerChallengeController() {
        if(handler == null) {
            try {
                handler = new FileHandler("loggs/logg-challenge-controller.txt", true);
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

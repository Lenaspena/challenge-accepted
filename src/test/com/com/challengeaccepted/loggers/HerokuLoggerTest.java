package com.challengeaccepted.loggers;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class HerokuLoggerTest {

    private HerokuLogger herokuLogger;
    private ByteArrayOutputStream outputStream;
    private ByteArrayOutputStream errorStream;
    private static final String INFO_TEST_MESSAGE = "Information goes here";
    private static final String WARNING_TEST_MESSAGE = "Warning goes here";
    private static final String ERROR_TEST_MESSAGE = "Error goes here";

    @Before
    public void setUp() throws Exception {
        herokuLogger = new HerokuLogger();
        outputStream = new ByteArrayOutputStream();
        errorStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        System.setErr(new PrintStream(errorStream));
    }

    @After
    public void tearDown() throws Exception {
        herokuLogger = null;
    }

    @Test(expected = HerokuLoggerException.class)
    public void testWriteToInfoLog_Should_Throw_HerokuLoggerException() throws Exception {
        herokuLogger.writeToInfoLog(null);
    }

    @Test
    public void testWriteToInfoLog() throws Exception {
        herokuLogger.writeToInfoLog(INFO_TEST_MESSAGE);
        assertEquals("INFO: Information goes here\n", outputStream.toString());
    }

    @Test(expected = HerokuLoggerException.class)
    public void testWriteToWarningLog_Should_Throw_HerokuLoggerException() throws Exception {
        herokuLogger.writeToWarningLog(null);
    }

    @Test
    public void testWriteToWarningLog() throws Exception {
        herokuLogger.writeToWarningLog(WARNING_TEST_MESSAGE);
        assertEquals("WARNING: Warning goes here\n", outputStream.toString());
    }

    @Test(expected = HerokuLoggerException.class)
    public void testWriteToErrorLog_Should_Throw_HerokuLoggerException() throws Exception {
        herokuLogger.writeToErrorLog(null);
    }

    @Test
    public void testWriteToErrorLog() throws Exception {
        herokuLogger.writeToErrorLog(ERROR_TEST_MESSAGE);
        assertEquals("ERROR: Error goes here\n", errorStream.toString());
    }

}
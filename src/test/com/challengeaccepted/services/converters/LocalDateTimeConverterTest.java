package com.challengeaccepted.services.converters;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.Assert.*;


public class LocalDateTimeConverterTest {

    LocalDateTimeConverter localDateTimeConverter;

    @Before
    public void setUp() throws Exception {
        localDateTimeConverter = new LocalDateTimeConverter();

    }

    @After
    public void tearDown() throws Exception {
        localDateTimeConverter = null;
    }

    @Test
    public void testConvertToDatabaseColumn_Should_Not_Return_Null() throws Exception {
        assertNotNull(localDateTimeConverter.convertToDatabaseColumn(LocalDateTime.now()));
        assertNotNull(localDateTimeConverter.convertToDatabaseColumn(null));
    }

    @Test
    public void testConvertToDatabaseColumn_Should_Return_Timestamp() throws Exception {
        assertEquals(Timestamp.valueOf("2020-12-12 12:04:04"), localDateTimeConverter.convertToDatabaseColumn(LocalDateTime.of(2020, 12, 12, 12, 4, 4)));
    }

    @Test
    public void testConvertToEntityAttribute_Should_Not_Return_Null() throws Exception {
        assertNotNull(localDateTimeConverter.convertToEntityAttribute(Timestamp.valueOf(LocalDateTime.now())));
        assertNotNull(localDateTimeConverter.convertToEntityAttribute(null));

    }

    @Test
    public void testConvertToEntityAttribute_Should_Return_LocalDateTime() throws Exception {
        assertEquals(LocalDateTime.of(2020, 12, 12, 12, 4, 4), localDateTimeConverter.convertToEntityAttribute(Timestamp.valueOf("2020-12-12 12:04:04")));

    }
}
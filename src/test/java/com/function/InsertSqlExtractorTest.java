package com.function;

import net.sf.jsqlparser.JSQLParserException;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class InsertSqlExtractorTest {

    @Test
    public void tableNameExtractor() throws JSQLParserException {
        String insertSql = "INSERT Patient (id, name, gender, age) VALUES ('10001', 'Dennis He', 'Male', 24)";
        String actualTableName = InsertSqlExtractor.tableNameExtractor(insertSql);
        String expectedTableName = "Patient";
        assertEquals(expectedTableName, actualTableName);
    }

    @Test
    public void valuesExtractor() throws JSQLParserException {
        String insertSql = "INSERT Patient (id, name, gender, age) VALUES ('10001', 'Dennis He', 'Male', 24)";
        Map<String,String> actualMap = InsertSqlExtractor.valuesExtractor(insertSql);
        assertTrue(actualMap.containsKey("gender"));
        assertTrue(actualMap.containsKey("id"));
        assertTrue(actualMap.containsKey("name"));
        assertTrue(actualMap.containsKey("resourceType"));
        assertTrue(actualMap.containsKey("age"));

        assertEquals("Male",actualMap.get("gender"));
        assertEquals("10001",actualMap.get("id"));
        assertEquals("Dennis He",actualMap.get("name"));
        assertEquals("24",actualMap.get("age"));
        assertEquals("Patient",actualMap.get("resourceType"));




    }
}
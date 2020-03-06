package com.function;

import net.sf.jsqlparser.JSQLParserException;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class UpdateSqlExtractorTest {

    @Test
    public void tableNameExtractorTest() throws JSQLParserException {
        String sqlQuery = "UPDATE Patient SET Address = 'Zhongshan 23', City = 'Nanjing' WHERE LastName = 'Wilson'";
        String actualTableName = UpdateSqlExtractor.tableNameExtractor(sqlQuery);
        String expectedTableName = "Patient";
        assertEquals(expectedTableName,actualTableName);
    }

    @Test
    public void setValuesExtractorTest() throws JSQLParserException {
        String sqlQuery = "UPDATE Patient SET Address = 'Zhongshan 23', City = 'Nanjing' WHERE LastName = 'Wilson'";
        Map<String,String> valueMap = UpdateSqlExtractor.setValuesExtractor(sqlQuery);
        assertTrue(valueMap.containsKey("Address"));
        assertTrue(valueMap.containsKey("City"));
        assertEquals("Zhongshan 23",valueMap.get("Address"));
        assertEquals("Nanjing",valueMap.get("City"));
    }

    @Test
    public void whereExpressionExtractorTest() throws JSQLParserException {
        String sqlQuery = "UPDATE Patient SET Address = 'Zhongshan 23', City = 'Nanjing' WHERE LastName = 'Wilson' and age = 25";
        String[] whereClause = UpdateSqlExtractor.whereExpressionExtractor(sqlQuery);
        assertEquals(2,whereClause.length);
        assertEquals("LastName = 'Wilson'",whereClause[0]);
        assertEquals("age = 25",whereClause[1]);

    }
}
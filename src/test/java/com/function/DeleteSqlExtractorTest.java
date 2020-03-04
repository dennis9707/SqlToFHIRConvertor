package com.function;

import net.sf.jsqlparser.JSQLParserException;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeleteSqlExtractorTest {

    @Test
    public void tableNameExtractorTest() throws JSQLParserException {
        String sqlDeleteQuery = "DELETE from Patient where name = 'Dennis'";
        String expectedTable = "Patient";
        String actualTable = DeleteSqlExtractor.tableNameExtractor(sqlDeleteQuery);
        assertEquals(expectedTable,actualTable);
    }

    @Test
    public void whereExpressionExtractorTest() throws JSQLParserException {
        String sqlDeleteQuery = "DELETE from Patient where name = 'Dennis' and age = 23";
        String[] whereClause = DeleteSqlExtractor.whereExpressionExtractor(sqlDeleteQuery);
        assertEquals("name = 'Dennis'", whereClause[0]);
        assertEquals("age = 23", whereClause[1]);

        String sqlDeleteQuery2 = "DELETE from Patient where name = 'Dennis' or school = 'UCL'";
        String[] whereClause2 = DeleteSqlExtractor.whereExpressionExtractor(sqlDeleteQuery2);
        assertEquals("name = 'Dennis'", whereClause2[0]);
        assertEquals("school = 'UCL'", whereClause2[1]);

    }
}
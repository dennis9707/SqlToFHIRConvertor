package com.function;

import org.junit.Test;

import static org.junit.Assert.*;

public class ErrorMessageTest {

    @Test
    public void testToString() {
        String errorSqlQuery = "SEECT * From School Where Name = 'lalala' and age = 25";
        try{
            String tableName = SelectSqlExtractor.tableNameExtractor(errorSqlQuery);
        }catch (Exception e){
            ErrorMessage errorMessage = new ErrorMessage("There is an syntax error in your SQL query", e.toString());
            String expectedErrorMessage = "{\n" +
                    "  \"errorMessage\": \"There is an syntax error in your SQL query\",\n" +
                    "  \"exceptionMessage\": \"net.sf.jsqlparser.JSQLParserException\"\n" +
                    "}";
            assertEquals(expectedErrorMessage, errorMessage.toString());
        }
    }
}
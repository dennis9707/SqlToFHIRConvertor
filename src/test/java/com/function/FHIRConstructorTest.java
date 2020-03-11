package com.function;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class FHIRConstructorTest {

    @Test
    public void queryParameterConstructorTest() {
        String[] whereClauses = new String[9];
        whereClauses[0] = "firstName = 'Dennis'";
        whereClauses[1] = "age >= 25";
        whereClauses[2] = "city LIKE '%olo%'";
        whereClauses[3] = "grade != 60";
        whereClauses[4] = "lastName LIKE 'He%'";
        whereClauses[5] = "birthday > '1990-02-28'";
        whereClauses[6] = "school != 'UCL'";
        whereClauses[7] = "height <= 190";
        whereClauses[8] = "arriveDate < '2020-05-01'";
        Map<String,String> queryParameters = FHIRConstructor.queryParameterConstructor(whereClauses);
//        Judge if the key contains
        assertTrue(queryParameters.containsKey("firstName:exact"));
        assertTrue(queryParameters.containsKey("age"));
        assertTrue(queryParameters.containsKey("city:contains"));
        assertTrue(queryParameters.containsKey("grade"));
        assertTrue(queryParameters.containsKey("lastName"));
        assertTrue(queryParameters.containsKey("birthday"));
        assertTrue(queryParameters.containsKey("school:not"));
        assertTrue(queryParameters.containsKey("height"));
        assertTrue(queryParameters.containsKey("arriveDate"));


//        Judge if the value is in the fhir query format
        assertEquals("Dennis",queryParameters.get("firstName:exact"));
        assertEquals("ge25", queryParameters.get("age"));
        assertEquals("olo",  queryParameters.get("city:contains"));
        assertEquals("ne60", queryParameters.get("grade"));
        assertEquals("He",queryParameters.get("lastName"));
        assertEquals("gt1990-02-28",queryParameters.get("birthday"));
        assertEquals("UCL",queryParameters.get("school:not"));
        assertEquals("le190",queryParameters.get("height"));
        assertEquals("lt2020-05-01",queryParameters.get("arriveDate"));


    }

    @Test
    public void generateQueryParameterTest() {
        Map<String,String> parameterMap = new HashMap<>();
        parameterMap.put("name:exact","Dennis");
        parameterMap.put("age","24");
        parameterMap.put("school:exact","UCL");

        String actualString = FHIRConstructor.generateQueryParameter(parameterMap);
        assertEquals(3,actualString.split("&").length);
        assertTrue(actualString.contains("name:exact=Dennis"));
        assertTrue(actualString.contains("age=24"));
        assertTrue(actualString.contains("school:exact=UCL"));
    }
}
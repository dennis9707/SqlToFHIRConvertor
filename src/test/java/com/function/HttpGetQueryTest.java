package com.function;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class HttpGetQueryTest {
    @Test
    public void testToString() {
        String hostURL = "https://FhirServer.com";
        String tableName = "Patient";
        Map<String,String> valueMap = new HashMap<>();
        valueMap.put("LastName:exact","Wilson");
        valueMap.put("age","24");
        String expectedQuery = "{\n" +
                "  \"fhirQuery\": \"https://FhirServer.com/Patient?LastName:exact=Wilson&age=24\",\n" +
                "  \"requestType\": \"GET\"\n" +
                "}";
        HttpGetQuery actualQuery = new HttpGetQuery(hostURL,tableName,valueMap);
        assertEquals(expectedQuery,actualQuery.toString());

        Map<String,String> value1Map = new HashMap<>();

        expectedQuery = "{\n" +
                "  \"fhirQuery\": \"https://FhirServer.com/Patient\",\n" +
                "  \"requestType\": \"GET\"\n" +
                "}";
         actualQuery = new HttpGetQuery(hostURL,tableName,value1Map);
        assertEquals(expectedQuery,actualQuery.toString());
    }

}
package com.function;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class HttpPostQueryTest {

    @Test
    public void testToString() {
        String hostURL = "https://FhirServer.com";
        String tableName = "Patient";
        Map<String,String> valueMap = new HashMap<>();
        valueMap.put("LastName:exact","Wilson");
        valueMap.put("age","24");
        String expectedQuery =
        "{\n" +
                "  \"fhirQuery\": \"https://FhirServer.com/Patient\",\n" +
                "  \"body\": {\n" +
                "    \"LastName:exact\": \"Wilson\",\n" +
                "    \"age\": \"24\"\n" +
                "  },\n" +
                "  \"requestType\": \"POST\"\n" +
                "}";
        HttpPostQuery actualQuery = new HttpPostQuery(hostURL,tableName,valueMap);
        assertEquals(expectedQuery,actualQuery.toString());

    }
}
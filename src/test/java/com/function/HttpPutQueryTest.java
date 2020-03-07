package com.function;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class HttpPutQueryTest {
//        String sqlQuery = "UPDATE Person SET Address = 'Zhongshan 23', City = 'Nanjing' WHERE LastName = 'Wilson'";

    @Test
    public void testToString() {
        //        PUT path/Observation?identifier=http://my-lab-system|123

        String hostURL = "https://FhirServer.com";
        String tableName = "Patient";
        Map<String,String> parameterMap = new HashMap<>();
        parameterMap.put("LastName:exact","Wilson");

        Map<String,String> bodyMap = new HashMap<>();
        bodyMap.put("age","24");;

        String expectedQuery = "{\n" +
                "  \"fhirQuery\": \"https://FhirServer.com/Patient?LastName:exact=Wilson\",\n" +
                "  \"body\": {\n" +
                "    \"age\": \"24\"\n" +
                "  },\n" +
                "  \"requestType\": \"PUT\"\n" +
                "}";

            HttpPutQuery actualQuery = new HttpPutQuery(hostURL,tableName,parameterMap,bodyMap);
        assertEquals(expectedQuery,actualQuery.toString());
    }
}
package com.function;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class FHIRConstructor {

    public static Map<String,String> queryParameterConstructor(String[] whereClauses){
        HashMap<String, String> queryParameterMap = new HashMap<String, String>();
        for(String clause : whereClauses){
            String[] expression = clause.split("!=|>=|<=|>|<|=|LIKE");
            expression[0] = expression[0].trim();
            expression[1] = expression[1].trim();
            //Handle the exact match

            // If the query parameter is string, handle the cases that contains wildcards
            if(ConvertUtil.isString(expression[1]) && !ConvertUtil.isTimestamp(expression[1])) {

                //Delete the quotation marks
                expression[1] = expression[1].substring(1, expression[1].length() - 1);
                String defaultPattern = ".*%";
                String containPattern = "%.*%";

                if (Pattern.matches(containPattern, expression[1])) {
                    expression[0] = expression[0] + ":contains";
                    expression[1] = expression[1].replace('%', ' ').trim();
                } else if (Pattern.matches(defaultPattern, expression[1])) {
                    //Default pattern in FHIR query, do nothing.
                    expression[1] = expression[1].replace('%', ' ').trim();
                } else {
                    expression[0] = expression[0] + ":exact";
                }

                if(clause.contains("!=")){
                    expression[0] = expression[0] + ":not";
                }
            }

            // Handle the operator
            if(ConvertUtil.isNumber(expression[1])|| ConvertUtil.isTimestamp(expression[1])) {
                if (clause.contains("!=")) {
                    expression[1] = "ne" + expression[1];
                } else if (clause.contains(">=")) {
                    expression[1] = "ge" + expression[1];
                } else if (clause.contains(">")) {
                    expression[1] = "gt" + expression[1];
                } else if (clause.contains("<=")) {
                    expression[1] = "le" + expression[1];
                } else if (clause.contains("<")) {
                    expression[1] = "lt" + expression[1];
                }
            }
            queryParameterMap.put(expression[0], expression[1]);


        }

        return queryParameterMap;
    }

    public static String generateQueryParameter(Map<String,String> parameterMap){
        StringBuilder queryParameterBuilder = new StringBuilder();
        int count = 0;
        for(String key : parameterMap.keySet()){
            queryParameterBuilder.append(key + "=" + parameterMap.get(key));
            count++;
            if(count < parameterMap.keySet().size()){
                queryParameterBuilder.append("&");
            }
        }
        return queryParameterBuilder.toString();
    }


}

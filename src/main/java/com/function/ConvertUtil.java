package com.function;
import java.util.regex.Pattern;

public class ConvertUtil {
    public static boolean isString(String input){
        String stringPattern = "'.*'";
        return Pattern.matches(stringPattern,input);
    }

    public static boolean isTimestamp(String input){
        String timestampPattern = "'\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}'";
        String timestampPatternTwo = "'\\d{4}-\\d{2}-\\d{2}'";
        return Pattern.matches(timestampPattern,input) || Pattern.matches(timestampPatternTwo,input);
    }

    public static boolean isNumber(String input){
        String integerPattern =  "^-?\\d+$";
        String floatPattern = "^(-?\\\\d+)(\\.\\d+)?$";

        return Pattern.matches(integerPattern,input) || Pattern.matches(floatPattern,input);
    }

    public static String quoteWrapper(String input){
        return "\"" + input + "\"";
    }
}

package com.function;
import net.sf.jsqlparser.JSQLParserException;

import java.util.Map;


public class SqlToFHIR {
    public static void main(String[] args) throws JSQLParserException {
        String sqlQuery = "SELECT col1 AS a, col2 AS b, col3 AS c FROM table_1 WHERE col1 like '%abc%' AND col2 >= 20 AND col3 = 30";
        String insertQuery = "INSERT INTO table_name (id, name, active)\n" +
                        "VALUES ('example', 'bob', 'true');";
        String tableName = InsertSqlExtractor.tableNameExtractor(insertQuery);
        Map<String, String> values = InsertSqlExtractor.valuesExtractor(insertQuery);
        HttpPostQuery httpPostQuery = new HttpPostQuery("https","testFhirSever.com",tableName);
        httpPostQuery.setBody(values);
        System.out.println(httpPostQuery);
        String[] whereExpression = SelectSqlExtractor.parametersExtractor(sqlQuery);
        Map<String,String> queryParamterMap = FHIRConstructor.queryParameterConstructor(whereExpression);
        HttpGetQuery httpGetQuery = new HttpGetQuery("https","testFhirSever.com",tableName,queryParamterMap);
        System.out.println(tableName);
    }




}

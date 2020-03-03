package com.function;

import java.util.Arrays;
import java.util.Map;

import net.sf.jsqlparser.JSQLParserException;

public class SqlToFHIR {
    public static void main(String[] args) throws JSQLParserException {
       String sqlQuery = "SELECT * FROM Patient WHERE name IS NOT NULL";
//        String sqlQuery = "UPDATE Person SET Address = 'Zhongshan 23', City = 'Nanjing' WHERE LastName = 'Wilson'";
//        String sqlQuery = "DELETE FROM Person WHERE LastName = 'Wilson'";
        if(sqlQuery.toLowerCase().contains("insert")){
            String tableName = InsertSqlExtractor.tableNameExtractor(sqlQuery);
            Map<String, String> values = InsertSqlExtractor.valuesExtractor(sqlQuery);
            HttpPostQuery httpPostQuery = new HttpPostQuery("https","testFhirSever.com",tableName,values);
        }else if(sqlQuery.toLowerCase().contains("select")){
            String[] expression = SelectSqlExtractor.parametersExtractor(sqlQuery).toArray(new String[0]);
            System.out.println(Arrays.toString(expression));

            String tableName = SelectSqlExtractor.tableNameExtractor(sqlQuery);
            Map<String,String> queryParamterMap = FHIRConstructor.queryParameterConstructor(expression);
            HttpGetQuery httpGetQuery = new HttpGetQuery("https","testFhirSever.com",tableName,queryParamterMap);
            System.out.println(httpGetQuery);

        }else if(sqlQuery.toLowerCase().contains("update")){
            String tableName = UpdateSqlExtractor.tableNameExtractor(sqlQuery);
            Map<String,String> setValues = UpdateSqlExtractor.setValuesExtractor(sqlQuery);
            String[] whereExpression = UpdateSqlExtractor.whereExpressionExtractor(sqlQuery);
            Map<String,String> updateConditionParameters = FHIRConstructor.queryParameterConstructor(whereExpression);
            HttpPutQuery httpPutQuery = new HttpPutQuery("https","testFhirSever.com",tableName,updateConditionParameters,setValues);
            System.out.println(httpPutQuery);
        }else if(sqlQuery.toLowerCase().contains("delete")){
            String tableName = DeleteSqlExtractor.tableNameExtractor(sqlQuery);
            String[] whereExpression = DeleteSqlExtractor.whereExpressionExtractor(sqlQuery);
            Map<String,String> deleteConditionParameters = FHIRConstructor.queryParameterConstructor(whereExpression);
            HttpDeleteQuery httpDeleteQuery = new HttpDeleteQuery("https","testFhirSever.com",tableName,deleteConditionParameters);
            System.out.println(httpDeleteQuery);
        }


    }

}

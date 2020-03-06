package com.function;

import java.util.*;
import com.microsoft.azure.functions.annotation.*;

import net.sf.jsqlparser.JSQLParserException;

import com.microsoft.azure.functions.*;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {
    /**
     * This function listens at endpoint "/api/HttpTrigger-Java". Two ways to invoke
     * it using "curl" command in bash: 1. curl -d "HTTP Body" {your
     * host}/api/HttpTrigger-Java&code={your function key} 2. curl "{your
     * host}/api/HttpTrigger-Java?name=HTTP%20Query&code={your function key}"
     * Function Key is not needed when running locally, it is used to invoke
     * function deployed to Azure. More details:
     * https://aka.ms/functions_authorization_keys
     * 
     * @throws JSQLParserException
     */
    @FunctionName("HttpTrigger-Java")
    public HttpResponseMessage run(@HttpTrigger(name = "req", methods = { HttpMethod.GET,
            HttpMethod.POST }, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) throws JSQLParserException {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        String sqlQuery = request.getQueryParameters().get("sqlQuery");
        String fhirServer = request.getQueryParameters().get("fhirServer");
        // String name = request.getBody().orElse(query);
        String response = "123";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if(sqlQuery == null || fhirServer == null ){
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a sqlQuery and a fhirServer on the query string").build();
        }
        try{
            if(sqlQuery.toLowerCase().contains("insert")){
                String tableName = InsertSqlExtractor.tableNameExtractor(sqlQuery);
                Map<String, String> values = InsertSqlExtractor.valuesExtractor(sqlQuery);
                HttpPostQuery httpPostQuery = new HttpPostQuery(fhirServer,tableName,values);
                response = httpPostQuery.toString();
                System.out.println(httpPostQuery);
            }else if(sqlQuery.toLowerCase().contains("select")){
                String[] expression = SelectSqlExtractor.parametersExtractor(sqlQuery).toArray(new String[0]);
                String tableName = SelectSqlExtractor.tableNameExtractor(sqlQuery);
                Map<String,String> queryParamterMap = FHIRConstructor.queryParameterConstructor(expression);
                HttpGetQuery httpGetQuery = new HttpGetQuery(fhirServer,tableName,queryParamterMap);
                response = httpGetQuery.toString();
                System.out.println(httpGetQuery);
            }else if(sqlQuery.toLowerCase().contains("update")){
                String tableName = UpdateSqlExtractor.tableNameExtractor(sqlQuery);
                Map<String,String> setValues = UpdateSqlExtractor.setValuesExtractor(sqlQuery);
                String[] whereExpression = UpdateSqlExtractor.whereExpressionExtractor(sqlQuery);
                Map<String,String> updateConditionParameters = FHIRConstructor.queryParameterConstructor(whereExpression);
                HttpPutQuery httpPutQuery = new HttpPutQuery(fhirServer,tableName,updateConditionParameters,setValues);
                response = httpPutQuery.toString();
                System.out.println(httpPutQuery);
            }else if(sqlQuery.toLowerCase().contains("delete")){
                String tableName = DeleteSqlExtractor.tableNameExtractor(sqlQuery);
                String[] whereExpression = DeleteSqlExtractor.whereExpressionExtractor(sqlQuery);
                Map<String,String> deleteConditionParameters = FHIRConstructor.queryParameterConstructor(whereExpression);
                HttpDeleteQuery httpDeleteQuery = new HttpDeleteQuery(fhirServer,tableName,deleteConditionParameters);
                response = httpDeleteQuery.toString();
                System.out.println(httpDeleteQuery);
            }
            status = HttpStatus.OK;
        }catch(Exception e){
            response = new ErrorMessage("There is an syntax error in your SQL query", e.toString()).toString();
        }
        return request.createResponseBuilder(status).body(response).build();
    }
}

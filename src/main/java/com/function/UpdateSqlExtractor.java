package com.function;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.update.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateSqlExtractor {

    public static String tableNameExtractor(String sqlQuery) throws JSQLParserException {
        Update stmt = (Update) CCJSqlParserUtil.parse(sqlQuery);
        String tableName = stmt.getTable().toString();
        return tableName;
    }

    public static Map<String,String> setValuesExtractor(String sqlQuery) throws JSQLParserException{
        Map<String,String> setValuesMap = new HashMap<String, String>();
        Update stmt = (Update) CCJSqlParserUtil.parse(sqlQuery);
        List<Column> columns = stmt.getColumns();
        List<Expression> expressions = stmt.getExpressions();
        for (int i = 0; i < columns.size(); i++){
            String value = expressions.get(i).toString();
            if(ConvertUtil.isString(value)){
                value = value.substring(1,value.length() - 1);
            }
            setValuesMap.put(columns.get(i).toString(), value);
        }
        return setValuesMap;
    }

    public static String[] whereExpressionExtractor(String sqlQuery) throws JSQLParserException{
        Update stmt = (Update) CCJSqlParserUtil.parse(sqlQuery);
        String whereExpressionStr = stmt.getWhere().toString();
        String[] whereClauses = whereExpressionStr.split("AND|OR");
        return whereClauses;
    }

}

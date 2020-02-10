package com.function;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.delete.Delete;

public class DeleteSqlExtractor {
    public static String tableNameExtractor(String sqlQuery) throws JSQLParserException {
        Delete stmt = (Delete) CCJSqlParserUtil.parse(sqlQuery);
        return stmt.getTable().toString();
    }

    public static String[] whereExpressionExtractor(String sqlQuery) throws JSQLParserException {
        Delete stmt = (Delete) CCJSqlParserUtil.parse(sqlQuery);
        String whereExpressionStr = stmt.getWhere().toString();
        String[] whereClauses = whereExpressionStr.split("AND|OR");
        return whereClauses;
    }

}

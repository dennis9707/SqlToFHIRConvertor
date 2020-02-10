package com.function;

import java.util.List;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;

public class SelectSqlExtractor {

    public static String[] parametersExtractor(String sqlQuery) throws JSQLParserException {
        Select stmt = (Select) CCJSqlParserUtil.parse(sqlQuery);
        PlainSelect plainSelect = (PlainSelect)(stmt.getSelectBody());
        String whereExpression = plainSelect.getWhere().toString();
        String[] whereClauses = whereExpression.split("AND|OR");
        return whereClauses;
    }

    public static String tableNameExtractor(String sqlQuery) throws JSQLParserException {
        Select stmt = (Select) CCJSqlParserUtil.parse(sqlQuery);
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder.getTableList(stmt);
        return tableList.get(0);
    }



}

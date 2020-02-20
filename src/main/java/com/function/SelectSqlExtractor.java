package com.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.util.TablesNamesFinder;

public class SelectSqlExtractor {

    public static ArrayList<String> parametersExtractor(String sqlQuery) throws JSQLParserException {
        Select stmt = (Select) CCJSqlParserUtil.parse(sqlQuery);
        PlainSelect plainSelect = (PlainSelect)(stmt.getSelectBody());
        ArrayList<String> clauses = new ArrayList<String>();

//        check for where clauses

        Expression whereExpression = plainSelect.getWhere();
        if(whereExpression!=null) {

            String[] whereclauses = whereExpression.toString().split("AND|OR");
            clauses.addAll(Arrays.asList(whereclauses));
        }
        Limit limitexpression=plainSelect.getLimit();
        Top topexpression=plainSelect.getTop();

//        check for limit and top
        if(limitexpression!=null){
            clauses.add("_count="+limitexpression.getRowCount()+"");
        }
        else if(topexpression!=null){
            clauses.add("_count="+topexpression.getRowCount()+"");

        }
//        order by
        List<OrderByElement> OrderByExpressions=plainSelect.getOrderByElements();
        if(OrderByExpressions!=null){
            StringBuilder order = new StringBuilder("_sort=");


            for(OrderByElement ord:OrderByExpressions){
                order.append(",");
                order.append(ord.toString());
            }
            order=order.delete(6,7);
            clauses.add(order.toString());
        }


        return clauses;
    }

    public static String tableNameExtractor(String sqlQuery) throws JSQLParserException {
        Select stmt = (Select) CCJSqlParserUtil.parse(sqlQuery);
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder.getTableList(stmt);
        return tableList.get(0);
    }



}

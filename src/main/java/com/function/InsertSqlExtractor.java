package com.function;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsertSqlExtractor {
    public static String tableNameExtractor(String sqlQuery) throws JSQLParserException {
        Insert stmt = (Insert) CCJSqlParserUtil.parse(sqlQuery);
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder.getTableList(stmt);
        return tableList.get(0);
    }

    public static Map<String,String> valuesExtractor(String sqlQuery) throws  JSQLParserException{
        Map<String,String> valuesMap = new HashMap<String, String>();
        Insert stmt = (Insert) CCJSqlParserUtil.parse(sqlQuery);
        List<Column> columns = stmt.getColumns();
        String itemsList = stmt.getItemsList().toString();
        //Remove the brackets
        itemsList = itemsList.substring(1,itemsList.length() - 1);
        String[] valuesList = itemsList.split(",");
        valuesMap.put("resourceType",tableNameExtractor(sqlQuery));
        for(int i = 0; i < columns.size(); i++){
            valuesList[i] = valuesList[i].trim();
            if(ConvertUtil.isString(valuesList[i])){
                valuesList[i] = valuesList[i].substring(1,valuesList[i].length() - 1);
            }
            valuesMap.put(columns.get(i).toString().trim(),valuesList[i]);
        }
        return valuesMap;
    }
}

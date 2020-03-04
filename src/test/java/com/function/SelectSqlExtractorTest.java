package com.function;

import net.sf.jsqlparser.JSQLParserException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class SelectSqlExtractorTest {
    private ArrayList<String> querys;
    private static Logger logger = LogManager.getLogger();


    public SelectSqlExtractorTest() {
        logger.info("set up the test");
        this.querys = new ArrayList<String>();
        querys.add("SELECT * FROM Patient");
        querys.add("SELECT * FROM Patient WHERE given ='Jane'");
        querys.add("SELECT * FROM Patient WHERE given ='Jane' AND birthdate >= 1986-03-06");
        querys.add("SELECT * FROM Patient WHERE given ='Jane' ORDER BY Status");
        querys.add("SELECT * FROM Patient WHERE given='Jane' ORDER BY Status,Category");
        querys.add("SELECT * FROM Patient WHERE given='Jane' ORDER BY Status DESC, Category ASC");
        querys.add("SELECT * FROM Patient WHERE given='Jane' ORDER BY Status ASC, Category ASC");
        querys.add("SELECT TOP 3 * FROM Patient");
        querys.add("SELECT * FROM Patient LIMIT 10");
        querys.add("SELECT * FROM Patient WHERE name IS NULL");
        querys.add("SELECT * FROM Patient WHERE name IS NOT NULL");
    }



    @Test
    public void parametersExtractor() throws JSQLParserException {
        String[] expression = SelectSqlExtractor.parametersExtractor(querys.get(0)).toArray(new String[0]);
        String[] expected=new String[0];
        Assert.assertArrayEquals(expected, expression);

        expression = SelectSqlExtractor.parametersExtractor(querys.get(1)).toArray(new String[0]);
        expected= new String[]{"given='Jane'"};
        Assert.assertArrayEquals(expected, expression);

        expression = SelectSqlExtractor.parametersExtractor(querys.get(2)).toArray(new String[0]);
        expected= new String[]{"given='Jane'","birthdate>=1986-03-06"};
        Assert.assertArrayEquals(expected, expression);

        expression = SelectSqlExtractor.parametersExtractor(querys.get(3)).toArray(new String[0]);
        expected= new String[]{"given='Jane'","_sort=Status"};
        Assert.assertArrayEquals(expected, expression);

        expression = SelectSqlExtractor.parametersExtractor(querys.get(4)).toArray(new String[0]);
        expected= new String[]{"given='Jane'","_sort=Status,Category"};
        Assert.assertArrayEquals(expected, expression);

        expression = SelectSqlExtractor.parametersExtractor(querys.get(5)).toArray(new String[0]);
        expected= new String[]{"given='Jane'","_sort=-Status,Category"};
        Assert.assertArrayEquals(expected, expression);

        expression = SelectSqlExtractor.parametersExtractor(querys.get(6)).toArray(new String[0]);
        expected= new String[]{"given='Jane'","_sort=Status,Category"};
        Assert.assertArrayEquals(expected, expression);

        expression = SelectSqlExtractor.parametersExtractor(querys.get(7)).toArray(new String[0]);
        expected= new String[]{"_count=3"};
        Assert.assertArrayEquals(expected, expression);

        expression = SelectSqlExtractor.parametersExtractor(querys.get(8)).toArray(new String[0]);
        expected= new String[]{"_count=10"};
        Assert.assertArrayEquals(expected, expression);

        expression = SelectSqlExtractor.parametersExtractor(querys.get(9)).toArray(new String[0]);
        expected= new String[]{"name:missing=true"};
        Assert.assertArrayEquals(expected, expression);

        expression = SelectSqlExtractor.parametersExtractor(querys.get(10)).toArray(new String[0]);
        expected= new String[]{"name:missing=false"};
        Assert.assertArrayEquals(expected, expression);




    }

    @Test
    public void tableNameExtractor() throws JSQLParserException {
        String q1="SELECT * FROM Patient";
        String tableName = SelectSqlExtractor.tableNameExtractor(q1);
        String expected = "Patient";
        Assert.assertEquals(expected, tableName);

        String q2="SELECT * FROM Patient, Observation";
        String tableName1 = SelectSqlExtractor.tableNameExtractor(q1);
        String expected1 = "Patient";
        Assert.assertEquals(expected1, tableName1);

        String q3="SELECT * FROM Patient, Observation WHERE given='Jane' ORDER BY Status,Category";
        String tableName2 = SelectSqlExtractor.tableNameExtractor(q1);
        String expected2 = "Patient";
        Assert.assertEquals(expected2, tableName2);


    }
}
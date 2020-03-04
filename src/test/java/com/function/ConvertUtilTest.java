package com.function;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConvertUtilTest {

    @Test
    public void isStringTest() {
        String sqlString = "'this is a sql string'";
        String nonSqlString = "this is not a sql string";
        String nonSqlString2 = "'non sql string";
        String nonSqlString3 = "non sql string'";
        assertTrue(ConvertUtil.isString(sqlString));
        assertFalse(ConvertUtil.isString(nonSqlString));
        assertFalse(ConvertUtil.isString(nonSqlString2));
        assertFalse(ConvertUtil.isString(nonSqlString3));
    }

    @Test
    public void isTimestampTest() {
        String timeStamp = "'2020-03-04'";
        String timeStampDetail = "'2020-03-04 20:29:30'";
        String nonTimeStamp = "2020-03-04";
        String nonTimeStamp2 = "'Random String'";
        assertTrue(ConvertUtil.isTimestamp(timeStamp));
        assertTrue(ConvertUtil.isTimestamp(timeStampDetail));
        assertFalse(ConvertUtil.isTimestamp(nonTimeStamp));
        assertFalse(ConvertUtil.isTimestamp(nonTimeStamp2));

    }

    @Test
    public void isNumberTest() {
        String intNumber = "456";
        String floatNumber = "32.5";
        String nonNumber = "normal String 124";
        assertTrue(ConvertUtil.isNumber(intNumber));
        assertTrue(ConvertUtil.isNumber(floatNumber));
        assertFalse(ConvertUtil.isNumber(nonNumber));
    }


}
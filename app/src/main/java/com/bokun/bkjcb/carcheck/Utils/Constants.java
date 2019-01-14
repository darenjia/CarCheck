package com.bokun.bkjcb.carcheck.Utils;

/**
 * Created by BKJCB on 2017/3/16.
 * 系统常量
 */

public class Constants {

    public final static int NETWORK_WIFI = 1;
    public final static int NETWORK_MOBILE = 2;
    public final static boolean ISLOG = true;

    public final static String PLAN_DATA="[{\"name\":\"1\",\"describe\":\"22\"}]";

    public static class DataBaseCreateSQL{
        public final static String CREATE_PLAN="create table plan(" +
                "id int primary key," +
                "name char(50)," +
                "guid char(50)," +
                "describe char(50)" +
                ")";
        public final static String CREATE_RECORD="create table record(" +
                "id int primary key," +
                "guid char(50)," +
                "typeId char(10)," +
                "remark char(50)," +
                "imageUrl varchar(100)," +
                "result int(1)" +
                ")";
    }
}

package com.cc.o2m;

import com.cc.o2m.model.OracleTable;
import org.junit.jupiter.api.Test;

import javax.naming.event.ObjectChangeListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * dml
 * oracle to mysql
 */
public class Converter {

    @Test
    public void run(){

        String url = "jdbc:oracle:" + "thin:@47.100.81.41:1521:xe";
        String username = "rsp_dev";
        String password = "rsp5938";

        Connection con = null;
        // 创建预编译语句对象，一般都是用这个而不用Statement
        PreparedStatement pre = null;
        ResultSet result = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Trying to connect ... ！");
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Connected！");
            // step1 获取每张表的名称和注释
            pre = con.prepareStatement("select a.TABLE_NAME as TABLE_NAME, b.COMMENTS as COMMENTS " +
                    "from user_tables a " +
                    "left join user_tab_comments b on a.TABLE_NAME= b.TABLE_NAME " +
                    "order by table_name asc");
            result = pre.executeQuery();
            int allTableCount = result.getMetaData().getColumnCount();
            System.out.println("一共有" + allTableCount + "张表");
            List<OracleTable> allTableNames = new ArrayList<OracleTable>(allTableCount);
            int i = 1;
            while (result.next()) {
                OracleTable oracleTable = new OracleTable();
                String tableName = result.getString("TABLE_NAME");
                String comment = result.getString("COMMENTS");
                System.out.println("第" + i + "个表名称为： " + tableName + " 注释为：" + comment);
                oracleTable.name = tableName;
                oracleTable.comment = comment;
                allTableNames.add(oracleTable);
                i++;
            }

            //step2 找到每张表的信息进行DML拼凑和转换
            for(OracleTable oracleTable: allTableNames){
                pre = con.prepareStatement("select a.TABLE_NAME, a.COLUMN_NAME, a.DATA_TYPE, a.DATA_LENGTH, a.DATA_PRECISION, a.DATA_SCALE, a.NULLABLE, b.COMMENTS " +
                        "from user_tab_columns a " +
                        "inner join user_col_comments b on a.COLUMN_NAME = b.COLUMN_NAME and a.TABLE_NAME=b.TABLE_NAME " +
                        "where a.TABLE_NAME = ? " +
                        "order by a.COLUMN_ID asc");
                pre.setString(1, oracleTable.name);
                result = pre.executeQuery();

                while (result.next()) {
                    OracleTable oracleTable = new OracleTable();
                    String tableName = result.getString("TABLE_NAME");
                    String comment = result.getString("COMMENTS");
                    System.out.println("第" + i + "个表名称为： " + tableName + " 注释为：" + comment);
                    oracleTable.name = tableName;
                    oracleTable.comment = comment;
                    allTableNames.add(oracleTable);
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
                if (result != null) {
                    result.close();
                }
                if (pre != null) {
                    pre.close();
                }
                if (con != null) {
                    con.close();
                }
                System.out.println("Connection to database closed!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

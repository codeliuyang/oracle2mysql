package com.cc.o2m;

import com.cc.model.TableColumn;
import com.cc.model.Table;
import com.cc.util.FileUtils;

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
public class O2MConverter {

    public void run(String url, String username, String password){

        Connection con = null;
        // 创建预编译语句对象，一般都是用这个而不用Statement
        PreparedStatement pre = null;
        ResultSet result = null;
        ResultSet result2 = null;
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
            List<Table> allTable = new ArrayList<Table>(allTableCount);
            int i = 1;
            while (result.next()) {
                Table oracleTable = new Table();
                String tableName = result.getString("TABLE_NAME");
                String comment = result.getString("COMMENTS");
                // System.out.println("第" + i + "个表名称为： " + tableName + " 注释为：" + comment);
                oracleTable.name = tableName;
                oracleTable.comment = comment;
                allTable.add(oracleTable);
                i++;
            }
            pre.close();
            //step2 找到每张表的信息进行DML拼凑和转换
            for(Table oracleTable: allTable){
                // 查询表结构
                pre = con.prepareStatement("select a.TABLE_NAME, a.COLUMN_NAME, a.DATA_TYPE, a.DATA_LENGTH, a.DATA_PRECISION, a.DATA_SCALE, a.NULLABLE, b.COMMENTS " +
                        "from user_tab_columns a " +
                        "inner join user_col_comments b on a.COLUMN_NAME = b.COLUMN_NAME and a.TABLE_NAME=b.TABLE_NAME " +
                        "where a.TABLE_NAME = ? " +
                        "order by a.COLUMN_ID asc");
                pre.setString(1, oracleTable.name);
                result = pre.executeQuery();

                // 查询主键
                pre = pre = con.prepareStatement("select col.column_name " +
                        "from user_constraints con,user_cons_columns col " +
                        "where con.constraint_name=col.constraint_name and con.constraint_type='P' " +
                        "and col.table_name= ? ");
                pre.setString(1, oracleTable.name);
                result2 = pre.executeQuery();

                String primaryKey = "ID";
                while (result2.next()){
                    primaryKey = result2.getString("COLUMN_NAME");
                }
                // System.out.println("主键是 " + primaryKey);

                List<TableColumn> oracleColumns = new ArrayList<TableColumn>();

                while (result.next()) {
                    TableColumn oracleColumn = new TableColumn();
                    oracleColumn.name = result.getString("COLUMN_NAME");
                    oracleColumn.comment = result.getString("COMMENTS");
                    oracleColumn.dataLength = result.getString("DATA_LENGTH");
                    oracleColumn.dataPrecision = result.getString("DATA_PRECISION");
                    oracleColumn.dataScale = result.getString("DATA_SCALE");
                    oracleColumn.dataType = result.getString("DATA_TYPE");
                    oracleColumn.defaultValue = "";
                    oracleColumn.nullable = result.getString("NULLABLE");

                    if(oracleColumn.name.equalsIgnoreCase(primaryKey)){
                        oracleColumn.primaryKey = true;
                        oracleTable.primaryKeyColumnName = oracleColumn.name;
                        oracleTable.primaryKeyDataType = oracleColumn.dataType;
                    } else {
                        oracleColumn.primaryKey = false;
                    }
                    oracleColumns.add(oracleColumn);
                }
                oracleTable.columns = oracleColumns;
                pre.close();
            }

            //step3 进行转换
            StringBuilder sb = new StringBuilder();
            for(Table oracleTable: allTable){
                // System.out.println("converting ...... " + oracleTable.name);
                sb.append(oracleTable.toMySQLScript());
            }

            //step4 save to file
            String scriptComment = "-- Oracle 2 MySQL \n" +
                    "-- Database Info: \n" +
                    "--     " + url + "\n" +
                    "--     " + username + "\n\n\n";
            sb.insert(0, scriptComment);
            FileUtils.saveSqlScript(sb.toString());

            System.out.println("get table data finished!");
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

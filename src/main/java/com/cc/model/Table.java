package com.cc.model;

import java.util.List;

public class Table {

    public String name;

    public String comment;

    public List<TableColumn> columns;

    /**
     * 转成MySQL脚本
     *
     * @return
     */
    public String toMySQLScript(){
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + this.name + " ( \n");
        for(TableColumn tableColumn: this.columns){
            sb.append("  " + tableColumn + ",\n");
        }
        sb.append(") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='" + this.comment + "';");
        return sb.toString();
    }

}

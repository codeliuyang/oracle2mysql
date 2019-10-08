package com.cc.model;

import com.cc.util.DataTypeUtils;
import org.junit.platform.commons.util.StringUtils;

import java.util.List;

public class Table {

    public String name;

    public String comment;

    public List<TableColumn> columns;

    public String primaryKeyColumnName;

    public String primaryKeyDataType;

    /**
     * 转成MySQL脚本
     *
     * @return
     */
    public String toMySQLScript(){
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE IF EXISTS " + this.name + ";\n");
        sb.append("CREATE TABLE " + this.name + " ( \n");
        for(TableColumn tableColumn: this.columns){
            sb.append("  " + tableColumn.toMysqlScript() + ",\n");
        }
        if(StringUtils.isNotBlank(this.primaryKeyColumnName)){
            sb.append("  PRIMARY KEY (" + this.primaryKeyColumnName + ") USING BTREE,");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        if(DataTypeUtils.isNumber(primaryKeyDataType)){
            sb.append("\n) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4");
        } else {
            sb.append("\n) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");
        }
        if(StringUtils.isNotBlank(this.comment)){
            sb.append(" COMMENT='" + this.comment + "'");
        }
        sb.append(";\n\n");
        return sb.toString();
    }

}

package com.cc.o2m.model;


import com.cc.util.DataTypeUtils;
import org.junit.platform.commons.util.StringUtils;

public class TableColumn {

    public Boolean primaryKey;

    public String name;

    public String dataType;

    public String dataLength;

    public String dataPrecision;

    public String dataScale;

    public String nullable;

    public String defaultValue;

    public String comment;

    public volatile Integer divideChineseCharater = 3;

    public String toMysqlScript(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.name + " ");
        System.out.println("converting ... " + this.name);
        if("NUMBER".equalsIgnoreCase(this.dataType)){
            Integer precision = 18;
            if(StringUtils.isNotBlank(this.dataPrecision)){
                precision = Integer.valueOf(this.dataPrecision);
            }
            if(precision <= 3 ){
                sb.append("tinyint" + "(" + 3 + ") ");
            } else if(precision <= 5 ){
                sb.append("smallint" + "(" + 5 + ") ");
            } else if(precision <= 8){
                sb.append("mediumint" + "(" + 8 + ") ");
            } else if(precision <= 10) {
                sb.append("integer" + "(" + 10 + ") ");
            } else {
                sb.append("bigint" + "(" + 20 + ") ");
            }
        } else if (this.dataType.startsWith("TIMESTAMP")) {
            sb.append("timestamp" + " ");
        } else if (this.dataType.equalsIgnoreCase("DATE")) {
            sb.append("datetime" + " ");
        } else if (this.dataType.equalsIgnoreCase("DATETIME")) {
            sb.append("datetime" + " ");
        } else if (this.dataType.equalsIgnoreCase("CLOB")) {
            sb.append("longtext" + " ");
        } else if (this.dataType.contains("BLOB")) {
            sb.append("longblob" + " ");
        }
        else if (this.dataType.startsWith("VARCHAR") || this.dataType.startsWith("NVARCHAR")) {
            Integer length = 18;
            if(StringUtils.isNotBlank(this.dataLength)) {
                length = Integer.valueOf(this.dataLength);
            }
            sb.append("varchar" + "(" + length + ") ");
        } else if (this.dataType.contains("FLOAT")) {
            sb.append("float" + " ");
        } else if (this.dataType.contains("DOUBLE")) {
            sb.append("double" + " ");
        }
        else {
            sb.append(this.dataType + "(" + this.dataLength + ") ");
        }
        if ("N".equalsIgnoreCase(this.nullable)){
            sb.append("NOT NULL ");
        }
        if (StringUtils.isNotBlank(this.defaultValue)){
            sb.append("DEFAULT '" + defaultValue + "' ");
        } else if ("Y".equalsIgnoreCase(this.nullable) && !this.primaryKey){
            if (this.dataType.startsWith("TIMESTAMP")) {
                sb.append("DEFAULT NULL");
            } else {
                sb.append("DEFAULT NULL ");
            }
        }
        if (this.primaryKey && DataTypeUtils.isNumber(this.dataType)){
            sb.append("AUTO_INCREMENT ");
        }
        if (StringUtils.isNotBlank(this.comment)){
            sb.append("COMMENT '" + this.comment + "'");
        }
        return sb.toString();
    }

}

package com.cc.model;

public class TableColumn {

    public String name;

    public String dataType;

    public String dataLength;

    public String dataPrecision;

    public String dataScale;

    public String nullable;

    public String defaultValue;

    public String comment;

    public String toMysqlScript(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.name + " ");
        sb.append(this.dataType + "(" + this.dataPrecision + ") ");
        if("N".equalsIgnoreCase(this.nullable)){
            sb.append("NOT NULL ");
        }
        return "";
    }

}

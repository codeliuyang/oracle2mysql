package com.cc.util;

public class DataTypeUtils {

    public static boolean isNumber(String dataType){
        if("NUMBER".equalsIgnoreCase(dataType)){
            return true;
        }
        return false;
    }

}

package com.cc;

import com.cc.m2o.M2OConverter;
import com.cc.o2m.O2MConverter;

/**
 * main函数，运行main方法即可进行转换
 */
public class Application {

    /**
     * 设置好Oracle和MySQL互转开关
     */
    private static boolean OPEN_O2M = false;
    private static boolean OPEN_M2O = false;

    public static void main(String[] args){
        o2m();
        m2o();
    }

    /**
     * oracle 2 mysql
     */
    public static void o2m(){
        O2MConverter o2m = new O2MConverter();
        if(OPEN_O2M){
            o2m.run("jdbc:oracle:thin:@0.0.0.0:1521:xe",
                    "username",
                    "password");
        } else {
            System.out.println("如果要运行Oracle2MySQL，请设置OPEN_O2M=true");
        }
    }

    /**
     * mysql 2 oracle
     */
    public static void m2o(){
        M2OConverter m2o = new M2OConverter();
        if(OPEN_M2O){
            // TODO...
        } else {
            System.out.println("如果要运行MySQL2Oracle，请设置OPEN_M2O=true");
        }
    }

}

package com.cc;

import com.cc.m2o.M2OConverter;
import com.cc.o2m.O2MConverter;

public class Application {

    public static void main(String[] args){
        o2m();
        m2o();
    }

    /**
     * oracle 2 mysql
     */
    public static void o2m(){
        O2MConverter o2m = new O2MConverter();
        if(false){
            o2m.run("jdbc:oracle:thin:@0.0.0.0:1521:xe",
                    "username",
                    "password");
        } else {
            System.out.println("如果要运行Oracle2MySQL，请设置true");
        }
    }

    /**
     * mysql 2 oracle
     */
    public static void m2o(){
        M2OConverter m2o = new M2OConverter();
        if(false){

        } else {
            System.out.println("如果要运行MySQL2Oracle，请设置true");
        }
    }

}

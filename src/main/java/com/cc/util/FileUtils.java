package com.cc.util;

import java.io.PrintWriter;

public class FileUtils {

    public static void saveSqlScript(String content){
        PrintWriter writer = null;
        try{
            writer = new PrintWriter("converter.sql", "UTF-8");
            writer.println(content);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(writer != null){
                writer.close();
            }
        }
    }

}

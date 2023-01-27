package com.api.auto.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileUtils {
    //path to properties
    private static String CONFIG_PATH = "./configuration/configs.properties";
    private static String TOKEN_PATH = "./configuration/token.properties";

    //get any property by key
    public static String getProperties(String key){

        Properties properties = new Properties();
        String value = null;
        FileInputStream fileInputStream = null;

        //catch exception
        try {
            fileInputStream = new FileInputStream(CONFIG_PATH);
            properties.load(fileInputStream);
            value = properties.getProperty(key);
            return value;
        } catch (Exception ex) {
            System.out.println("Get problem when reading key  " + key);
            ex.printStackTrace();
        } finally {
            // close reading thread
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    //set property
    public static void setProperties(String key, String value){
        Properties properties= new Properties();
        FileOutputStream fileOutputStream = null;
        try {
            // FileOutputStream
            fileOutputStream = new FileOutputStream(CONFIG_PATH);
            //load properties and mapping value
            properties.setProperty(key, value);

            properties.store(fileOutputStream, "Set new value in properties");
            System.out.println("Set new value in file properties success.");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            //close writing thread
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //save TOKEN
    public static void saveToken(String token) {
        Properties properties= new Properties();
        // Khái báo properties, biến cần thiết
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(TOKEN_PATH);
            properties.setProperty("token", token);
            properties.store(fileOutputStream, "Set new value in token properties");
            System.out.println("Set new value in file token properties success.");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            //close writing thread
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //Get TOKEN
    public static String getToken(){
        Properties properties = new Properties();
        String value = null;
        FileInputStream fileInputStream = null;

        //catch exception
        try {
            fileInputStream = new FileInputStream(TOKEN_PATH);
            properties.load(fileInputStream);
            value = properties.getProperty("token");
            return value;
        } catch (Exception ex) {
            System.out.println("Get problem when reading key token ");
            ex.printStackTrace();
        } finally {
            // close reading thread
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

}





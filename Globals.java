package com.example.teamproject;

/**
 * @author Kieran Morgan
 * handles global variables for project
 */

public class Globals{
    public static int userCode;
    public void setCode(){
        int generatedCode = (int)(Math.random()*90000)+10000;
        userCode = generatedCode;
    }

    public int getCode(){
        return userCode;
    }
}

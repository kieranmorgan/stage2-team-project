package com.example.teamproject.data;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;


public class DBMethodsNew {
    private static PreparedStatement stmt;
    private static Connection conn;

    private static PreparedStatement stmt2;
    private static Connection conn2;

    private static PreparedStatement stmt3;
    private static Connection conn3;

    private static PreparedStatement stmt4;
    private static Connection conn4;

    private static PreparedStatement stmt5;
    private static Connection conn5;

    private static PreparedStatement stmt6;
    private static Connection conn6;

    private static PreparedStatement stmt7;
    private static Connection conn7;

    private static PreparedStatement stmt8;
    private static Connection conn8;

    private static PreparedStatement stmt9;
    private static Connection conn9;

    private static PreparedStatement stmt10;
    private static Connection conn10;





    public static int createAdminID()throws SQLException,ClassNotFoundException{
        boolean newNumber = false;
        // MySql database connection info
        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String USER = "admin";
        String PASS = "CSC2033Team27";//Team27MH
        String DB_URL = "jdbc:mysql://stubankplc.cqk3im4dcwwo.eu-west-2.rds.amazonaws.com";
        Class.forName(JDBC_DRIVER);
        conn7 = DriverManager.getConnection(DB_URL, USER, PASS);
        String query = "SELECT AdminID FROM StuBankPLC.Admins";
        stmt7 = conn7.prepareStatement(query);
        ResultSet rs = stmt7.executeQuery();
        ArrayList<Integer> existingAccounts = new ArrayList<Integer>();
        while(rs.next()){
            existingAccounts.add(rs.getInt("AdminID"));
        }
        while(true){
            int generatedNumber =(int) (Math.random()*(99999999-10000001)+10000001);
            if(!existingAccounts.contains(generatedNumber)){
                System.out.println(generatedNumber);
                return generatedNumber;
            }

        }
    }





    public static int createCustID()throws SQLException,ClassNotFoundException{
        boolean newNumber = false;
        // MySql database connection info
        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String USER = "admin";
        String PASS = "CSC2033Team27";//Team27MH
        String DB_URL = "jdbc:mysql://stubankplc.cqk3im4dcwwo.eu-west-2.rds.amazonaws.com";
        Class.forName(JDBC_DRIVER);
        conn6 = DriverManager.getConnection(DB_URL, USER, PASS);
        String query = "SELECT CustomerID FROM StuBankPLC.Users";
        stmt6 = conn6.prepareStatement(query);
        ResultSet rs = stmt6.executeQuery();
        ArrayList<Integer> existingAccounts = new ArrayList<Integer>();
        while(rs.next()){
            existingAccounts.add(rs.getInt("CustomerID"));
        }
        while(true){
            int generatedNumber =(int) (Math.random()*(99999999-10000001)+10000001);
            if(!existingAccounts.contains(generatedNumber)){
                System.out.println(generatedNumber);
                return generatedNumber;
            }

        }
    }

    public static String createHash(String password){
        //creates a hashed version of a String
        try{
            //uses the SHA-512 hash
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(password.getBytes());
            byte byteData[] = md.digest();
            StringBuilder sb = new StringBuilder();
            //hashes each character and inserts into hashed stringbuilder
            for (int i=0; i<byteData.length; i++){
                sb.append(Integer.toString((byteData[i] & 0xff)+ 0x100,16).substring(1));
            }
            //returns hashed data as a string
            return sb.toString();
        }
        catch (NoSuchAlgorithmException ex){
            Logger.getLogger("SHA-512").log(Level.SEVERE,null,ex);
            return null;
        }
    }



    public static void createUser(String firstName, String lastName, String email,int phoneNumber, String enterdPassword) throws SQLException, ClassNotFoundException{
        try {

            // MySql database connection info
            String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
            String USER = "admin";
            String PASS = "CSC2033Team27";//Team27MH
            String DB_URL = "jdbc:mysql://stubankplc.cqk3im4dcwwo.eu-west-2.rds.amazonaws.com";
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);


            int custID = createCustID();
            String hashedPassword = createHash(enterdPassword);




            String query = "INSERT INTO StuBankPLC.Users (FirstName, LastName, PhoneNumber, Email, Password,CustomerID)" +
                    "VALUES (?,?,?,?,?,?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setInt(3, phoneNumber);
            stmt.setString(4, email);
            stmt.setString(5, hashedPassword);
            stmt.setInt(6,custID);
            stmt.execute();
            conn.close();
            System.out.println("account created");


        }
        catch (Exception se){
            se.printStackTrace();
            String error = "display this error to the user on the app";
        }

    }
    public static ArrayList<Object> getUserDetails(int accountNumber)throws SQLException, ClassNotFoundException{

        // MySql database connection info
        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String USER = "admin";
        String PASS = "CSC2033Team27";//Team27MH
        String DB_URL = "jdbc:mysql://stubankplc.cqk3im4dcwwo.eu-west-2.rds.amazonaws.com";
        Class.forName(JDBC_DRIVER);
        conn2 = DriverManager.getConnection(DB_URL, USER, PASS);


        ArrayList<Object> userDetails = new ArrayList<Object>();
        String query = "SELECT * FROM StuBankPLC.Users WHERE CustomerID=?";
        stmt2 = conn2.prepareStatement(query);
        stmt2.setInt(1,accountNumber);
        ResultSet rs = stmt2.executeQuery();
        while(rs.next()){
            userDetails.add(rs.getInt("CustomerID"));
            userDetails.add(rs.getString("FirstName"));
            userDetails.add(rs.getString("LastName"));
            userDetails.add(rs.getInt("PhoneNumber"));
            userDetails.add(rs.getString("Email"));
        }

        return userDetails;
    }

    public static ArrayList<Object> getAdminDetails(int adminID)throws SQLException, ClassNotFoundException{

        // MySql database connection info
        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String USER = "admin";
        String PASS = "CSC2033Team27";//Team27MH
        String DB_URL = "jdbc:mysql://stubankplc.cqk3im4dcwwo.eu-west-2.rds.amazonaws.com";
        Class.forName(JDBC_DRIVER);
        conn8 = DriverManager.getConnection(DB_URL, USER, PASS);


        ArrayList<Object> userDetails = new ArrayList<Object>();
        String query = "SELECT * FROM StuBankPLC.Admins WHERE AdminID=?";
        stmt8 = conn8.prepareStatement(query);
        stmt8.setInt(1,adminID);
        ResultSet rs = stmt8.executeQuery();
        while(rs.next()){
            userDetails.add(rs.getInt("AdminID"));
            userDetails.add(rs.getString("FirstName"));
            userDetails.add(rs.getString("LastName"));
            userDetails.add(rs.getInt("PhoneNumber"));
            userDetails.add(rs.getString("Email"));
            userDetails.add(rs.getInt("Privelige"));

        }

        return userDetails;
    }


    public static boolean userLogin(int accountNumber, String enteredPassword)throws SQLException, ClassNotFoundException{
        boolean loginSuccess = false;
        // MySql database connection info
        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String USER = "admin";
        String PASS = "CSC2033Team27";//Team27MH
        String DB_URL = "jdbc:mysql://stubankplc.cqk3im4dcwwo.eu-west-2.rds.amazonaws.com";
        Class.forName(JDBC_DRIVER);
        conn3 = DriverManager.getConnection(DB_URL, USER, PASS);

        String query = "SELECT * FROM StuBankPLC.Users WHERE CustomerID=?";
        stmt3 = conn3.prepareStatement(query);
        stmt3.setInt(1,accountNumber);
        String hashedPassword = createHash(enteredPassword);
        ResultSet rs = stmt3.executeQuery();
        while(rs.next()){
            if(rs.getString("Password").equals(hashedPassword)){
                loginSuccess = true;
                return loginSuccess;
            }
        }
        return loginSuccess;
    }


    public static void createAdmin(String firstName, String lastName, int phoneNumber, String email, String enteredPassword, int privelige){
        try {

            // MySql database connection info
            String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
            String USER = "admin";
            String PASS = "CSC2033Team27";//Team27MH
            String DB_URL = "jdbc:mysql://stubankplc.cqk3im4dcwwo.eu-west-2.rds.amazonaws.com";
            Class.forName(JDBC_DRIVER);
            conn4 = DriverManager.getConnection(DB_URL, USER, PASS);


            int adminID = createAdminID();
            String hashedPassword = createHash(enteredPassword);




            String query = "INSERT INTO StuBankPLC.Admins (FirstName, LastName, PhoneNumber, Email, Password, AdminID, Privelige)" +
                    "VALUES (?,?,?,?,?,?,?)";
            stmt4 = conn4.prepareStatement(query);
            stmt4.setString(1, firstName);
            stmt4.setString(2, lastName);
            stmt4.setInt(3, phoneNumber);
            stmt4.setString(4, email);
            stmt4.setString(5, hashedPassword);
            stmt4.setInt(6, adminID);
            stmt4.setInt(7, privelige);
            stmt4.execute();
            conn4.close();


        }
        catch (Exception se){
            se.printStackTrace();
            String error = "display this error to the user on the app";
        }
    }

    public static boolean adminLogin(int adminID, String enteredPassword)throws SQLException,ClassNotFoundException{
        boolean loginSuccess = false;
        // MySql database connection info
        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String USER = "admin";
        String PASS = "CSC2033Team27";//Team27MH
        String DB_URL = "jdbc:mysql://stubankplc.cqk3im4dcwwo.eu-west-2.rds.amazonaws.com";
        Class.forName(JDBC_DRIVER);
        conn5 = DriverManager.getConnection(DB_URL, USER, PASS);

        String query = "SELECT * FROM StuBankPLC.Admins WHERE AdminID=?";
        stmt5 = conn5.prepareStatement(query);
        stmt5.setInt(1,adminID);
        String hashedPassword = createHash(enteredPassword);
        ResultSet rs = stmt5.executeQuery();
        while(rs.next()){
            if(rs.getString("Password").equals(hashedPassword)){
                loginSuccess = true;
                return loginSuccess;
            }
        }
        return loginSuccess;
    }

    public static void createPot(String cardNumber, String potName, String potDescription, Float amount)throws SQLException,ClassNotFoundException{
        // MySql database connection info
        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String USER = "admin";
        String PASS = "CSC2033Team27";//Team27MH
        String DB_URL = "jdbc:mysql://stubankplc.cqk3im4dcwwo.eu-west-2.rds.amazonaws.com";
        Class.forName(JDBC_DRIVER);
        conn9 = DriverManager.getConnection(DB_URL, USER, PASS);

        String query = "INSERT INTO StuBankPLC.Pots (PotName,DigitalCardNumber,PotDescription,Amount)"+"VALUES (?,?,?,?)";
        stmt9 = conn9.prepareStatement(query);
        stmt9.setString(1,potName);
        stmt9.setString(2,cardNumber);
        stmt9.setString(3,potDescription);
        stmt9.setFloat(4,amount);
        stmt9.execute();
        conn9.close();

    }

    public static ArrayList<Object> getPotDetails(String digitalCardNumber,String potName)throws SQLException, ClassNotFoundException{
        // MySql database connection info
        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String USER = "admin";
        String PASS = "CSC2033Team27";//Team27MH
        String DB_URL = "jdbc:mysql://stubankplc.cqk3im4dcwwo.eu-west-2.rds.amazonaws.com";
        Class.forName(JDBC_DRIVER);
        conn10 = DriverManager.getConnection(DB_URL, USER, PASS);

        ArrayList<Object> potDetails = new ArrayList<Object>();
        String query = "SELECT * FROM StuBankPCL.Pots WHERE DigitalCardNumber=? AND PotName =?";
        stmt10 = conn10.prepareStatement(query);
        stmt10.setString(1,digitalCardNumber);
        stmt10.setString(2,potName);
        ResultSet rs = stmt10.executeQuery();
        while(rs.next()){
            potDetails.add(rs.getString("PotName"));
            potDetails.add(rs.getString("DigitalCardNumber"));
            potDetails.add(rs.getString("PotDescription"));
            potDetails.add(rs.getFloat("Amount"));
        }
        return potDetails;
    }


}

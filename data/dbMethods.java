package com.example.teamproject.data;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;


public class dbMethods {
    Connection conn = null;
    PreparedStatement stmt;
    // MySql database connection info
    String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    String USER = "admin";
    String PASS = "CSC2033Team27";
    //AWS endpoint
    String DB_URL = "jdbc:mysql://stubankplc.cqk3im4dcwwo.eu-west-2.rds.amazonaws.com";

    public int createAdminID(){
        try {
            boolean newNumber = false;
            // MySql database connection info
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String query = "SELECT AdminID FROM StuBankPLC.Admins";
            stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Integer> existingAccounts = new ArrayList<Integer>();
            while (rs.next()) {
                existingAccounts.add(rs.getInt("AdminID"));
            }
            while (true) {
                int generatedNumber = (int) (Math.random() * (99999999 - 10000001) + 10000001);
                if (!existingAccounts.contains(generatedNumber)) {
                    System.out.println(generatedNumber);
                    return generatedNumber;
                }

            }
        }catch (ClassNotFoundException | SQLException e){
            return 0;
        }finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                //EMPTY TO BE FILLED
            }
        }
    }




    public int createCustID(){
        try {
            boolean newNumber = false;
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String query = "SELECT CustomerID FROM StuBankPLC.Users";
            stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Integer> existingAccounts = new ArrayList<Integer>();
            while (rs.next()) {
                existingAccounts.add(rs.getInt("CustomerID"));
            }
            while (true) {
                int generatedNumber = (int) (Math.random() * (99999999 - 10000001) + 10000001);
                if (!existingAccounts.contains(generatedNumber)) {
                    System.out.println(generatedNumber);
                    return generatedNumber;
                }

            }
        } catch (ClassNotFoundException | SQLException e){
            return 0;
        }finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                //EMPTY TO BE FILLED
            }
        }
    }

    public String createHash(String password){
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
        }finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                //EMPTY TO BE FILLED
            }
        }
    }



    public void createUser(String firstName, String lastName, String email,int phoneNumber, String enterdPassword){
        try {
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


        }
        catch (Exception se){
            se.printStackTrace();
            String error = "display this error to the user on the app";
        }finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                //EMPTY TO BE FILLED
            }
        }

    }
    public ArrayList<Object> getUserDetails(int accountNumber){
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);


            ArrayList<Object> userDetails = new ArrayList<Object>();
            String query = "SELECT * FROM StuBankPLC.Users WHERE CustomerID=?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                userDetails.add(rs.getInt("CustomerID"));
                userDetails.add(rs.getString("FirstName"));
                userDetails.add(rs.getString("LastName"));
                userDetails.add(rs.getInt("PhoneNumber"));
                userDetails.add(rs.getString("Email"));
            }

            return userDetails;
        }catch (ClassNotFoundException | SQLException e){
            return null;
        }finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                //EMPTY TO BE FILLED
            }
        }
    }

    public ArrayList<Object> getAdminDetails(int adminID){
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);


            ArrayList<Object> userDetails = new ArrayList<Object>();
            String query = "SELECT * FROM StuBankPLC.Admins WHERE AdminID=?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, adminID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                userDetails.add(rs.getInt("AdminID"));
                userDetails.add(rs.getString("FirstName"));
                userDetails.add(rs.getString("LastName"));
                userDetails.add(rs.getInt("PhoneNumber"));
                userDetails.add(rs.getString("Email"));
                userDetails.add(rs.getInt("Privelige"));

            }

            return userDetails;
        }catch (ClassNotFoundException | SQLException e){
            return null;
        }finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                //EMPTY TO BE FILLED
            }
        }
    }


    public  boolean userLogin(int accountNumber, String enteredPassword){
        try {
            boolean loginSuccess = false;
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String query = "SELECT * FROM StuBankPLC.Users WHERE CustomerID=?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, accountNumber);
            String hashedPassword = createHash(enteredPassword);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("Password").equals(hashedPassword)) {
                    loginSuccess = true;
                    return loginSuccess;
                }
            }
            return loginSuccess;
        }catch (ClassNotFoundException | SQLException e){
            return false;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                //EMPTY TO BE FILLED
            }
        }
    }


    public  void createAdmin(String firstName, String lastName, int phoneNumber, String email, String enteredPassword, int privelige){
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);


            int adminID = createAdminID();
            String hashedPassword = createHash(enteredPassword);




            String query = "INSERT INTO StuBankPLC.Admins (FirstName, LastName, PhoneNumber, Email, Password, AdminID, Privelige)" +
                    "VALUES (?,?,?,?,?,?,?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setInt(3, phoneNumber);
            stmt.setString(4, email);
            stmt.setString(5, hashedPassword);
            stmt.setInt(6, adminID);
            stmt.setInt(7, privelige);
            stmt.execute();
            conn.close();


        }
        catch (Exception se){
            se.printStackTrace();
            String error = "display this error to the user on the app";
        }finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                //EMPTY TO BE FILLED
            }
        }
    }

    public boolean adminLogin(int adminID, String enteredPassword){
        try {
            boolean loginSuccess = false;
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String query = "SELECT * FROM StuBankPLC.Admins WHERE AdminID=?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, adminID);
            String hashedPassword = createHash(enteredPassword);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("Password").equals(hashedPassword)) {
                    loginSuccess = true;
                    return loginSuccess;
                }
            }
            return loginSuccess;
        }catch (ClassNotFoundException | SQLException e){
            return false;
        }finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                //EMPTY TO BE FILLED
            }
        }
    }

    public void createPot(String cardNumber, String potName, String potDescription, Float amount){
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String query = "INSERT INTO StuBankPLC.Pots (PotName,DigitalCardNumber,PotDescription,Amount)" + "VALUES (?,?,?,?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, potName);
            stmt.setString(2, cardNumber);
            stmt.setString(3, potDescription);
            stmt.setFloat(4, amount);
            stmt.execute();
        }catch (ClassNotFoundException | SQLException e){

        }finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                //EMPTY TO BE FILLED
            }
        }

    }

    public ArrayList<Object> getPotDetails(String digitalCardNumber,String potName){
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            ArrayList<Object> potDetails = new ArrayList<Object>();
            String query = "SELECT * FROM StuBankPCL.Pots WHERE DigitalCardNumber=? AND PotName =?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, digitalCardNumber);
            stmt.setString(2, potName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                potDetails.add(rs.getString("PotName"));
                potDetails.add(rs.getString("DigitalCardNumber"));
                potDetails.add(rs.getString("PotDescription"));
                potDetails.add(rs.getFloat("Amount"));
            }
            return potDetails;
        }catch (ClassNotFoundException | SQLException e){
            return null;
        }finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                //EMPTY TO BE FILLED
            }
        }

    }

    public void addAccount(int CustomerID, String DigitalCardNumber, float Balance, String AccountType){
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.prepareStatement("INSERT INTO StuBankPLC.Accounts (CustomerID, DigitalCardNumber, Balance, AccountType) VALUES (?, ?, ?, ?)");
            stmt.setInt(1, CustomerID);
            stmt.setString(2, DigitalCardNumber);
            stmt.setFloat(3, Balance);
            stmt.setString(4, AccountType);
            // set values into SQL query statement
            stmt.execute();
        } catch (ClassNotFoundException | SQLException e) {
            //EMPTY TO BE FILLED
        } finally {
            try {
                if (stmt != null){
                    stmt.close();
                }
                if (conn != null){
                    conn.close();
                }
            }catch (SQLException e){
                //EMPTY TO BE FILLED
            }
        }
    }

    public void deleteAccount(int CustomerID){
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.prepareStatement("DELETE FROM StuBankPLC.Accounts WHERE CustomerID = ?");
            stmt.setInt(1, CustomerID);
            stmt.execute();
        } catch (ClassNotFoundException | SQLException e) {
            //EMPTY TO BE FILLED
        } finally {
            try {
                if (stmt != null){
                    stmt.close();
                }
                if (conn != null){
                    conn.close();
                }
            }catch (SQLException e){
                //EMPTY TO BE FILLED
            }
        }
    }

    public ResultSet getAccountDetails(String Field, String DigitalCardNumber){
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.prepareStatement("SELECT " + Field + " FROM StuBankPLC.Accounts WHERE DigitalCardNumber = ?");
            stmt.setString(1, DigitalCardNumber);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs;
        } catch (ClassNotFoundException | SQLException e) {
            //EMPTY TO BE FILLED
        } finally {
            try {
                if (stmt != null){
                    stmt.close();
                }
                if (conn != null){
                    conn.close();
                }
            }catch (SQLException e){
                //EMPTY TO BE FILLED
            }
        }
        return null;
    }

    public void updateAccountBalance(float Balance, String DigitalCardNumber){
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.prepareStatement("UPDATE StuBankPLC.Accounts SET Balance = ? WHERE DigitalCardNumber = ?");
            stmt.setFloat(1,Balance);
            stmt.setString(2, DigitalCardNumber);
            stmt.execute();
        } catch (ClassNotFoundException | SQLException e) {
            //EMPTY TO BE FILLED
        } finally {
            try {
                if (stmt != null){
                    stmt.close();
                }
                if (conn != null){
                    conn.close();
                }
            }catch (SQLException e){
                //EMPTY TO BE FILLED
            }
        }
    }

    public void addTransaction(String DigitalCardNumber, String Type, String Time, String Date,
                               String Transactor, float Exchange){
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            ResultSet rs = getAccountDetails("Balance", DigitalCardNumber);
            float Balance = rs.getFloat("Balance") + Exchange;
            updateAccountBalance(Balance, DigitalCardNumber);
            stmt = conn.prepareStatement("INSERT INTO StuBankPLC.Transactions (DigitalCardNumber, Balance, Type, Time," +
                    " Date, Transactor) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, DigitalCardNumber);
            stmt.setFloat(2, Balance);
            stmt.setString(3, Type);
            stmt.setTime(4, java.sql.Time.valueOf(Time));
            stmt.setDate(5, java.sql.Date.valueOf(Date));
            stmt.setString(6, Transactor);
            // set values into SQL query statement
            stmt.execute();
        } catch (ClassNotFoundException | SQLException e) {
            //EMPTY TO BE FILLED
        } finally {
            try {
                if (stmt != null){
                    stmt.close();
                }
                if (conn != null){
                    conn.close();
                }
            }catch (SQLException e){
                //EMPTY TO BE FILLED
            }
        }
    }

    public ResultSet getAllTransactionsOfAccount(String DigitalCardNumber){
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.prepareStatement("SELECT * FROM StuBankPLC.Transactions WHERE DigitalCardNumber = ?");
            stmt.setString(1, DigitalCardNumber);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs;
        } catch (ClassNotFoundException | SQLException e) {
            //EMPTY TO BE FILLED
        } finally {
            try {
                if (stmt != null){
                    stmt.close();
                }
                if (conn != null){
                    conn.close();
                }
            }catch (SQLException e){
                //EMPTY TO BE FILLED
            }
        }
        return null;
    }

    public ResultSet getAllTransactions(){
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.prepareStatement("SELECT * FROM StuBankPLC.Transactions");
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs;
        } catch (ClassNotFoundException | SQLException e) {
            //EMPTY TO BE FILLED
        } finally {
            try {
                if (stmt != null){
                    stmt.close();
                }
                if (conn != null){
                    conn.close();
                }
            }catch (SQLException e){
                //EMPTY TO BE FILLED
            }
        }
        return null;
    }

    public void deleteTransaction(int TransactionID){
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.prepareStatement("DELETE FROM StuBankPLC.Transactions WHERE TransactionID = ?");
            stmt.setInt(1, TransactionID);
            stmt.execute();
        } catch (ClassNotFoundException | SQLException e) {
            //EMPTY TO BE FILLED
        } finally {
            try {
                if (stmt != null){
                    stmt.close();
                }
                if (conn != null){
                    conn.close();
                }
            }catch (SQLException e){
                //EMPTY TO BE FILLED
            }
        }
    }

    public void makeTransaction(String DigitalCardNumber, String Type, String Time, String Date,
                                String Transactor, float Exchange, String Recipient){
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            addTransaction(DigitalCardNumber, Type, Time, Date, Transactor, -Exchange);
            addTransaction(Recipient, Type, Time, Date, Transactor, Exchange);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null){
                    stmt.close();
                }
                if (conn != null){
                    conn.close();
                }
            }catch (SQLException e){
                //EMPTY TO BE FILLED
            }
        }

    }
}

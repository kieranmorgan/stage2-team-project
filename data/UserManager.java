package com.example.teamproject.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.teamproject.data.model.LoggedInUser;
import com.example.teamproject.ui.login.LoginActivity;

/**
 * @author Evan Hosking
 * Singleton class to handles user session, ensures only one user logged in
 */

public class UserManager {

    private static final String User = "User";
    private static final String CustomerID = "CustomerID";
    private static final String FirstName = "FirstName";
    private static final String Email = "Email";
    private static UserManager Instance;
    private static Context context;

    private UserManager(Context c) {
        context = c;
    }

    /**
     * Constructor for user manager, only one instance can exist at any one time
     * @param context
     * @return
     */
    public static synchronized UserManager getInstance(Context context) {
        if (Instance == null) {
            Instance = new UserManager(context);
        }
        return Instance;
    }

    /**
     * takes the user information and sets it as a string
     * @param user
     */
    public void userLogin(LoggedInUser user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(User, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CustomerID", user.getUserId());
        editor.putString("FirstName", user.getFirstName());
        editor.putString("Email", user.getEmailAddress());
        editor.apply();
    }

    /**
     * this method will checker whether user is already logged in or not
     * @return
     */
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(User, Context.MODE_PRIVATE);
        return sharedPreferences.getString(CustomerID, null) != null;
    }


    /**
     * This method will give the logged in user
     * @return
     */
    public LoggedInUser getUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(User, Context.MODE_PRIVATE);
        return new LoggedInUser(
                sharedPreferences.getString(CustomerID, null),
                sharedPreferences.getString(FirstName, null),
                sharedPreferences.getString(Email, null)
        );
    }

    /**
     * logs the user out of their account
     */
    public void logout() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(User, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        context.startActivity(new Intent(context, LoginActivity.class));
    }
}

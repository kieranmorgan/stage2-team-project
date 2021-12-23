package com.example.teamproject.ui.login;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.teamproject.R;
import com.example.teamproject.data.REST.POST;
import com.example.teamproject.data.REST.PostListener;
import com.example.teamproject.data.UserManager;
import com.example.teamproject.data.model.LoggedInUser;
import com.example.teamproject.data.SendingEmail;

/**
 * @author Evan Hosking, Kieran Morgan, Matthew Holdsworth
 * Handles login authentication using login credentials and retrieves user information.
 */
public class LoginDataSource {
    private Context context;
    private POST post;

    public LoginDataSource(Context context) {
        this.context = context;
    }

    protected void login(String username, String password) {
        try {
            post = new POST(context);
            post.authenticate(username, password, new PostListener() {
                @Override
                public void onSuccess(boolean result, String CustomerID, String FirstName, String Email) {
                    if (result) {
                        // creates logged in user
                        LoggedInUser loggedInUser = new LoggedInUser(CustomerID, FirstName, Email);
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    SendingEmail.send2faEmail(Email);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        thread.start();
                        // logs in user
                        UserManager.getInstance(context).userLogin(loggedInUser);
                        Intent intent = new Intent(context, Confirm2FAActivity.class);
                        context.startActivity(intent);

                    } else {
                        Toast.makeText(context, R.string.login_failed, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        catch (Exception e) {
            Toast.makeText(context, "Internal error logging in", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * seperate login for admins
     * @param username
     * @param password
     */
    protected void loginAdmin(String username, String password) {
        try {
            //calls the authenticate admin method in post
            post = new POST(context);
            post.authenticateAdmin(username, password, new PostListener() {
                @Override
                public void onSuccess(boolean result, String CustomerID, String FirstName, String Email) {
                    if (result) {
                        //appends an A to the id, so the project can recognise its an admin account
                        CustomerID = 'A' + CustomerID;
                        //sets logged in user as the received credentials
                        LoggedInUser loggedInUser = new LoggedInUser(CustomerID, FirstName, Email);
                        //sends an email to the accounts email address to check 2FA
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    SendingEmail.send2faEmail(Email);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        thread.start();
                        UserManager.getInstance(context).userLogin(loggedInUser);
                        //calls the confirm2fa activity
                        Intent intent = new Intent(context, Confirm2FAActivity.class);
                        context.startActivity(intent);

                    } else {
                        Toast.makeText(context, R.string.login_failed, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        catch (Exception e) {
            Toast.makeText(context, "Internal error logging in", Toast.LENGTH_SHORT).show();
        }

    }
}
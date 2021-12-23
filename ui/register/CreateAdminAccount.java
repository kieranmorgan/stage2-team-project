package com.example.teamproject.ui.register;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamproject.R;
import com.example.teamproject.data.REST.POST;
import com.example.teamproject.data.REST.PostListener;
import com.example.teamproject.data.UserManager;
import com.example.teamproject.data.model.LoggedInUser;
import com.example.teamproject.ui.login.AdminLoginActivity;
import com.example.teamproject.ui.useraccount.activity.AdminAccountActivity;
import com.google.android.material.textfield.TextInputLayout;

/**
 * @author Matthew Holdsworth
 */

public class CreateAdminAccount extends AppCompatActivity {

    //creates objects of the buttons, inputs and post to initialise
    TextInputLayout firstName, lastName, phoneNumber, emailAddress, password;
    Button registerButton, loginButton;
    ValidateAdminRegistration validateAdminRegistration;
    POST post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the layout of the admin registration
        setContentView(R.layout.activity_create_admin_account);

        //initialises buttons, inputs and post
        initialiseComponents();
        setListeners();


        // register admin with credentials entered on click
        registerButton.setEnabled(false);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //call registerAdmin with given user data and checks if its successful
                    post.registerAdmin(firstName.getEditText().getText().toString(), lastName.getEditText().getText().toString(),
                            phoneNumber.getEditText().getText().toString(), emailAddress.getEditText().getText().toString(),
                            password.getEditText().getText().toString(), new PostListener() {
                                @Override
                                public void onSuccess(boolean result, String CustomerID, String FirstName, String Email) {
                                    //if the it worked
                                    if (result) {
                                        //sets loggedInUser as the the admin account created
                                        LoggedInUser loggedInUser = new LoggedInUser(CustomerID, FirstName, Email);
                                        UserManager.getInstance(CreateAdminAccount.this).userLogin(loggedInUser);

                                        // success message
                                        AlertDialog.Builder message = new AlertDialog.Builder(CreateAdminAccount.this);
                                        message.setMessage(firstName.getEditText().getText().toString() + ", you have " +
                                                "successfully created an account");
                                        message.setCancelable(false);
                                        message.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // redirects to AdminAccountActivity page
                                                Intent i = new Intent(CreateAdminAccount.this, AdminAccountActivity.class);
                                                startActivity(i);
                                            }
                                        });
                                        AlertDialog alert = message.create();
                                        alert.show();
                                    }//error message asking to try again if it fails
                                    else {
                                        Toast.makeText(CreateAdminAccount.this, "Unable to register, please try again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                catch (Exception e) {
                    Toast.makeText(CreateAdminAccount.this, "Internal error logging in", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // go back to login page
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * initialises objects set in global
     */
    public void initialiseComponents() {
        firstName = (TextInputLayout) findViewById(R.id.first_name);
        lastName = (TextInputLayout) findViewById(R.id.last_name);
        phoneNumber = (TextInputLayout) findViewById(R.id.phone_number);
        emailAddress = (TextInputLayout) findViewById(R.id.email_address);
        password = (TextInputLayout) findViewById(R.id.admin_password);
        registerButton = (Button) findViewById(R.id.admin_register_button);
        loginButton = findViewById(R.id.sign_in);
        post = new POST(CreateAdminAccount.this);
        validateAdminRegistration = new ValidateAdminRegistration();
    }

    /**
     * sets the listeners to make sure account details are correct
     */
    public void setListeners() {
        firstName.getEditText().addTextChangedListener(payTextWatcher);
        lastName.getEditText().addTextChangedListener(payTextWatcher);
        emailAddress.getEditText().addTextChangedListener(payTextWatcher);
        password.getEditText().addTextChangedListener(payTextWatcher);
        phoneNumber.getEditText().addTextChangedListener(payTextWatcher);
    }


    /**
     * checks if the entered details are valid
     */
    private TextWatcher payTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void afterTextChanged(Editable s) {
            if (validateAdminRegistration.checkValid(firstName, lastName, phoneNumber, emailAddress, password)) {
                registerButton.setEnabled(true);
            }
            else {
                registerButton.setEnabled(false);
            }
        }
    };
}

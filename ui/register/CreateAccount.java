package com.example.teamproject.ui.register;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.teamproject.R;
import com.example.teamproject.data.REST.GET;
import com.example.teamproject.data.REST.POST;
import com.example.teamproject.data.REST.PostListener;
import com.example.teamproject.data.SendingEmail;
import com.example.teamproject.data.UserManager;
import com.example.teamproject.data.model.LoggedInUser;
import com.example.teamproject.ui.login.LoginActivity;
import com.example.teamproject.ui.useraccount.activity.UserAccountActivity;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author Evan Hosking
 * Gets the data the user entered and creates an user account,
 * if validation passed.
 */

public class CreateAccount extends AppCompatActivity {

    TextInputLayout firstName, lastName, phoneNumber, emailAddress, password;
    Button registerButton, loginButton;
    ValidateRegistration validateRegistration;
    POST post;
    GET get;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        initialiseComponents();
        setListeners();


        // register user with credentials entered
        registerButton.setEnabled(false);
        registerButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              try {
                post.register(firstName.getEditText().getText().toString(), lastName.getEditText().getText().toString(),
                  phoneNumber.getEditText().getText().toString(), emailAddress.getEditText().getText().toString(),
                  password.getEditText().getText().toString(), new PostListener() {
                      @Override
                      public void onSuccess(boolean result, String CustomerID, String FirstName, String Email) {
                          if (result) {
                              Thread thread = new Thread(new Runnable() {
                                  @Override
                                  public void run() {
                                      try {
                                          SendingEmail.sendCustIDEmail(Email,CustomerID);
                                      } catch (Exception e) {
                                          e.printStackTrace();
                                      }
                                  }
                              });
                              thread.start();
                              // creates logged in user
                              LoggedInUser loggedInUser = new LoggedInUser(CustomerID, FirstName, Email);
                              UserManager.getInstance(CreateAccount.this).userLogin(loggedInUser);

                              // success message
                              AlertDialog.Builder message = new AlertDialog.Builder(CreateAccount.this);
                              message.setMessage(firstName.getEditText().getText().toString() + ", you have " +
                                      "successfully created an account");
                              message.setCancelable(false);
                              message.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                  public void onClick(DialogInterface dialog, int id) {
                                      // redirects to account page
                                      Intent i = new Intent(CreateAccount.this, UserAccountActivity.class);
                                      startActivity(i);
                                  }
                              });
                              AlertDialog alert = message.create();
                              alert.show();
                          }
                          else {
                              Toast.makeText(CreateAccount.this, "Unable to register, please try again", Toast.LENGTH_SHORT).show();
                          }
                      }
                  });
              }
              catch (Exception e) {
                  Toast.makeText(CreateAccount.this, "Internal error logging in", Toast.LENGTH_SHORT).show();
                  e.printStackTrace();
              }
          }
      });


        // go back to login page
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initialiseComponents() {
        firstName = (TextInputLayout) findViewById(R.id.first_name);
        lastName = (TextInputLayout) findViewById(R.id.last_name);
        phoneNumber = (TextInputLayout) findViewById(R.id.phone_number);
        emailAddress = (TextInputLayout) findViewById(R.id.email_address);
        password = (TextInputLayout) findViewById(R.id.admin_password);
        registerButton = (Button) findViewById(R.id.sign_in);
        loginButton = findViewById(R.id.sign_in_button);
        post = new POST(CreateAccount.this);
        validateRegistration = new ValidateRegistration();
    }

    public void setListeners() {
        firstName.getEditText().addTextChangedListener(payTextWatcher);
        lastName.getEditText().addTextChangedListener(payTextWatcher);
        emailAddress.getEditText().addTextChangedListener(payTextWatcher);
        password.getEditText().addTextChangedListener(payTextWatcher);
        phoneNumber.getEditText().addTextChangedListener(payTextWatcher);
    }


    private TextWatcher payTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void afterTextChanged(Editable s) {
            if (validateRegistration.checkValid(firstName, lastName, phoneNumber, emailAddress, password)) {
                registerButton.setEnabled(true);
            }
            else {
                registerButton.setEnabled(false);
            }
        }
    };
}
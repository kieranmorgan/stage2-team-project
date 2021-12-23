package com.example.teamproject.ui.register;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamproject.R;
import com.example.teamproject.data.REST.POST;
import com.example.teamproject.data.REST.PostListener;
import com.example.teamproject.ui.useraccount.activity.AdminAccountActivity;
import com.example.teamproject.ui.useraccount.activity.DeleteUserActivity;
import com.google.android.material.textfield.TextInputLayout;

/**
 * @author Matthew Holdsworth
 */

public class AdminCreateUser extends AppCompatActivity {

    //creates buttons and inputs as global variables, as well as create POST object
    TextInputLayout firstName, lastName, phoneNumber, emailAddress, password;
    Button registerButton, returnButton;
    ValidateAdminRegistration validateAdminRegistration;
    POST post;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_user);

        initialiseComponents();
        setListeners();

        // go back to login page
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCreateUser.this, AdminAccountActivity.class);
                startActivity(intent);
            }
        });


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
                                        // success message
                                        AlertDialog.Builder message = new AlertDialog.Builder(AdminCreateUser.this);
                                        message.setMessage(firstName.getEditText().getText().toString() + ", you have " +
                                                "successfully created an account");
                                        message.setCancelable(false);
                                        message.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // redirects to account page
                                                Intent i = new Intent(AdminCreateUser.this, AdminAccountActivity.class);
                                                startActivity(i);
                                            }
                                        });
                                        AlertDialog alert = message.create();
                                        alert.show();
                                    } else {
                                        Toast.makeText(AdminCreateUser.this, "Unable to register, please try again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } catch (Exception e) {
                    Toast.makeText(AdminCreateUser.this, "Internal error logging in", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // go back to login page
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminAccountActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * initialises the global objects
     */
    public void initialiseComponents() {
        firstName = (TextInputLayout) findViewById(R.id.first_name);
        lastName = (TextInputLayout) findViewById(R.id.last_name);
        phoneNumber = (TextInputLayout) findViewById(R.id.phone_number);
        emailAddress = (TextInputLayout) findViewById(R.id.email_address);
        password = (TextInputLayout) findViewById(R.id.admin_password);
        registerButton = (Button) findViewById(R.id.sign_in);
        returnButton = findViewById(R.id.sign_in);
        post = new POST(AdminCreateUser.this);
        validateAdminRegistration = new ValidateAdminRegistration();
        backButton = findViewById(R.id.back_button);
    }

    /**
     * sets listeners on the text inputs to monitor for verification
     */
    public void setListeners() {
        firstName.getEditText().addTextChangedListener(payTextWatcher);
        lastName.getEditText().addTextChangedListener(payTextWatcher);
        emailAddress.getEditText().addTextChangedListener(payTextWatcher);
        password.getEditText().addTextChangedListener(payTextWatcher);
        phoneNumber.getEditText().addTextChangedListener(payTextWatcher);
    }

    /**
     * Checks if the text in the input boxes is valid
     */
    private TextWatcher payTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (validateAdminRegistration.checkValid(firstName, lastName, phoneNumber, emailAddress, password)) {
                registerButton.setEnabled(true);
            } else {
                registerButton.setEnabled(false);
            }
        }
    };
}

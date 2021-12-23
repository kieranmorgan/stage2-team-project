package com.example.teamproject.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.example.teamproject.R;
import com.example.teamproject.ui.register.CreateAdminAccount;
import com.google.android.material.textfield.TextInputLayout;

/**
 * @author Matthew Holdsworth
*/

public class AdminLoginActivity extends AppCompatActivity {

    //generates objects of buttons and text inputs
    private TextInputLayout adminUsername, adminPassword;
    private Button adminLoginButton, customerUserButton, adminRegisterButton;
    private LoginDataSource loginDataSource;
    private ValidateLogin validateLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sets the layout to admin login layout
        setContentView(R.layout.activity_admin_login);

        //initialises the objects created in global
        initialiseComponents();

        adminUsername.getEditText().addTextChangedListener(textListener);
        adminPassword.getEditText().addTextChangedListener(textListener);

        // go back to login page
        customerUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminLoginActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // go to admin page
        adminLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // attempt admin login
                loginDataSource.loginAdmin(adminUsername.getEditText().getText().toString(), adminPassword.getEditText().getText().toString());
            }
        });

        // got to admin register
        adminRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminLoginActivity.this, CreateAdminAccount.class);
                startActivity(intent);
            }
        });
    }

    /**
     * initialises the objects
     */
    private void initialiseComponents() {
        adminUsername = findViewById(R.id.admin_username);
        adminPassword =findViewById(R.id.admin_password);
        adminLoginButton = findViewById(R.id.admin_login);
        adminRegisterButton = findViewById(R.id.admin_register_button);
        validateLogin = new ValidateLogin();
        loginDataSource = new LoginDataSource(AdminLoginActivity.this);
        customerUserButton = findViewById(R.id.customer_login_button);
    }

    /**
     * makes sure the text input fields are not invalid
     */
    private TextWatcher textListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (validateLogin.checkValid(adminUsername, adminPassword)) {
                adminLoginButton.setEnabled(true);
            }
            else {
                adminLoginButton.setEnabled(false);
            }
        }
    };
}
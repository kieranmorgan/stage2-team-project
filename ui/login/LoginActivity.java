package com.example.teamproject.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.teamproject.ui.register.CreateAccount;
import com.example.teamproject.R;
import com.google.android.material.textfield.TextInputLayout;

/**
 * @author Evan Hosking
 * Handles the login authentication when a user attempts to login.
 */

public class LoginActivity extends AppCompatActivity {

    private LoginDataSource loginDataSource;
    private ValidateLogin validateLogin;
    private TextView notRegistered;
    private TextInputLayout username, password;
    private Button loginButton, adminLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialiseComponents();

        username.getEditText().addTextChangedListener(textListener);
        password.getEditText().addTextChangedListener(textListener);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDataSource.login(username.getEditText().getText().toString(), password.getEditText().getText().toString());
                password.getEditText().getText().clear();
            }
        });

        notRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CreateAccount.class);
                startActivity(intent);
            }
        });

        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, AdminLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initialiseComponents() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        validateLogin = new ValidateLogin();
        loginDataSource = new LoginDataSource(LoginActivity.this);
        notRegistered = findViewById(R.id.new_stubank);
        adminLogin = findViewById(R.id.admin_login);
    }

    private TextWatcher textListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (validateLogin.checkValid(username, password)) {
                loginButton.setEnabled(true);
            }
            else {
                loginButton.setEnabled(false);
            }
        }
    };
}
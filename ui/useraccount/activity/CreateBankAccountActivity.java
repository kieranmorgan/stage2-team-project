package com.example.teamproject.ui.useraccount.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teamproject.R;
import com.example.teamproject.data.REST.POST;
import com.example.teamproject.data.REST.PostListener;
import com.example.teamproject.data.UserManager;
import com.example.teamproject.data.model.LoggedInUser;
import com.example.teamproject.ui.useraccount.validation.CreateAccountInputValidation;
import com.google.android.material.textfield.TextInputLayout;

/**
 * @author Evan Hosking
 * Allows user to create an bank account
 */

public class CreateBankAccountActivity extends AppCompatActivity {

    private TextView createTitle;
    private TextInputLayout balance, lastName;
    private Button createButton;
    private POST post;
    private LoggedInUser user;
    private CreateAccountInputValidation validation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bank_account);

        initialiseComponents();
        setListeners();

        createButton.setEnabled(false);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customerID = user.getUserId();
                createAccount(customerID, Double.parseDouble(balance.getEditText().getText().toString()),
                        lastName.getEditText().getText().toString());
            }
        });
    }

    public void initialiseComponents() {
        user = UserManager.getInstance(CreateBankAccountActivity.this).getUser();
        Toolbar toolbar = findViewById(R.id.create_ba_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        createTitle = (TextView) findViewById(R.id.create_ba_title);
        createTitle.setText("Create a bank account");
        createButton = findViewById(R.id.create_bank_account_button);
        balance = findViewById(R.id.balance_account);
        lastName = findViewById(R.id.last_name_account);
        validation = new CreateAccountInputValidation();
    }

    /**
     * Creates a bank account for the user currently logged in
     * @param customerID
     * @param balance
     * @param lastName
     */
    private void createAccount(String customerID, double balance, String lastName) {
        post = new POST(CreateBankAccountActivity.this);
        post.createAccount(customerID, balance, lastName, new PostListener() {
            @Override
            public void onSuccess(boolean result, String CustomerID, String FirstName, String Email) {
                try {
                    if (result) {
                        AlertDialog.Builder message = new AlertDialog.Builder(CreateBankAccountActivity.this);
                        message.setTitle("User: " + customerID);
                        message.setMessage("Successfully created an account");
                        message.setCancelable(false);
                        message.setPositiveButton(
                                "Okay",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent i = new Intent(CreateBankAccountActivity.this, UserAccountActivity.class);
                                        startActivity(i);
                                    }
                                });
                        AlertDialog alert = message.create();
                        alert.show();
                    } else {
                        Toast.makeText(CreateBankAccountActivity.this, "Unable to create an" +
                                " account, please try again", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e) {
                    Toast.makeText(CreateBankAccountActivity.this, "Internal error " +
                            "creating account, please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setListeners() {
        balance.getEditText().addTextChangedListener(payTextWatcher);
        lastName.getEditText().addTextChangedListener(payTextWatcher);
    }

    private TextWatcher payTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void afterTextChanged(Editable s) {
            if (validation.checkInput(balance, lastName)) {
                createButton.setEnabled(true);
            }
            else {
                createButton.setEnabled(false);
            }
        }
    };

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
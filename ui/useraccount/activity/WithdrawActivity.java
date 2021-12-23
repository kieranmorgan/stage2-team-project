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
import com.example.teamproject.data.model.BankAccount;
import com.example.teamproject.ui.useraccount.validation.TransactionInputValidation;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Evan Hosking
 * Gets the data entered by the user and executes a withdraw transaction,
 * calling database methods.
 */

public class WithdrawActivity extends AppCompatActivity {

    private TextView withdrawTitle, dateValue, userDigitalCardNumber, userAccountNumber, accountBalance;
    private TextInputLayout withdrawAmount;
    private Button authoriseButton;
    private TransactionInputValidation transactionInputValidation;
    private Toolbar toolbar;
    private BankAccount accounts;
    private POST post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        initialiseComponents();
        setListener();

        authoriseButton.setEnabled(false);
        authoriseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.generateWithdrawTransaction(accounts.getDigitalCardNumber(), Double.parseDouble(withdrawAmount.getEditText().getText().toString()),
                        getTime(), getDate(), new PostListener() {
                        @Override
                        public void onSuccess(boolean result, String CustomerID, String FirstName, String Email) {
                        if (result) {
                            AlertDialog.Builder message = new AlertDialog.Builder(WithdrawActivity.this);
                            message.setMessage("Successful withdraw of £" + withdrawAmount.getEditText().getText().toString());
                            message.setCancelable(false);
                            message.setPositiveButton(
                                    "Okay",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Intent i = new Intent(WithdrawActivity.this, UserAccountActivity.class);
                                            startActivity(i);
                                        }
                                    });
                            AlertDialog alert = message.create();
                            alert.show();
                        }
                        else {
                            Toast.makeText(WithdrawActivity.this, "Error executing transaction, " +
                                    "Wrong credentials or insufficient funds", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    public void initialiseComponents() {
        transactionInputValidation = new TransactionInputValidation();
        Bundle data = getIntent().getExtras();
        accounts = (BankAccount) data.getParcelable("UserAccount");
        post = new POST(WithdrawActivity.this);

        toolbar = findViewById(R.id.withdraw_toolbar);
        userDigitalCardNumber = (TextView) findViewById(R.id.digital_card_number);
        userAccountNumber = (TextView) findViewById(R.id.number_account);
        accountBalance = (TextView) findViewById(R.id.account_balance);
        withdrawAmount = findViewById(R.id.withdraw_amount);
        authoriseButton = (Button) findViewById(R.id.withdraw_authorise_button);
        dateValue = (TextView) findViewById(R.id.date_value);
        dateValue.setText(getDate());

        userDigitalCardNumber.setText(accounts.getDigitalCardNumber());
        userAccountNumber.setText(accounts.getAccountNumber());
        accountBalance.setText("£"+accounts.getBalance());

        // title
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        withdrawTitle = (TextView) findViewById(R.id.withdraw_title);
        withdrawTitle.setText("Make a withdrawal");
    }

    public String getDate() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return formattedDate.format(date);
    }

    public String getTime() {
        Date time = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(time);
    }

    public void setListener() {
        withdrawAmount.getEditText().addTextChangedListener(withdrawTextWatcher);
    }

    private TextWatcher withdrawTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            if (transactionInputValidation.checkWithdrawData(withdrawAmount)) {
                authoriseButton.setEnabled(true);
            }
            else {
                authoriseButton.setEnabled(false);
            }
        }
    };

    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

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
 * Gets the data entered by the user and executes a payment transaction,
 * calling database methods.
 */

public class PayActivity extends AppCompatActivity {

    private TextView payTitle, dateValue, userDigitalCardNumber, userAccountNumber, accountBalance;
    private TextInputLayout payDigitalCardNumberNumber, payAmount;
    private Button authoriseButton;
    private TransactionInputValidation transactionInputValidation;
    private Toolbar toolbar;
    private POST post;
    private BankAccount accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        initialiseComponents();
        setListeners();;

        /**
         * Creates a payment transaction
         */
        authoriseButton.setEnabled(false);
        authoriseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            post.generateTransferTransaction(accounts.getDigitalCardNumber(), Double.parseDouble(payAmount.getEditText().getText().toString()),
                    getTime(), getDate(), payDigitalCardNumberNumber.getEditText().getText().toString(), new PostListener() {
                        @Override
                        public void onSuccess(boolean result, String CustomerID, String FirstName, String Email) {
                            if (result) {
                            AlertDialog.Builder message = new AlertDialog.Builder(PayActivity.this);
                            message.setMessage("Successful payment of £" + payAmount.getEditText().getText().toString());
                            message.setCancelable(false);
                            message.setPositiveButton(
                                    "Okay",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Intent i = new Intent(PayActivity.this, UserAccountActivity.class);
                                            startActivity(i);
                                        }
                                    });
                            AlertDialog alert = message.create();
                            alert.show();
                            }
                            else {
                                Toast.makeText(PayActivity.this, "Error executing transaction, " +
                                        "Wrong credentials or insufficient funds", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            });
    }

    public void initialiseComponents() {
        transactionInputValidation = new TransactionInputValidation();
        post = new POST(PayActivity.this);
        authoriseButton = (Button) findViewById(R.id.pay_authorise_button);
        payDigitalCardNumberNumber = (TextInputLayout) findViewById(R.id.pay_account_number);
        payAmount = (TextInputLayout) findViewById(R.id.pay_amount);
        dateValue = (TextView) findViewById(R.id.date_value);
        dateValue.setText(getDate());

        // title
        toolbar = findViewById(R.id.payment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        payTitle = (TextView) findViewById(R.id.pay_title);
        payTitle.setText("Make a payment");

        // account information
        Bundle data = getIntent().getExtras();
        accounts = (BankAccount) data.getParcelable("UserAccount");
        userDigitalCardNumber = (TextView) findViewById(R.id.digital_card_number);
        userDigitalCardNumber.setText(accounts.getDigitalCardNumber());
        userAccountNumber = (TextView) findViewById(R.id.number_account);
        userAccountNumber.setText(accounts.getAccountNumber());
        accountBalance = (TextView) findViewById(R.id.account_balance);
        accountBalance.setText("£"+accounts.getBalance());
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

    public void setListeners() {
        payDigitalCardNumberNumber.getEditText().addTextChangedListener(payTextWatcher);
        payAmount.getEditText().addTextChangedListener(payTextWatcher);
    }

    private TextWatcher payTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            if (transactionInputValidation.checkPayData(payDigitalCardNumberNumber, payAmount)) {
                authoriseButton.setEnabled(true);
            }
            else {
                authoriseButton.setEnabled(false);
            }
        }
    };

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
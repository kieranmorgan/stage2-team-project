package com.example.teamproject.ui.useraccount.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
 * Allows a user to create a pot
 */

public class CreatePotActivity extends AppCompatActivity {

    private TextView potTitle, dateValue, userDigitalCardNumber, userAccountNumber, accountBalance;
    private TextInputLayout potName, potDescription, potAmount;
    private Button createPotButton;
    private POST post;
    private BankAccount accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pot);

        initialiseComponents();

        createPotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.createPot(potName.getEditText().getText().toString(), potDescription.getEditText().getText().toString(), accounts.getDigitalCardNumber(),
                        Double.parseDouble(potAmount.getEditText().getText().toString()), getTime(), getDate(), new PostListener() {
                            @Override
                            public void onSuccess(boolean result, String CustomerID, String FirstName, String Email) {
                                if (result) {
                                    AlertDialog.Builder message = new AlertDialog.Builder(CreatePotActivity.this);
                                    message.setMessage("Created a pot of £" + potAmount.getEditText().getText().toString());
                                    message.setCancelable(false);
                                    message.setPositiveButton(
                                            "Okay",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    Intent i = new Intent(CreatePotActivity.this, UserAccountActivity.class);
                                                    startActivity(i);
                                                }
                                            });
                                    AlertDialog alert = message.create();
                                    alert.show();
                                }
                                else {
                                    Toast.makeText(CreatePotActivity.this, "Error creating pot, " +
                                            "Insufficient funds or pot already created", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

    }

    public void initialiseComponents() {
        post = new POST(CreatePotActivity.this);
        createPotButton = (Button) findViewById(R.id.create_pot);
        potName = (TextInputLayout) findViewById(R.id.pot_name);
        potDescription = (TextInputLayout) findViewById(R.id.pot_description);
        potAmount = (TextInputLayout) findViewById(R.id.pot_amount);
        dateValue = (TextView) findViewById(R.id.date_value);
        dateValue.setText(getDate());

        potTitle = (TextView) findViewById(R.id.pot_title);
        potTitle.setText("Create a pot");

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
}
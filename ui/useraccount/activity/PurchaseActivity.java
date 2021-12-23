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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
 * Gets the data entered by the user and executes a purchase transaction,
 * calling database methods.
 */

public class PurchaseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView purchaseTitle, dateValue, userDigitalCardNumber, userAccountNumber, accountBalance;
    private TextInputLayout shopName, purchaseAmount;
    private Button authoriseButton;
    private TransactionInputValidation transactionInputValidation;
    private Spinner categories;
    private POST post;
    private BankAccount accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

       initialiseComponents();
       setListener();
       setSpinnerData();
        dateValue.setText(getDate());

        // authorise button
        authoriseButton.setEnabled(false);
        authoriseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.generatePurchaseTransaction(accounts.getDigitalCardNumber(), Double.parseDouble(purchaseAmount.getEditText().getText().toString()),
                        getTime(), getDate(), shopName.getEditText().getText().toString(), new PostListener() {
                        @Override
                        public void onSuccess(boolean result, String CustomerID, String FirstName, String Email) {
                            if (result) {
                                AlertDialog.Builder message = new AlertDialog.Builder(PurchaseActivity.this);
                                message.setMessage("Successful purchase from " + shopName.getEditText().getText().toString() +
                                        "\n" + "Total spend: £" + purchaseAmount.getEditText().getText().toString());
                                message.setCancelable(false);
                                message.setPositiveButton(
                                        "Okay",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Intent i = new Intent(PurchaseActivity.this, UserAccountActivity.class);
                                                startActivity(i);
                                            }
                                        });
                                AlertDialog alert = message.create();
                                alert.show();
                            }
                            else {
                                Toast.makeText(PurchaseActivity.this, "Error executing transaction, " +
                                        "Wrong credentials or insufficient funds", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            }
        });
    }

    public void initialiseComponents() {
        transactionInputValidation = new TransactionInputValidation();
        authoriseButton = (Button) findViewById(R.id.purchase_authorise_button);
        shopName = findViewById(R.id.pay_shop_name);
        purchaseAmount = findViewById(R.id.purchase_amount);
        categories = findViewById(R.id.categories);
        Toolbar toolbar = findViewById(R.id.purchase_toolbar);
        userDigitalCardNumber = (TextView) findViewById(R.id.digital_card_number);
        userAccountNumber = (TextView) findViewById(R.id.number_account);
        accountBalance = (TextView) findViewById(R.id.account_balance);
        dateValue = (TextView) findViewById(R.id.date_value);
        post = new POST(PurchaseActivity.this);

        // title
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        purchaseTitle = (TextView) findViewById(R.id.purchase_title);
        purchaseTitle.setText("Make a purchase");

        // set accounts details for sender
        Bundle data = getIntent().getExtras();
        accounts = (BankAccount) data.getParcelable("UserAccount");
        userDigitalCardNumber.setText(accounts.getDigitalCardNumber());
        userAccountNumber.setText(accounts.getAccountNumber());
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

    public void setListener() {
        // get details for purchase
        shopName.getEditText().addTextChangedListener(purchaseTextWatcher);
        purchaseAmount.getEditText().addTextChangedListener(purchaseTextWatcher);
    }

    public void setSpinnerData() {
        // Category spinner
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.category_options, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories.setAdapter(arrayAdapter);
        categories.setOnItemSelectedListener(this);
    }


    private TextWatcher purchaseTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            if (transactionInputValidation.checkPurchaseData(shopName, purchaseAmount)) {
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

    // for spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // store category in DB?
        // categories.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
package com.example.teamproject.ui.useraccount.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teamproject.R;
import com.example.teamproject.data.REST.GET;
import com.example.teamproject.data.REST.GetListener;
import com.example.teamproject.data.UserManager;
import com.example.teamproject.data.model.BankAccount;
import com.example.teamproject.data.model.LoggedInUser;
import com.example.teamproject.ui.login.Confirm2FAActivity;
import com.example.teamproject.ui.login.LoginActivity;
import com.example.teamproject.ui.useraccount.adapter.DigitalCardAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author Evan Hosking
 * Gets bank account information for current user logged in and displays
 * as a credit card
 */

public class DigitalCardActivity extends AppCompatActivity {

    private ArrayList<BankAccount> accountsList = new ArrayList<>();
    private GET get;
    private LoggedInUser user;
    private  RecyclerView cardRecycler;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_card);

        initialiseComponents();
        setAccountData();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go back to previous page
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                    return;
                }
                else {
                    DigitalCardActivity.super.onBackPressed();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setAccountData() {
        String customerID = user.getUserId();
        get = new GET(DigitalCardActivity.this);
        get.getBankAccountDetails(customerID, new GetListener() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    // gets data from JSON and creates a bank account object
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject temp = result.getJSONObject(i);
                        BankAccount account = new BankAccount(temp.getString("CustomerID"), temp.getString("DigitalCardNumber"),
                                temp.getDouble("Balance"), temp.getString("LastName"));
                        accountsList.add(account);
                    }
                    DigitalCardAdapter adapter = new DigitalCardAdapter(accountsList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    cardRecycler.setLayoutManager(mLayoutManager);
                    cardRecycler.setItemAnimator(new DefaultItemAnimator());
                    cardRecycler.setAdapter(adapter);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void initialiseComponents() {
        cardRecycler = findViewById(R.id.card_recycler);
        user = UserManager.getInstance(DigitalCardActivity.this).getUser();
        backButton = findViewById(R.id.back_button);
    }
}
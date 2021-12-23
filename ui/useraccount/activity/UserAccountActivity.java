package com.example.teamproject.ui.useraccount.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.teamproject.R;
import com.example.teamproject.data.REST.GetListener;
import com.example.teamproject.data.REST.GET;
import com.example.teamproject.data.UserManager;
import com.example.teamproject.data.model.BankAccount;
import com.example.teamproject.data.model.LoggedInUser;
import com.example.teamproject.ui.useraccount.AccountCardviewListener;
import com.example.teamproject.ui.useraccount.adapter.BankAccountAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author Evan Hosking
 * Gets the data stored about bank accounts a user holds.
 * Displays this data as card views
 */

public class UserAccountActivity extends AppCompatActivity {

    private ArrayList<BankAccount> accountsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Button createAccountButton, logout;
    private TextView homeTitle;
    private GET get;
    private LoggedInUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        initialiseComponents();

        // sets welcome message to user
        homeTitle.setText("Welcome, " + user.getFirstName());

        // for create account button
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // directs to create account page
                Intent i = new Intent(getApplicationContext(), CreateBankAccountActivity.class);
                startActivity(i);
            }
        });

        // logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManager.getInstance(getApplicationContext()).logout();
            }
        });

        // set card views with extracted bank account data
        setAccountData();

    }

    // ensures the page is up to date each time it is loaded
    @Override
    public void onRestart(){
        super.onRestart();
        Intent restart = new Intent(UserAccountActivity.this, UserAccountActivity.class);
        startActivity(restart);
        this.finish();
    }

    public void initialiseComponents() {
        homeTitle = findViewById(R.id.home_title);
        createAccountButton = findViewById(R.id.create_account_button);
        logout = findViewById(R.id.logout);
        recyclerView = findViewById(R.id.accounts_recycler);
        user = UserManager.getInstance(UserAccountActivity.this).getUser();
    }

    private void setAccountData() {
        // gets userID of current logged in user
        String customerID = user.getUserId();

        get = new GET(UserAccountActivity.this);
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

                    // sets data extracted to the card view using the adapter
                    BankAccountAdapter adapter = new BankAccountAdapter(accountsList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());

                    adapter.setOnItemClickListener(new AccountCardviewListener<BankAccount>() {
                        @Override
                        public void onItemClick(BankAccount data) {

                            Intent i = new Intent(getApplicationContext(), UserAccountDetailsActivity.class);
                            i.putExtra("UserAccount", data);
                            startActivity(i);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

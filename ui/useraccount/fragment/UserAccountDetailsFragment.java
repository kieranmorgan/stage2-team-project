package com.example.teamproject.ui.useraccount.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.teamproject.R;
import com.example.teamproject.data.REST.GET;
import com.example.teamproject.data.REST.GetListener;
import com.example.teamproject.data.model.BankAccount;
import com.example.teamproject.data.model.Transaction;
import com.example.teamproject.ui.useraccount.adapter.TransactionAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

/**
 * @author Evan Hosking
 * Gets the bank account details and displays them, and displays all transactions
 * associated with that account as a list.
 */

public class UserAccountDetailsFragment extends Fragment {

    private TextView balance, weeklyTotal;
    private BankAccount accounts;
    private ArrayList<Transaction> transactionList = new ArrayList<>();
    private GET get;
    private RecyclerView recyclerView;

    public UserAccountDetailsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            accounts =  bundle.getParcelable("UserAccount");
        }

        get = new GET(getContext());
        get.getWeeklySpend(accounts.getDigitalCardNumber(), new GetListener() {
            @Override
            public void onSuccess(JSONArray result) {
                balance.setText("£"+accounts.getBalance());
                try {
                    double total = result.getDouble(0);
                    String totalFormatted = String.format(Locale.UK, "%.2f", total);
                    weeklyTotal.setText("£"+totalFormatted);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_account_details, container, false);

        initialiseComponents(view);
        setData();

        return view;
    }

    public void initialiseComponents(View view) {
        balance = view.findViewById(R.id.balance_value);
        weeklyTotal = view.findViewById(R.id.total_week_value);
        recyclerView = view.findViewById(R.id.transaction_recycler);
    }

    private void setData() {
        get = new GET(getContext());
        get.getTransactions(accounts.getDigitalCardNumber(), new GetListener() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    // gets data from JSON and creates a transaction object
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject temp = result.getJSONObject(i);
                        Transaction transaction = new Transaction(temp.getString("DigitalCardNumber"),
                                temp.getDouble("Balance"), temp.getString("Type"), temp.getString("Time"),
                                temp.getString("Date"), temp.getString("Transactor"));
                        transactionList.add(transaction);
                    }
                    TransactionAdapter adapter = new TransactionAdapter(transactionList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
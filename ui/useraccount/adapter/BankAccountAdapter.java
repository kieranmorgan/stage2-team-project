package com.example.teamproject.ui.useraccount.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamproject.R;
import com.example.teamproject.data.model.BankAccount;
import com.example.teamproject.ui.useraccount.AccountCardviewListener;

import java.util.ArrayList;

/**
 * @author Evan Hosking
 * Provides access for the recycler view to the user's bank account data.
 * To allow data to bind and be displayed.
 */

public class BankAccountAdapter extends RecyclerView.Adapter<BankAccountAdapter.MyViewHolder> {

    private ArrayList<BankAccount> accountsList;
    private AccountCardviewListener<BankAccount> accountCardviewListener;

    public BankAccountAdapter(ArrayList<BankAccount> accounts) {
        this.accountsList = accounts;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView digitalCardNumber, accountNumber, Balance;
        public CardView cardView;

        public MyViewHolder(final View view) {
            super(view);
            digitalCardNumber = (TextView) view.findViewById(R.id.digital_card_number);
            accountNumber = (TextView) view.findViewById(R.id.number_account);
            Balance = (TextView) view.findViewById(R.id.account_balance);
            cardView = itemView.findViewById(R.id.carView);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_account_layout,
                parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final BankAccount accounts = accountsList.get(position);

        holder.digitalCardNumber.setText(accounts.getDigitalCardNumber());
        holder.accountNumber.setText(accounts.getAccountNumber());
        holder.Balance.setText("£"+accounts.getBalance());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountCardviewListener.onItemClick(accounts);
            }
        });
    }

    @Override
    public int getItemCount() {
        return accountsList.size();
    }

    public void setOnItemClickListener(AccountCardviewListener<BankAccount> accountsAccountCardviewListener) {
        this.accountCardviewListener = accountsAccountCardviewListener;
    }
}



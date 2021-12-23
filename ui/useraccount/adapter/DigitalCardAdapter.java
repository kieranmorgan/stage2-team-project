package com.example.teamproject.ui.useraccount.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.teamproject.R;
import com.example.teamproject.data.model.BankAccount;
import com.vinaygaba.creditcardview.CreditCardView;

import java.util.ArrayList;

/**
 * @author Evan Hosking
 * Provides access for the recycler view to the user's account data.
 * Binds to the credit card view
 */

public class DigitalCardAdapter extends RecyclerView.Adapter<DigitalCardAdapter.MyViewHolder> {

    private ArrayList<BankAccount> accountsList;

    public DigitalCardAdapter(ArrayList<BankAccount> accounts) {
        this.accountsList = accounts;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CreditCardView creditCardView;

        public MyViewHolder(final View view) {
            super(view);
            creditCardView = view.findViewById(R.id.card);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.digital_card_layout,
                parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final BankAccount accounts = accountsList.get(position);

        holder.creditCardView.setCardNumber(accounts.getDigitalCardNumber());
        holder.creditCardView.setCardName(String.valueOf(accounts.getAccountNumber()));
        //holder.creditCardView.setCardBackBackground(R.drawable.background);
    }

    @Override
    public int getItemCount() {
        return accountsList.size();
    }
}



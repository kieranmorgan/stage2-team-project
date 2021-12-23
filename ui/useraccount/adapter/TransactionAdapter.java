package com.example.teamproject.ui.useraccount.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.teamproject.R;
import com.example.teamproject.data.model.Transaction;

import java.util.ArrayList;

/**
 * @author Evan Hosking
 * Provides access for the recycler view to the user's transaction data.
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {

    private ArrayList<Transaction> transactionsList;

    public TransactionAdapter(ArrayList<Transaction> transactions) {
            this.transactionsList = transactions;
        }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date, amount, type;

        public MyViewHolder(final View view) {
            super(view);
            date = view.findViewById(R.id.date);
            amount = view.findViewById(R.id.amount);
            type = view.findViewById(R.id.type);
        }
    }

    @Override
    public TransactionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item_layout,
                parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TransactionAdapter.MyViewHolder holder, int position) {
        final Transaction transaction = transactionsList.get(position);

        // alternating colours for transactions
        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else {
            holder.itemView.setBackgroundColor(Color.parseColor("#D3D3D3"));
        }

        holder.date.setText(transaction.getDate());
        holder.amount.setText("£"+transaction.getAmount());
        holder.type.setText(transaction.getType());
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }
}


package com.example.teamproject.ui.useraccount.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.teamproject.R;
import com.example.teamproject.data.model.User;
import com.example.teamproject.ui.useraccount.AccountCardviewListener;

import java.util.List;

/**
 * @author Matthew Holdswrth
 * Provides access for recyclerview to allows User data to be displayed on the admin account page
 */

public class AdminAccountAdapter extends RecyclerView.Adapter<AdminAccountAdapter.ViewHolder> {

    Context context;
    List<User> userAccounts;
    AccountCardviewListener<User> accountCardviewListener;

    public AdminAccountAdapter(Context context, List<User> userAccounts) {
        this.context = context;
        this.userAccounts = userAccounts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_admin_account_recycler,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(userAccounts != null && userAccounts.size() > 0){
            User model = userAccounts.get(position);
            holder.customer_id.setText(model.getCustomerID());
            holder.last_name.setText(model.getLastName());
            holder.phone_number.setText(model.getPhoneNumber());
            holder.email.setText(model.getEmail());
        } else{
            return;
        }
    }

    @Override
    public int getItemCount() {
        return userAccounts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView customer_id, last_name, phone_number, email;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            customer_id = itemView.findViewById(R.id.customer_id);
            last_name = itemView.findViewById(R.id.last_name);
            phone_number = itemView.findViewById(R.id.phone_number);
            email = itemView.findViewById(R.id.email);
        }
    }

    public void setOnItemClickListener(AccountCardviewListener<User> userAccountCardviewListener) {
        this.accountCardviewListener = userAccountCardviewListener;
    }
}
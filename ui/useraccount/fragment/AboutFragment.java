package com.example.teamproject.ui.useraccount.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.teamproject.R;
import com.example.teamproject.data.model.BankAccount;
import com.example.teamproject.ui.useraccount.activity.DigitalCardActivity;
import com.example.teamproject.ui.useraccount.activity.UserDetailsActivity;


/**
 * @author Evan Hosking
 * Contains list of options related to the user's information
 */

public class AboutFragment extends Fragment {

    private TextView aboutTitle;
    private Button viewUser, viewCards;
    private BankAccount accounts;

    public AboutFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            accounts =  bundle.getParcelable("UserAccount");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        initialiseComponents(view);

        viewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), UserDetailsActivity.class);
                startActivity(i);
            }
        });

        viewCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), DigitalCardActivity.class);
                i.putExtra("UserAccount", accounts);
                startActivity(i);
            }
        });
        return view;
    }

    public void initialiseComponents(View view) {
        aboutTitle = (TextView) view.findViewById(R.id.title);
        aboutTitle.setText("About");
        viewCards = view.findViewById(R.id.view_cards);
        viewUser = view.findViewById(R.id.view_user);
    }
}
package com.example.teamproject.ui.useraccount.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.teamproject.R;
import com.example.teamproject.data.model.BankAccount;
import com.example.teamproject.ui.useraccount.activity.PayActivity;
import com.example.teamproject.ui.useraccount.activity.PurchaseActivity;
import com.example.teamproject.ui.useraccount.activity.WithdrawActivity;

/**
 * @author Evan Hosking
 * Displays the three types of transactions options, and provides access to
 * each page.
 */

public class TransferFragment extends Fragment implements View.OnClickListener {

    private TextView transferTitle;
    private BankAccount accounts;

    public TransferFragment() {}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            accounts =  bundle.getParcelable("UserAccount");
        }
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.pay_card:
                i = new Intent(getActivity().getApplicationContext(), PayActivity.class);
                i.putExtra("UserAccount", accounts);
                startActivity(i);
                break;
            case R.id.purchase_card:
                AlertDialog.Builder message = new AlertDialog.Builder(getContext());
                message.setMessage("A purchase transaction allows you to record any purchases made" +
                        " and the shop where the order is placed");
                message.setCancelable(true);
                message.setPositiveButton(
                        "Okay",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(getActivity().getApplicationContext(), PurchaseActivity.class);
                                i.putExtra("UserAccount", accounts);
                                startActivity(i);
                            }
                        });
                AlertDialog alert = message.create();
                alert.show();
                break;
            case R.id.withdraw_card:
                i = new Intent(getActivity().getApplicationContext(), WithdrawActivity.class);
                i.putExtra("UserAccount", accounts);
                startActivity(i);
                break;
            default:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transfer, container, false);

        initialiseComponents(view);

        CardView payCard = (CardView) view.findViewById(R.id.pay_card);
        payCard.setOnClickListener(this);
        CardView purchaseCard = (CardView) view.findViewById(R.id.purchase_card);
        purchaseCard.setOnClickListener(this);
        CardView withdrawCard = (CardView) view.findViewById(R.id.withdraw_card);
        withdrawCard.setOnClickListener(this);

        return view;
    }

    public void initialiseComponents(View view) {
        transferTitle = (TextView) view.findViewById(R.id.title);
        transferTitle.setText("Transfers and payments");
    }
}
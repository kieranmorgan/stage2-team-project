package com.example.teamproject.ui.useraccount.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teamproject.R;
import com.example.teamproject.data.REST.GET;
import com.example.teamproject.data.REST.GetListener;
import com.example.teamproject.data.REST.POST;
import com.example.teamproject.data.REST.PostListener;
import com.example.teamproject.data.model.BankAccount;
import com.example.teamproject.data.model.Pot;
import com.example.teamproject.ui.useraccount.AddPotClickListener;
import com.example.teamproject.ui.useraccount.DeletePotClickListener;
import com.example.teamproject.ui.useraccount.activity.CreatePotActivity;
import com.example.teamproject.ui.useraccount.activity.DigitalCardActivity;
import com.example.teamproject.ui.useraccount.activity.UserAccountDetailsActivity;
import com.example.teamproject.ui.useraccount.activity.UserDetailsActivity;
import com.example.teamproject.ui.useraccount.adapter.PotsAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Evan Hosking
 * Option to create a pot and display pots
 * Additionally allows users to add funds and delete their pot
 */

public class ManageFragment extends Fragment {

    private TextView manageTitle;
    private GET get;
    private POST post;
    private ArrayList<Pot> potsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PotsAdapter adapter;
    private Button createPot;
    private BankAccount accounts;

    public ManageFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            accounts =  bundle.getParcelable("UserAccount");
        }

        get = new GET(getContext());
        get.getPots(accounts.getDigitalCardNumber(), new GetListener() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject temp = result.getJSONObject(i);
                        Pot pot = new Pot(temp.getString("PotName"), temp.getString("DigitalCardNumber"),
                                temp.getString("PotDescription"), temp.getDouble("Amount"));
                        potsList.add(pot);
                    }
                    // sets pot data using adapter
                    adapter = new PotsAdapter(potsList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());

                    // click listener for delete button
                    adapter.setOnDeleteClickListener(new DeletePotClickListener<Pot>() {
                        @Override
                        public void onItemClick(Pot data) {
                            deletePot(potsList.get(0).getPotName(), potsList.get(0).getDigitalCardNumber());
                        }
                    });

                    // click listener for add button
                    adapter.setOnAddClickListener(new AddPotClickListener<Pot>() {
                        @Override
                        public void onItemClick(Pot data) {
                            addToPot(potsList.get(0).getPotName(), potsList.get(0).getDigitalCardNumber());
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage, container, false);

       initialiseComponents(view);

       // directs to create pot page
       createPot.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(getContext(), CreatePotActivity.class);
               i.putExtra("UserAccount", accounts);
               startActivity(i);
           }
       });
       return view;
    }

    /**
     * Deletes the pot, if a user has created one. Adds funds back to associated account
     * @param potName
     * @param DigitalCardNumber
     */
    public void deletePot(String potName, String DigitalCardNumber) {
        post.deletePot(potName, DigitalCardNumber, new PostListener() {
            @Override
            public void onSuccess(boolean result, String CustomerID, String FirstName, String Email) {
                if (result) {
                    Toast.makeText(getContext(), "Pot deleted, pot funds add back to your account\n" +
                            "Please refresh page to see result",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Adds funds to pot, if they have sufficient funds
     * @param potName
     * @param digitalCardNumber
     */
    public void addToPot(String potName, String digitalCardNumber) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Add to pot");
        final EditText amount = new EditText(getContext());
        amount.setInputType(InputType.TYPE_CLASS_NUMBER);
        amount.setRawInputType(Configuration.KEYBOARD_12KEY);
        alert.setView(amount);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                post.addToPot(potName, digitalCardNumber, Double.parseDouble(amount.getText().toString()), getTime(), getDate(), new PostListener() {
                    @Override
                    public void onSuccess(boolean result, String CustomerID, String FirstName, String Email) {
                        if (result) {
                            Toast.makeText(getContext(), "You have add: £" + amount.getText().toString() + " to your pot\n" +
                                            "Please refresh page to see result",
                                    Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getContext(), "Insufficient funds",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {}
        });
        alert.show();
    }

    public void initialiseComponents(View view) {
        post = new POST(getContext());
        manageTitle = (TextView) view.findViewById(R.id.title);
        manageTitle.setText("Manage finances");
        recyclerView = view.findViewById(R.id.pots_recycler);
        createPot = view.findViewById(R.id.create_pot);
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
}
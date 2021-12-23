package com.example.teamproject.ui.useraccount.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.teamproject.R;
import com.example.teamproject.data.model.BankAccount;
import com.example.teamproject.ui.useraccount.fragment.AboutFragment;
import com.example.teamproject.ui.useraccount.fragment.ManageFragment;
import com.example.teamproject.ui.useraccount.fragment.TransferFragment;
import com.example.teamproject.ui.useraccount.fragment.UserAccountDetailsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * @author Evan Hosking
 * Activity displaying user details and provides outline for fragments to
 * be added when selected using the bottom navigation bar.
 */

public class UserAccountDetailsActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    private Bundle bundle, data;
    private BankAccount accounts;
    private Toolbar toolbar;
    private TextView accountInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_details);

        initialiseComponents();

        accountInfo.setText(accounts.getDigitalCardNumber()+"\n"+accounts.getAccountNumber());

        openFragment(new UserAccountDetailsFragment(), accounts);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        openFragment(new UserAccountDetailsFragment(), accounts);
                        return true;

                    case R.id.transfer:
                        openFragment(new TransferFragment(), accounts);
                        return true;

                    case R.id.manage:
                        openFragment(new ManageFragment(), accounts);
                        return true;

                    case R.id.about:
                        openFragment(new AboutFragment(), accounts);
                        return true;
                }
                return false;
            }
        });

    }

    public void initialiseComponents() {
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        data = getIntent().getExtras();
        accounts = (BankAccount) data.getParcelable("UserAccount");
        accountInfo = (TextView) findViewById(R.id.account_info);
        bottomNavigation = findViewById(R.id.bottom_navigation);
    }

    void openFragment(Fragment fragment, BankAccount accounts) {
        bundle = new Bundle();
        fragment.setArguments(bundle);
        bundle.putParcelable("UserAccount", accounts);
        //accounts = bundle.getParcelable("UserAccount");
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
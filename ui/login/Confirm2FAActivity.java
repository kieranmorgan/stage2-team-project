package com.example.teamproject.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamproject.data.model.LoggedInUser;
import com.example.teamproject.data.SendingEmail;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.teamproject.Globals;
import com.example.teamproject.R;
import com.example.teamproject.data.UserManager;
import com.example.teamproject.ui.useraccount.activity.AdminAccountActivity;
import com.example.teamproject.ui.useraccount.activity.UserAccountActivity;

/**
 * @author Evan Hosking, Kieran Morgan
 * Gets the code the user entered and checks if it matches the code
 * emailed to them.
 */

public class Confirm2FAActivity extends AppCompatActivity {
    private TextView userEmail;
    private PinView passcode;
    private Button confirm;
    private ImageView backButton;
    private LoggedInUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_2fa);

        initialiseComponents();

        // go back to login page
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManager.getInstance(getApplicationContext()).logout();
                Intent intent = new Intent(Confirm2FAActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // confirm authentication code
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userCode = Globals.userCode;
                String userCodeAsString = String.valueOf(userCode);
                if (passcode.getText().toString().equals(userCodeAsString)) {
                    // login is now authenticated -> access to account granted
                    // checks to see if this an admin account, to redirect to the correct activity
                    if (user.getUserId().charAt(0)=='A'){
                        Intent intent = new Intent(Confirm2FAActivity.this, AdminAccountActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Intent intent = new Intent(Confirm2FAActivity.this, UserAccountActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                else {
                    // resets pass-code
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                SendingEmail.send2faEmail(user.getEmailAddress());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                    passcode.getText().clear();
                    Toast.makeText(getApplicationContext(), "Wrong code, a new code has been " +
                            "sent to your email", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void initialiseComponents() {
        user = UserManager.getInstance(Confirm2FAActivity.this).getUser();
        userEmail = findViewById(R.id.user_email_value);
        userEmail.setText(user.getEmailAddress());
        passcode = findViewById(R.id.passcode);
        passcode.setAnimationEnable(true);
        backButton = findViewById(R.id.back_button);
        confirm = findViewById(R.id.confirm);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
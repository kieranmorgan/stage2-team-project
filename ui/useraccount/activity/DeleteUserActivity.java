package com.example.teamproject.ui.useraccount.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.teamproject.R;
import com.example.teamproject.data.REST.POST;
import com.example.teamproject.data.REST.PostListener;
import com.example.teamproject.data.UserManager;
import com.example.teamproject.ui.login.Confirm2FAActivity;
import com.example.teamproject.ui.login.LoginActivity;
import com.example.teamproject.ui.register.ValidateRegistration;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author Kieran Morgan
 * deletes given account number
 */

public class DeleteUserActivity extends AppCompatActivity {

    private TextInputLayout accountNumber;
    private Button deleteButton;
    private POST post;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        initialiseComponents();

        // go back to login page
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeleteUserActivity.this, AdminAccountActivity.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String number = accountNumber.getEditText().getText().toString();
                    post.deleteUser(number, new PostListener() {
                                @Override
                                public void onSuccess(boolean result, String CustomerID, String FirstName, String Email) {
                                    if (result) {
                                        // success message
                                        AlertDialog.Builder message = new AlertDialog.Builder(DeleteUserActivity.this);
                                        message.setMessage("This account has been deleted: " + number);
                                        message.setCancelable(false);
                                        message.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Intent i = new Intent(DeleteUserActivity.this, AdminAccountActivity.class);
                                                startActivity(i);
                                            }
                                        });
                                        AlertDialog alert = message.create();
                                        alert.show();
                                    }
                                    else {
                                        Toast.makeText(DeleteUserActivity.this, "Unable to delete, please try again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                catch (Exception e) {
                    Toast.makeText(DeleteUserActivity.this, "Internal error deleting", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void initialiseComponents() {
        accountNumber = (TextInputLayout) findViewById(R.id.account_number);
        deleteButton = (Button) findViewById(R.id.delete_button);
        post = new POST(DeleteUserActivity.this);
        backButton = findViewById(R.id.back_button);
    }


}
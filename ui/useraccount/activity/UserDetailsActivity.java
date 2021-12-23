package com.example.teamproject.ui.useraccount.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teamproject.R;
import com.example.teamproject.data.REST.GET;
import com.example.teamproject.data.REST.GetListener;
import com.example.teamproject.data.UserManager;
import com.example.teamproject.data.model.LoggedInUser;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Evan Hosking
 * Gets details stored about current user logged in
 */

public class UserDetailsActivity extends AppCompatActivity {

    private TextView detailsTitle, firstName, lastName, phone, email;
    private LoggedInUser user;
    private GET get;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        initialiseComponents();
        getData();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                    return;
                } else {
                    UserDetailsActivity.super.onBackPressed();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void initialiseComponents() {
        user = UserManager.getInstance(UserDetailsActivity.this).getUser();
        detailsTitle = (TextView) findViewById(R.id.user_details_title);
        firstName = (TextView) findViewById(R.id.first_name_details);
        lastName = (TextView) findViewById(R.id.last_name_details);
        phone = (TextView) findViewById(R.id.phone_details);
        email = (TextView) findViewById(R.id.email_details);
        backButton = findViewById(R.id.back_button);
    }

    public void getData() {
        String CustomerID = user.getUserId();
        get = new GET(UserDetailsActivity.this);
        get.getUserDetails(CustomerID, new GetListener() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    JSONObject values = result.getJSONObject(0);
                    detailsTitle.setText(values.getString("CustomerID"));
                    firstName.setText(values.getString("FirstName"));
                    lastName.setText(values.getString("LastName"));
                    phone.setText(values.getString("PhoneNumber"));
                    email.setText(values.getString("Email"));
                }
                catch (Exception e) {
                    Toast.makeText(UserDetailsActivity.this, "Unable to get user details," +
                            " please try again", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }
}
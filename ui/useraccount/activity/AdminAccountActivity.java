package com.example.teamproject.ui.useraccount.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.teamproject.R;
import com.example.teamproject.data.REST.GetListener;
import com.example.teamproject.data.REST.GET;
import com.example.teamproject.data.REST.POST;
import com.example.teamproject.data.UserManager;
import com.example.teamproject.data.model.User;
import com.example.teamproject.ui.register.AdminCreateUser;
import com.example.teamproject.ui.useraccount.adapter.AdminAccountAdapter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthew Holdsworth
 */

public class AdminAccountActivity extends AppCompatActivity {

    //creates button and get objects
    private RecyclerView recyclerView;
    AdminAccountAdapter adapter;
    private Button logout, createUser, delete;
    private GET get;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //set layout to the admin account
        setContentView(R.layout.activity_admin_account);

        //initialises all objects
        initialiseComponents();

        //sets the recyclerview, to display all users
        setRecyclerView();

        //delete user
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //takes user to the AdminCreateUser page
                Intent intent = new Intent(AdminAccountActivity.this, DeleteUserActivity.class);
                startActivity(intent);
            }
        });

        //create user
        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //takes user to the AdminCreateUser page
                Intent intent = new Intent(AdminAccountActivity.this, AdminCreateUser.class);
                startActivity(intent);
            }
        });

        //logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calls logout to remove credentials and return to home screen
                UserManager.getInstance(getApplicationContext()).logout();
            }
        });
    }



    /**
     * initialises objects created in global
     */
    public void initialiseComponents() {
        recyclerView = findViewById(R.id.recycler_view);
        logout = findViewById(R.id.logout);
        createUser = findViewById(R.id.create_user_button);
        delete = findViewById(R.id.delete_user_button);
    }

    /**
     * if the app is restarted return to this page
     */
//    @Override
//    public void onRestart(){
//        super.onRestart();
//        Intent restart = new Intent(AdminAccountActivity.this, AdminAccountActivity.class);
//        startActivity(restart);
//        this.finish();
//    }

    /**
     * sets the recycler view using adminAccountRecycler
     */
    private void setRecyclerView() {
        //new list of users
        List<User> Users = new ArrayList<>();
        //initialises get
        get = new GET(AdminAccountActivity.this);
        //calls get all users and on success sets layout
        get.getAllUsers(new GetListener() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    // gets the data from the JSONArray
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject temp = result.getJSONObject(i);
                        //creates new User using data received and inserts in into the User list
                        User tempUser = new User(temp.getString("CustomerID"), temp.getString("LastName"),
                                temp.getString("PhoneNumber"), temp.getString("Email"));
                        Users.add(tempUser);
                    }
                    //sets recyclerview layout scheme
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    //calls the adapter using the list of Users using AdminAccountAdapter
                    adapter = new AdminAccountAdapter(getApplicationContext(),Users);
                    //sets the adapter in recyclerview as the one created above
                    recyclerView.setAdapter(adapter);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
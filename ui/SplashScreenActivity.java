package com.example.teamproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamproject.R;
import com.example.teamproject.ui.login.LoginActivity;

/**
 * @author Evan Hosking
 * Start-up screen, re-directs to login page once splash screen displayed.
 */

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
        finish();
    }
}
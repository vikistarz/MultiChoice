package com.company.mulitchoice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.company.mulitchoice.dashboardPage.NewDashboardActivity;
import com.company.mulitchoice.logInPage.LogInActivity;
import com.company.mulitchoice.logInPage.LogInViewModel;

public class SplashScreenActivity extends AppCompatActivity {

    SharedPreferences mySharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

               mySharedPreferences = getSharedPreferences(LogInActivity.PREF_NAME, MODE_PRIVATE);
               boolean hasLoggedIn = mySharedPreferences.getBoolean("hasLoggedIn", false);

               if(hasLoggedIn){
                   Intent myIntent = new Intent(SplashScreenActivity.this, NewDashboardActivity.class);
                   startActivity(myIntent);
                   finish();
               }
               else{
                   Intent myIntent = new Intent(SplashScreenActivity.this, LogInActivity.class);
                   startActivity(myIntent);
                   finish();
               }
            }
        }, 3000);
    }
}
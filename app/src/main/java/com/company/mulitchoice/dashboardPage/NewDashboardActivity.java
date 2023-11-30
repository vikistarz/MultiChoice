package com.company.mulitchoice.dashboardPage;

import static android.content.Intent.ACTION_VIEW;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.company.mulitchoice.BaseBindingActivity;
import com.company.mulitchoice.MyReceiver;
import com.company.mulitchoice.R;
import com.company.mulitchoice.billersList.BillerListActivity;
import com.company.mulitchoice.databinding.ActivityNewDashboardBinding;
import com.company.mulitchoice.eventPage.EventsActivity;
import com.company.mulitchoice.liveScoresPage.LivescoresActivity;
import com.company.mulitchoice.logInPage.LogInActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class NewDashboardActivity extends BaseBindingActivity<ActivityNewDashboardBinding> {


    MyReceiver myReceiver = new MyReceiver();
    SliderAdapter mySliderAdapter;

    ActionBarDrawerToggle myDrawerToggle;

    BottomSheetDialog myBottomSheetDialog;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(myDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected ActivityNewDashboardBinding layoutInflater() {
        return ActivityNewDashboardBinding.inflate(getLayoutInflater());
    }

    @Override
    protected int layoutResources() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_new_dashboard;
    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter myAirplaneIntentFilter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        IntentFilter myBatteryIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_LOW);
        registerReceiver(myReceiver,myAirplaneIntentFilter);
        registerReceiver(myReceiver, myBatteryIntentFilter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myReceiver);
    }

    @Override
    protected void inItComponent() {

        navDrawer();
        sliderView();
        initListener();
        
    }

    private void navDrawer() {
        setSupportActionBar(getViewBinding().toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        myDrawerToggle = new ActionBarDrawerToggle(this, getViewBinding().drawerLayout, getViewBinding().toolBar, R.string.nav_open, R.string.nav_close);
        myDrawerToggle.syncState();
        getViewBinding().drawerLayout.addDrawerListener(myDrawerToggle);
        getViewBinding().navView.bringToFront();

        headerView();

    }

    private void headerView() {
        View headerView = getViewBinding().navView.getHeaderView(0);
        View settingsView = headerView.findViewById(R.id.settings);
        LinearLayout logOut = headerView.findViewById(R.id.layout_log_out);

        settingsView.setOnClickListener(view -> {
            Toast.makeText(this, "Hello Settings", Toast.LENGTH_SHORT).show();
        });

        logOut.setOnClickListener(view -> {
            Intent myIntent = new Intent(NewDashboardActivity.this, LogInActivity.class);
            startActivity(myIntent);
        });
    }

    private void sliderView() {
        ArrayList<SliderData> mySliderData = new ArrayList<>();
        mySliderData.add(new SliderData(R.drawable.business_woman, "Courses and Tools", "Business", "Zone"));
        mySliderData.add(new SliderData(R.drawable.card, "Total card control with", "Card", "Management"));
        mySliderData.add(new SliderData(R.drawable.events, "Never miss a thing", "Events"));
        mySliderData.add(new SliderData(R.drawable.piggy, "Best returns when you", "Save with", "Us"));
        mySliderData.add(new SliderData(R.drawable.map, "get the lowest cost on", "Overseas", "Transfers"));

        mySliderAdapter = new SliderAdapter(this, mySliderData);

        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        getViewBinding().slider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        // below method is used to
        // setAdapter to mySlider
        getViewBinding().slider.setSliderAdapter(mySliderAdapter);


        // below method is use to set
        // scroll time in seconds.
        getViewBinding().slider.setScrollTimeInSec(3);

        // to set it scrollable automatically
        // we use below method.
        getViewBinding().slider.setAutoCycle(true);

        // to start autoCycle below method is used.
        getViewBinding().slider.startAutoCycle();
    }

    private void initListener() {
     accountUpgrade();
     liveScores();
     events();
     billers();
    }

    private void billers() {
        getViewBinding().layoutBillers.setOnClickListener(view -> {
            Intent myIntent = new Intent(NewDashboardActivity.this, BillerListActivity.class);
            startActivity(myIntent);
        });
    }

    private void events() {
        getViewBinding().layoutEvents.setOnClickListener(view -> {
            Intent myIntent = new Intent(NewDashboardActivity.this, EventsActivity.class);
            startActivity(myIntent);
        });
    }

    private void liveScores() {
        getViewBinding().layoutLivescoresRelative.setOnClickListener(view -> {

            Intent myIntent = new Intent(NewDashboardActivity.this, LivescoresActivity.class);
            startActivity(myIntent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }

    private void accountUpgrade() {
        getViewBinding().layoutAccountRelative.setOnClickListener(view -> {

           dashBoardDialog();

        });
    }


}
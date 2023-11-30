package com.company.mulitchoice.dashboardPage;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.company.mulitchoice.BaseBindingActivity;
import com.company.mulitchoice.billersList.BillerListActivity;
import com.company.mulitchoice.R;
import com.company.mulitchoice.employeeDatabasePage.EmployeeDatabaseActivity;
import com.company.mulitchoice.employeeDatabasePage.SaveUsersActivity;
import com.company.mulitchoice.eventPage.EventsActivity;
import com.company.mulitchoice.superHerosDetails.SuperHeroDetailActivity;
import com.company.mulitchoice.databinding.ActivityDashboardBinding;
import com.company.mulitchoice.networkServices.SuperHerosAdapterInterface;
import com.company.mulitchoice.transactionDetails.TransactionActivity;
import com.company.mulitchoice.utilities.AppConstant;
import com.company.mulitchoice.utilities.Resource;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DashboardActivity extends BaseBindingActivity<ActivityDashboardBinding> implements SuperHerosAdapterInterface {
    RecyclerView.LayoutManager myLayoutManager;
    private SuperHerosAdapter myHerosAdapter;
    private SuperHerosViewModel myHerosViewModel;

    private SharedPreferences mySharedPreferences;
    private BottomSheetDialog myBottomSheetDialog;

    List<SuperHerosModel> mySuperHerosModel = new ArrayList<>();

    @Override
    protected ActivityDashboardBinding layoutInflater() {
        return ActivityDashboardBinding.inflate(getLayoutInflater());
    }

    @Override
    protected int layoutResources() {
        return R.layout.activity_dashboard;
    }


    @Override
    protected void inItComponent() {
        myHerosViewModel = ViewModelProviders.of(this).get(SuperHerosViewModel.class);
        myHerosViewModel.executeSuperHerosResponse();

        inItSubscribers();
        myRecyclerView();
        getDate();
        inItListener();

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        mySharedPreferences = getSharedPreferences(getString(R.string.myPref), Context.MODE_PRIVATE);
        String firstName =  mySharedPreferences.getString(AppConstant.FIRST_NAME, "");

        getViewBinding().tvWelcomeName.setText(" Welcome " + firstName);
    }

    private void getDate() {

        SimpleDateFormat sdfDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            sdfDate = new SimpleDateFormat("dd-MM-yyyy G");
        }
        String currentDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            currentDate = sdfDate.format(new Date());
        }
        getViewBinding().tvCurrentDayOfWeek.setText(currentDate);
    }

    private void myRecyclerView() {
        getViewBinding().recyclerView.setHasFixedSize(true);
        myLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        getViewBinding().recyclerView.setLayoutManager(myLayoutManager);
    }

    private void inItSubscribers() {
        myHerosViewModel.getHerosLiveData().observe(this, new Observer<Resource<List<SuperHerosModel>>>() {
            @Override
            public void onChanged(Resource<List<SuperHerosModel>> listResource) {
                switch(listResource.status){
                    case SUCCESS:
                        if(listResource != null){
                           mySuperHerosModel =  listResource.data;
                        }
                        myHerosAdapter = new SuperHerosAdapter(DashboardActivity.this, mySuperHerosModel, DashboardActivity.this);
                        getViewBinding().recyclerView.setAdapter(myHerosAdapter);
                        break;
                    case ERROR:
                        failureDialog(listResource.message);
                }
            }
        });
    }

    private void inItListener() {
        checkBiller();
        checkDatabase();
        checkEvent();
        eventList();
        rateUs();
    }

    private void eventList() {
        getViewBinding().layoutEventList.setOnClickListener(view -> {
            Intent myIntent = new Intent(DashboardActivity.this, TransactionActivity.class);
            startActivity(myIntent);
        });
    }

    private void checkEvent() {
        getViewBinding().layoutCreateEvent.setOnClickListener(view -> {
            Intent myIntent = new Intent(DashboardActivity.this, EventsActivity.class);
            startActivity(myIntent);
        });
    }

    private void rateUs() {
        getViewBinding().tvRateUs.setOnClickListener(view -> {

            showRating();

        });
    }



    private void checkDatabase() {
        getViewBinding().layoutDatabase.setOnClickListener(view -> {
            Intent myIntent = new Intent(DashboardActivity.this, EmployeeDatabaseActivity.class);
            startActivity(myIntent);
        });
    }

    private void checkBiller() {
        getViewBinding().layoutBillers.setOnClickListener(view -> {
            Intent myIntent = new Intent(DashboardActivity.this, BillerListActivity.class);
            startActivity(myIntent);
        });

    }
    @Override
    public void handleSuperHeroClickedItems(SuperHerosModel mySuperHerosModel) {
      Intent myIntent = new Intent(DashboardActivity.this, SuperHeroDetailActivity.class);
              myIntent.putExtra("hero_name", mySuperHerosModel.getName());
              myIntent.putExtra("real_name", mySuperHerosModel.getRealname());
              myIntent.putExtra("hero_bio", mySuperHerosModel.getBio());
              myIntent.putExtra("hero_creator", mySuperHerosModel.getCreatedby());
              myIntent.putExtra("hero_image", mySuperHerosModel.getImageurl());
              myIntent.putExtra("hero_first_appearance", mySuperHerosModel.getFirstappearance());
              myIntent.putExtra("hero_publisher", mySuperHerosModel.getPublisher());
             myIntent.putExtra("hero_team", mySuperHerosModel.getTeam());
             startActivity(myIntent);
    }

    private void showRating() {
        myBottomSheetDialog = new BottomSheetDialog(this);
        myBottomSheetDialog.setContentView(R.layout.rating_dialog_layout);

        Button rateButton = myBottomSheetDialog.findViewById(R.id.btn_rate);
        Button laterButton  = myBottomSheetDialog.findViewById(R.id.btn_later);
        RatingBar myRating = myBottomSheetDialog.findViewById(R.id.rating_bar);

        assert laterButton != null;
        laterButton.setOnClickListener(view -> {
            myBottomSheetDialog.dismiss();
        });

        assert rateButton != null;
        rateButton.setOnClickListener(view -> {
            myBottomSheetDialog.dismiss();
            String rating = "Rating ::" + myRating.getRating();
           String message = rating + "\n" + "thank you";
//           sendDialog(message);
        });
        myBottomSheetDialog.show();
    }

}
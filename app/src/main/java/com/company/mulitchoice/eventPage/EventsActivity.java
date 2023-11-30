package com.company.mulitchoice.eventPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.company.mulitchoice.BaseBindingActivity;
import com.company.mulitchoice.R;
import com.company.mulitchoice.dashboardPage.DashboardActivity;
import com.company.mulitchoice.dashboardPage.NewDashboardActivity;
import com.company.mulitchoice.databinding.ActivityEventsBinding;
import com.company.mulitchoice.liveScoresPage.LivescoresActivity;
import com.company.mulitchoice.logInPage.LogInActivity;
import com.company.mulitchoice.transactionDetails.TransactionActivity;
import com.company.mulitchoice.utilities.Dialogs;
import com.company.mulitchoice.utilities.Resource;

public class EventsActivity extends BaseBindingActivity<ActivityEventsBinding> {
    private EventViewModel myEventViewModel;

    @Override
    protected ActivityEventsBinding layoutInflater() {
        return ActivityEventsBinding.inflate(getLayoutInflater());
    }

    @Override
    protected int layoutResources() {
        return R.layout.activity_events;
    }

    @Override
    protected void inItComponent() {

        myEventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        inItSubscriber();
        inItListener();


    }

    private void inItListener() {
        submitButton();
        arrowBack();
    }

    private void arrowBack() {
        getViewBinding().arrowBack.setOnClickListener(view -> {
            Intent myIntent = new Intent(EventsActivity.this, NewDashboardActivity.class);
            startActivity(myIntent);
        });
    }

    private void submitButton() {
        getViewBinding().btnSubmit.setOnClickListener(view -> {
            String userUsername = getViewBinding().etTitle.getText().toString();
            String userPassword = getViewBinding().etDescription.getText().toString();

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(EventsActivity.this);
            String retrievedValueForCustomerId = sharedPreferences.getString("customerId", "");

            myEventViewModel.executeEventRequest(userUsername, userPassword, retrievedValueForCustomerId);

        });
    }

    private void inItSubscriber() {
        myEventViewModel.getMyEventLiveData().observe(this, new Observer<Resource<EventResponseModel>>() {
            @Override
            public void onChanged(Resource<EventResponseModel> eventResponseModelResource) {
                switch (eventResponseModelResource.status) {
                    case LOADING:
                        Dialogs.loadingDialog(EventsActivity.this);
                        getViewBinding().progressBar.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        getViewBinding().progressBar.setVisibility(View.GONE);
                        if (eventResponseModelResource != null) {
                            String successMessage = eventResponseModelResource.data.getMessage();
                           Dialogs.SuccessDialog(EventsActivity.this, successMessage);

                        }
                        break;

                    case ERROR:
                        getViewBinding().progressBar.setVisibility(View.GONE);
                        String errorMessage = eventResponseModelResource.message;
                        failureDialog(errorMessage);
                }
            }
        });
    }

//    private void textListener() {
//
//        getViewBinding().etTitle.addTextChangedListener(myTextWatcher);
//        getViewBinding().etDescription.addTextChangedListener(myTextWatcher);
//
//    }
//
//    private TextWatcher myTextWatcher = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            String title = getViewBinding().etTitle.getText().toString().trim();
//            String description = getViewBinding().etDescription.getText().toString().trim();
//
//            getViewBinding().btnSubmit.setEnabled(!title.isEmpty() && !description.isEmpty());
//        }
//
//        @Override
//        public void afterTextChanged(Editable editable) {
//
//        }
//    };


}
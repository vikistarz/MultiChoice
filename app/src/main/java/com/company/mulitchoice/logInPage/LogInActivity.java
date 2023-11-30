package com.company.mulitchoice.logInPage;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.company.mulitchoice.BaseBindingActivity;
import com.company.mulitchoice.dashboardPage.DashboardActivity;
import com.company.mulitchoice.R;
import com.company.mulitchoice.dashboardPage.NewDashboardActivity;
import com.company.mulitchoice.databinding.ActivityLogInBinding;
import com.company.mulitchoice.utilities.AppConstant;
import com.company.mulitchoice.utilities.Dialogs;
import com.company.mulitchoice.utilities.Resource;

public class LogInActivity extends BaseBindingActivity<ActivityLogInBinding> {

//    String userName, passWord;
    public static String PREF_NAME = "myPrefsFile";
    private LogInViewModel myLogInViewModel;
    private SharedPreferences mySharedPreferences;

    @Override
    protected ActivityLogInBinding layoutInflater() {
        return ActivityLogInBinding.inflate(getLayoutInflater());
    }

    @Override
    protected int layoutResources() {
        return R.layout.activity_log_in;
    }

    @Override
    protected void inItComponent() {

        myLogInViewModel = new ViewModelProvider(this).get(LogInViewModel.class);

        inItSubscriber();
        inItListener();
        textListener();
    }


    private void inItListener() {
        logInButton();
    }

    private void logInButton() {
        getViewBinding().btnLogIn.setOnClickListener(view -> {
            String userUsername = getViewBinding().etUsername.getText().toString().trim();
            String userPassword = getViewBinding().etPassword.getText().toString().trim();

            if (validateUsername() && validatePassword()) {
                myLogInViewModel.executeLogInRequest(userUsername, userPassword);
            }
        });
    }

    private void inItSubscriber() {
        myLogInViewModel.getMyLogInLiveData().observe(this, new Observer<Resource<LogInResponseModel>>() {
            @Override
            public void onChanged(Resource<LogInResponseModel> logInResponseModelResource) {

                switch (logInResponseModelResource.status) {
                    case LOADING:
//                        getViewBinding().progressBar.setVisibility(View.VISIBLE);
                          Dialogs.loadingDialog(LogInActivity.this);

                        break;

                    case SUCCESS:
                        if (logInResponseModelResource != null) {
                            SharedPreferences.Editor dd = PreferenceManager.getDefaultSharedPreferences(LogInActivity.this).edit();
                            dd.putString("customerId", logInResponseModelResource.data.getResult().getId());
                            dd.apply();

                            mySharedPreferences = getSharedPreferences(LogInActivity.PREF_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor myEditor = mySharedPreferences.edit();

                            myEditor.putBoolean("hasLoggedIn", true);
                            myEditor.commit();

//                            userName = logInResponseModelResource.data.getResult().getUsername();
//                            passWord = logInResponseModelResource.data.getResult().getPassword();

                            String successMessage = logInResponseModelResource.data.getMessage();
                            Dialogs.SuccessDialog(LogInActivity.this, successMessage);
                            Intent myIntent = new Intent(LogInActivity.this, NewDashboardActivity.class);
                            startActivity(myIntent);
                            finish();
                        }
                        break;

                    case ERROR:
                        String errorMessage = logInResponseModelResource.message;
                        failureDialog(errorMessage);

                }

            }
        });
    }

    private void textListener() {


        getViewBinding().etUsername.addTextChangedListener(myTextWatcher);
        getViewBinding().etPassword.addTextChangedListener(myTextWatcher);

    }

    private final TextWatcher myTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String userUsername = getViewBinding().etUsername.getText().toString().trim();
            String userPassword = getViewBinding().etPassword.getText().toString().trim();

            getViewBinding().btnLogIn.setEnabled(!userUsername.isEmpty() && !userPassword.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private boolean validateUsername() {
        mySharedPreferences = getSharedPreferences(getString(R.string.myPref), Context.MODE_PRIVATE);
        String loginUsername = mySharedPreferences.getString(AppConstant.USERNAME, " ");
        String retrievalUsername = getViewBinding().etUsername.getText().toString().trim();


        if (retrievalUsername.isEmpty()) {
            getViewBinding().etUsername.setError("Can't be empty");
            return false;
        }
        if (retrievalUsername.length() < 4) {
            getViewBinding().etUsername.setError("username too short");
            return false;
        }
//        else if (!retrievalUsername.equals(loginUsername)) {
//            getViewBinding().etUsername.setError("Invalid Username");
//            return false;
//        }
        return true;
    }

    private boolean validatePassword() {
        mySharedPreferences = getSharedPreferences(getString(R.string.myPref), Context.MODE_PRIVATE);
        String loginPassword = mySharedPreferences.getString(AppConstant.PASSWORD, " ");
        String retrievalPassword = getViewBinding().etPassword.getText().toString().trim();


        if (retrievalPassword.isEmpty()) {
            getViewBinding().etPassword.setError("Can't be empty");
            return false;
        } else if (retrievalPassword.length() < 6) {
            getViewBinding().etPassword.setError("password too short");
            return false;
        }
//        } else if (!retrievalPassword.equals(loginPassword)) {
//            getViewBinding().etPassword.setError("Invalid Password");
//            return false;
//        }
        return true;

    }


}
package com.company.mulitchoice.signUpPage;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.company.mulitchoice.BaseBindingActivity;
import com.company.mulitchoice.logInPage.LogInActivity;
import com.company.mulitchoice.R;
import com.company.mulitchoice.databinding.ActivitySignUpBinding;
import com.company.mulitchoice.utilities.AppConstant;
import com.company.mulitchoice.utilities.Dialogs;
import com.company.mulitchoice.utilities.Resource;

public class SignUpActivity extends BaseBindingActivity<ActivitySignUpBinding> {


    private SharedPreferences mySharedPreference;
    private  SharedPreferences.Editor myEditor;
    private SignUpViewModel mySignUpViewModel;
    String firstName, lastName, phoneNumber, userName;


    @Override
    protected ActivitySignUpBinding layoutInflater() {
        return ActivitySignUpBinding.inflate(getLayoutInflater());
    }

    @Override
    protected int layoutResources() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void inItComponent() {

        mySignUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        mySharedPreference = getSharedPreferences(getString(R.string.myPref), Context.MODE_PRIVATE);

        inItSubscriber();
        inItListener();
        textListener();
    }



    @Override
    protected void onPause() {
        super.onPause();
        myEditor = mySharedPreference.edit();
        myEditor.putString(AppConstant.FIRST_NAME, firstName);
        myEditor.putString(AppConstant.LAST_NAME, lastName);
        myEditor.putString(AppConstant.PHONE, phoneNumber);
        myEditor.putString(AppConstant.USERNAME, userName);
    }

    public void inItListener() {

        signUpButton();
        backToLogin();
    }

    private void backToLogin() {
        getViewBinding().tvBackToLogIn.setOnClickListener(view -> {
            Intent myIntent = new Intent(SignUpActivity.this, LogInActivity.class);
            startActivity(myIntent);
        });
    }

    private void signUpButton() {
        getViewBinding().btnSignUp.setOnClickListener(view -> {

            String userFirstName = getViewBinding().etFirstName.getText().toString().trim();
            String userLastName = getViewBinding().etLastName.getText().toString().trim();
            String userPhoneNumber = getViewBinding().etPhone.getText().toString().trim();
            String userUsername = getViewBinding().etUsername.getText().toString().trim();
            String userPassword = getViewBinding().etPassword.getText().toString().trim();


            if(validateFirstName() && validateLastName() && validatePhoneNumber() && validateUsername() && validatePassword()){

                mySignUpViewModel.executeSignUpRequest(userFirstName, userLastName, userPhoneNumber,
                        userUsername, userPassword);
            }
        });
    }

    private void inItSubscriber() {

        mySignUpViewModel.getMySignUpLiveData().observe(this, new Observer<Resource<SignUpResponseModel>>() {
            @Override
            public void onChanged(Resource<SignUpResponseModel> signUpResponseModelResource) {

                switch (signUpResponseModelResource.status) {
                    case LOADING:
                        Dialogs.loadingDialog(SignUpActivity.this);
                        getViewBinding().progressBar.setVisibility(View.VISIBLE);
                        break;

                    case SUCCESS:
                        getViewBinding().progressBar.setVisibility(View.GONE);

                        if (signUpResponseModelResource.data != null) {

                            firstName = signUpResponseModelResource.data.getResult().getFirstName();
                            lastName = signUpResponseModelResource.data.getResult().getLastName();
                            phoneNumber = signUpResponseModelResource.data.getResult().getPhoneNumber();
                            userName = signUpResponseModelResource.data.getResult().getUsername();

                            String successMessage = signUpResponseModelResource.data.getMessage();
                            Dialogs.SuccessDialog(SignUpActivity.this, successMessage);

                            Intent myIntent = new Intent(SignUpActivity.this, LogInActivity.class);
                            startActivity(myIntent);
                        }
                        break;

                    case ERROR:
                        getViewBinding().progressBar.setVisibility(View.GONE);
                        String errorMessage = signUpResponseModelResource.message;
                        failureDialog(errorMessage);
                }
            }
        });

    }


    private void textListener() {

        getViewBinding().etFirstName.addTextChangedListener(myTextWatcher);
        getViewBinding().etLastName.addTextChangedListener(myTextWatcher);
        getViewBinding().etPhone.addTextChangedListener(myTextWatcher);
        getViewBinding().etUsername.addTextChangedListener(myTextWatcher);
        getViewBinding().etPassword.addTextChangedListener(myTextWatcher);

    }

    private TextWatcher myTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String userFirstName = getViewBinding().etFirstName.getText().toString().trim();
            String userLastName = getViewBinding().etLastName.getText().toString().trim();
            String userPhoneNumber = getViewBinding().etPhone.getText().toString().trim();
            String userUsername = getViewBinding().etUsername.getText().toString().trim();
            String userPassword = getViewBinding().etPassword.getText().toString().trim();


            getViewBinding().btnSignUp.setEnabled(!userFirstName.isEmpty() && !userLastName.isEmpty()
            && !userPhoneNumber.isEmpty() && !userUsername.isEmpty() && !userPassword.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };




    private boolean validateFirstName() {
        String retrievalParentName = getViewBinding().etFirstName.getText().toString().trim();
        String acceptableAlphabets = "^([a-zA-Z]*)$";

        if (retrievalParentName.isEmpty()) {
            getViewBinding().etFirstName.setError("Can't be empty");
            return false;
        } else if (retrievalParentName.length() < 3) {
            getViewBinding().etFirstName.setError("Name too short");
            return false;
        } else if (!retrievalParentName.matches(acceptableAlphabets)) {
            getViewBinding().etFirstName.setError("Must only be in alphabets");
            return false;
        }
        return true;
    }

    private boolean validateLastName() {
        String retrievalLastName = getViewBinding().etLastName.getText().toString().trim();
        String acceptableAlphabets = "^([a-zA-Z]*)$";

        if (retrievalLastName.isEmpty()) {
            getViewBinding().etLastName.setError("Can't be empty");
            return false;
        } else if (retrievalLastName.length() < 3) {
            getViewBinding().etLastName.setError("Name too short");
            return false;
        } else if (!retrievalLastName.matches(acceptableAlphabets)) {
            getViewBinding().etLastName.setError("Must only be in alphabets");
            return false;
        }
        return true;
    }

    private boolean validateUsername() {
        String retrievalUsername = getViewBinding().etUsername.getText().toString().trim();
        String alphaNumericNumbers = "^([a-zA-Z][a-zA-Z$+._\\d]*)$";

        if (retrievalUsername.isEmpty()) {
            getViewBinding().etUsername.setError("Can't be empty");
            return false;
        } else if (retrievalUsername.length() < 4) {
            getViewBinding().etUsername.setError("username too short");
            return false;
        } else if (!retrievalUsername.matches(alphaNumericNumbers)) {
            getViewBinding().etUsername.setError("username too weak, add number");
            return false;
        }
        return true;
    }

    private boolean validatePhoneNumber() {
        String retrievalPhoneNumber = getViewBinding().etPhone.getText().toString().trim();
        String acceptableNumbers = "^([0-9]*)$";

        if (retrievalPhoneNumber.isEmpty()) {
            getViewBinding().etPhone.setError("Can't be empty");
            return false;
        } else if (retrievalPhoneNumber.length() < 11) {
            getViewBinding().etPhone.setError("Number typed invalid");
            return false;
        } else if (retrievalPhoneNumber.length() > 11) {
            getViewBinding().etPhone.setError("Number typed invalid");
            return false;
        } else if (!retrievalPhoneNumber.matches(acceptableNumbers)) {
            getViewBinding().etPhone.setError("must only be numbers");
            return false;
        }
        return true;
    }

    private boolean validatePassword() {

        String retrievalPassword = getViewBinding().etPassword.getText().toString().trim();
        String acceptablePassword = "^([a-zA-Z@#?$+._\\d]*)$";

        if (retrievalPassword.isEmpty()) {
            getViewBinding().etPassword.setError("Can't be empty");
            return false;
        } else if (retrievalPassword.length() < 6) {
            getViewBinding().etPassword.setError("password too short");
            return false;
        } else if (!retrievalPassword.matches(acceptablePassword)) {
            getViewBinding().etPassword.setError("too weak, requires letter, number or symbol");
            return false;
        }
        return true;
    }

}
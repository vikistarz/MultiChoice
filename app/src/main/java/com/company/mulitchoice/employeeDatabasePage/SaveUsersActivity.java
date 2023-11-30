package com.company.mulitchoice.employeeDatabasePage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.company.mulitchoice.BaseBindingActivity;
import com.company.mulitchoice.R;
import com.company.mulitchoice.databinding.ActivitySaveUsersBinding;
import com.company.mulitchoice.utilities.AppConstant;

public class SaveUsersActivity extends BaseBindingActivity<ActivitySaveUsersBinding> {



    @Override
    protected ActivitySaveUsersBinding layoutInflater() {
        return ActivitySaveUsersBinding.inflate(getLayoutInflater());
    }

    @Override
    protected int layoutResources() {
        return R.layout.activity_save_users;
    }

    @Override
    protected void inItComponent() {
        inItView();
        inItListener();
        textListener();
    }

    private void inItView() {

        // below line is to get intent as we
        // are getting data via an intent.
        Intent myIntent = getIntent();
        if(myIntent.hasExtra(AppConstant.EXTRA_ID)){
            // if we get id for our data then we are
            // setting values to our edit text fields.
            getViewBinding().etFullName.setText(myIntent.getStringExtra(AppConstant.EXTRA_EMPLOYEE_FULL_NAME));
            getViewBinding().etJobDescription.setText(myIntent.getStringExtra(AppConstant.EXTRA_JOB_DESCRIPTION));
            getViewBinding().etYearOfEntry.setText(myIntent.getStringExtra(AppConstant.EXTRA_YEAR_OF_ENTRY));
        }
    }

    private void inItListener() {
        saveButton();
        backButton();
    }

    private void backButton() {
        getViewBinding().arrowBack.setOnClickListener(view -> {
            Intent myIntent = new Intent(SaveUsersActivity.this, EmployeeDatabaseActivity.class);
            startActivity(myIntent);
        });
    }

    private void saveButton() {
        getViewBinding().btnSaveEmployee.setOnClickListener(view -> {

            if(validateFullName() && validateJobDescription() && validateYearOfEntry()){
                saveUserInput();
            }

        });
    }

    private void saveUserInput() {
        String userFullName = getViewBinding().etFullName.getText().toString();
        String userJobDescription = getViewBinding().etJobDescription.getText().toString();
        String userEntryYear = getViewBinding().etYearOfEntry.getText().toString().trim();



        Intent myIntent = getIntent();
        myIntent.putExtra(AppConstant.EXTRA_EMPLOYEE_FULL_NAME, userFullName);
        myIntent.putExtra(AppConstant.EXTRA_JOB_DESCRIPTION, userJobDescription);
        myIntent.putExtra(AppConstant.EXTRA_YEAR_OF_ENTRY, userEntryYear);
        int id = getIntent().getIntExtra(AppConstant.EXTRA_ID, -1);
        if(id != -1){
            myIntent.putExtra(AppConstant.EXTRA_ID, id);
        }
        setResult(RESULT_OK, myIntent);
        Toast.makeText(this, "Employees data has been saved to the  Database. ", Toast.LENGTH_LONG).show();
         finish();
    }

    private boolean validateFullName() {
        String retrievalFullName = getViewBinding().etFullName.getText().toString();
        String acceptableAlphabets = "^([a-zA-Z]*)$";

         if (retrievalFullName.length() < 5) {
            getViewBinding().etFullName.setError("Name too short");
            return false;
        }
//         else if (!retrievalFullName.matches(acceptableAlphabets)) {
//            getViewBinding().etFullName.setError("Must only be in alphabets");
//            return false;
//        }
        return true;
    }

    private boolean validateJobDescription() {
        String retrievalJobDescription = getViewBinding().etJobDescription.getText().toString().trim();
        String acceptableAlphabets = "^([a-zA-Z]*)$";

         if (retrievalJobDescription.length() < 2) {
            getViewBinding().etJobDescription.setError("Too short");
            return false;
        }
//         else if (!retrievalJobDescription.matches(acceptableAlphabets)) {
//            getViewBinding().etJobDescription.setError("Must only be in alphabets");
//            return false;
//        }
        return true;
    }



    private boolean validateYearOfEntry() {
        String retrievalYearOfEntry = getViewBinding().etYearOfEntry.getText().toString().trim();
        String acceptableNumbers = "^([0-9]*)$";

        if (retrievalYearOfEntry.length() < 4) {
            getViewBinding().etYearOfEntry.setError("Number typed invalid");
            return false;
        } else if (!retrievalYearOfEntry.matches(acceptableNumbers)) {
            getViewBinding().etYearOfEntry.setError("must only be numbers");
            return false;
        }
        return true;
    }

    private void textListener() {
        getViewBinding().etFullName.addTextChangedListener(myTextWatcher);
        getViewBinding().etJobDescription.addTextChangedListener(myTextWatcher);
        getViewBinding().etYearOfEntry.addTextChangedListener(myTextWatcher);
    }

    private TextWatcher myTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            buttonEnabled();
        }

        @Override
        public void afterTextChanged(Editable editable) {
            buttonEnabled();
        }
    };

    private void buttonEnabled() {

        String userFullName = getViewBinding().etFullName.getText().toString();
        String userJobDescription = getViewBinding().etJobDescription.getText().toString();
        String userEntryYear = getViewBinding().etYearOfEntry.getText().toString();

        getViewBinding().btnSaveEmployee.setEnabled(!userFullName.isEmpty() && !userJobDescription.isEmpty()
                && !userEntryYear.isEmpty());
    }

}
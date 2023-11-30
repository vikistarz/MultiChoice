package com.company.mulitchoice.employeeDatabasePage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.company.mulitchoice.BaseBindingActivity;
import com.company.mulitchoice.R;
import com.company.mulitchoice.billersList.BillersResponseModel;
import com.company.mulitchoice.dashboardPage.DashboardActivity;
import com.company.mulitchoice.databinding.ActivityEmployeeDatabaseBinding;
import com.company.mulitchoice.networkServices.EmployeeAdapterInterface;
import com.company.mulitchoice.utilities.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDatabaseActivity extends BaseBindingActivity<ActivityEmployeeDatabaseBinding> implements EmployeeAdapterInterface {

    private EmployeeViewModel myEmployeeViewModel;
    private EmployeeAdapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;
    List<EmployeeModel> myEmployeeModel = new ArrayList<>();


    private static final int ADD_Employee_REQUEST = 4;
    private static final int EDIT_Employee_REQUEST = 5;

    @Override
    protected ActivityEmployeeDatabaseBinding layoutInflater() {
        return ActivityEmployeeDatabaseBinding.inflate(getLayoutInflater());
    }

    @Override
    protected int layoutResources() {
        return R.layout.activity_employee_database;
    }

    @Override
    protected void inItComponent() {
      myEmployeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);
      inItSubscribers();
        myRecyclerView();
      inItListener();
     searchView();
    }

    private void searchView() {
        getViewBinding().searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filterBillers(newText);
                return false;
            }
        });
    }

    private void filterBillers(String newText) {

        List<EmployeeModel> myFilteredList = new ArrayList<>();
//        run a for loop to compare elements
        for(EmployeeModel items : myEmployeeModel){
            if(items.getEmployeeFullNames().toLowerCase().contains(newText.toLowerCase())){
                myFilteredList.add(items);
            }
        }
        if(myFilteredList.isEmpty()){
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }
        else{
            myAdapter.filterList(myFilteredList);
        }
    }

    private void myRecyclerView() {
        getViewBinding().recyclerView.setHasFixedSize(true);
        myLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        getViewBinding().recyclerView.setLayoutManager(myLayoutManager);
    }

    private void inItSubscribers() {

        myEmployeeViewModel.getAllEmployeeModelLiveData().observe(this, employeeModels -> {
            if(employeeModels != null){
                    myAdapter = new EmployeeAdapter(employeeModels, EmployeeDatabaseActivity.this, EmployeeDatabaseActivity.this);
                    getViewBinding().recyclerView.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();
                }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                myEmployeeViewModel.delete(myAdapter.getEmployeeAt(viewHolder.getAdapterPosition()));
                Toast.makeText(EmployeeDatabaseActivity.this, "Course deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(getViewBinding().recyclerView);
    }

    private void inItListener() {
        backButton();
        floatingButton();
    }

    private void floatingButton() {
        getViewBinding().btnFloating.setOnClickListener(view -> {
            Intent myIntent = new Intent(EmployeeDatabaseActivity.this, SaveUsersActivity.class);
            startActivityForResult(myIntent, ADD_Employee_REQUEST);
        });
    }

    private void backButton() {
        getViewBinding().arrowBack.setOnClickListener(view -> {
            Intent myIntent = new Intent(EmployeeDatabaseActivity.this, DashboardActivity.class);
            startActivity(myIntent);
        });
    }

    @Override
    public void handleEmployeeClickedItems(EmployeeModel myEmployeeModel) {

                Intent myIntent = new Intent(EmployeeDatabaseActivity.this, SaveUsersActivity.class);
                myIntent.putExtra(AppConstant.EXTRA_ID, myEmployeeModel.getId());
                myIntent.putExtra(AppConstant.EXTRA_EMPLOYEE_FULL_NAME, myEmployeeModel.getEmployeeFullNames());
                myIntent.putExtra(AppConstant.EXTRA_JOB_DESCRIPTION, myEmployeeModel.getJobDescription());
                myIntent.putExtra(AppConstant.EXTRA_YEAR_OF_ENTRY, myEmployeeModel.getYearOfEntry());

                // below line is to start a new activity and
                // adding a edit course constant.
                startActivityForResult(myIntent, EDIT_Employee_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_Employee_REQUEST && resultCode == RESULT_OK) {
            String fullName = data.getStringExtra(AppConstant.EXTRA_EMPLOYEE_FULL_NAME);
            String jobDescription = data.getStringExtra(AppConstant.EXTRA_JOB_DESCRIPTION);
            String yearOfEntry = data.getStringExtra(AppConstant.EXTRA_YEAR_OF_ENTRY);
            EmployeeModel myModel = new EmployeeModel(fullName, jobDescription, yearOfEntry);
            myEmployeeViewModel.insert(myModel);
            Toast.makeText(this, "Employee saved", Toast.LENGTH_SHORT).show();


        }
        else if (requestCode == EDIT_Employee_REQUEST && resultCode == RESULT_OK) {


            int id = data.getIntExtra(AppConstant.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Employee data can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String fullName = data.getStringExtra(AppConstant.EXTRA_EMPLOYEE_FULL_NAME);
            String jobDescription = data.getStringExtra(AppConstant.EXTRA_JOB_DESCRIPTION);
            String yearOfEntry = data.getStringExtra(AppConstant.EXTRA_YEAR_OF_ENTRY);
            EmployeeModel model = new EmployeeModel(fullName, jobDescription, yearOfEntry);
            model.setId(id);
            myEmployeeViewModel.update(model);
            Toast.makeText(this, "Employee data updated", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Employee Data not saved", Toast.LENGTH_SHORT).show();
        }
    }
}
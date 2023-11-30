package com.company.mulitchoice.transactionDetails;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;

import com.company.mulitchoice.BaseBindingActivity;
import com.company.mulitchoice.R;
import com.company.mulitchoice.databinding.ActivityTransactionBinding;
import com.company.mulitchoice.eventPage.EventsActivity;
import com.company.mulitchoice.networkServices.EventsAdapterInterface;
import com.company.mulitchoice.utilities.Dialogs;
import com.company.mulitchoice.utilities.Resource;

import java.util.ArrayList;
import java.util.List;

public class TransactionActivity extends BaseBindingActivity<ActivityTransactionBinding> implements EventsAdapterInterface {

    private TransactionDetailsViewModel myTransactionViewModel;
    private TransactionAdapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;

    private List<Result> myEventLog = new ArrayList<>();

    @Override
    protected ActivityTransactionBinding layoutInflater() {
        return ActivityTransactionBinding.inflate(getLayoutInflater());
    }

    @Override
    protected int layoutResources() {
        return R.layout.activity_transaction;
    }

    @Override
    protected void inItComponent() {

        myTransactionViewModel = ViewModelProviders.of(this).get(TransactionDetailsViewModel.class);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(TransactionActivity.this);
        String retrievedValueForCustomerId = sharedPreferences.getString("customerId", "");

        myTransactionViewModel.executeGetAllEvents(retrievedValueForCustomerId);
        inItSubscriber();
        recyclerView();

    }

//    private void initListener() {
//        getViewBinding().arrowBack.setOnClickListener(view -> {
//            BillerListActivity.super.onBackPressed();
//        });
//
//        getViewBinding().searchBar.setOnClickListener(view -> {
//            getViewBinding().searchView.setVisibility(View.VISIBLE);
//            searchView();
//        });
//    }
//
//    private void searchView() {
//        getViewBinding().searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                filterBillers(newText);
//                return false;
//            }
//        });
//    }
//
//    private void filterBillers(String newText) {
//
//        List<BillersResponseModel> myFilteredList = new ArrayList<>();
////        run a for loop to compare elements
//        for(BillersResponseModel items : myBillerListModel){
//            if(items.getName().toLowerCase().contains(newText.toLowerCase())){
//                myFilteredList.add(items);
//            }
//        }
//        if(myFilteredList.isEmpty()){
//            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            myAdapter.filterList(myFilteredList);
//        }
//    }


    private void recyclerView() {
        getViewBinding().recyclerView.setHasFixedSize(true);
        myLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        getViewBinding().recyclerView.setLayoutManager(myLayoutManager);
    }

    private void inItSubscriber() {

        myTransactionViewModel.getMyEventLogLiveData().observe(this, new Observer<Resource<EventLogModels>>() {
            @Override
            public void onChanged(Resource<EventLogModels> eventLogModelsResource) {
                switch (eventLogModelsResource.status) {

                    case LOADING:
                        Dialogs.loadingDialog(TransactionActivity.this);
                        getViewBinding().progressBar.setVisibility(View.VISIBLE);
                        break;

                    case SUCCESS:
                        getViewBinding().progressBar.setVisibility(View.GONE);
                        if (eventLogModelsResource.data != null) {
                            myEventLog = eventLogModelsResource.data.getResult();
                        }
                        myAdapter = new TransactionAdapter(TransactionActivity.this, myEventLog, TransactionActivity.this);
                        getViewBinding().recyclerView.setAdapter(myAdapter);
                        break;

                    case ERROR:
                        getViewBinding().progressBar.setVisibility(View.GONE);
                        String errorMessage = eventLogModelsResource.message;
                        failureDialog(errorMessage);
                        break;
                }

            }
        });
    }

    @Override
    public void handleBillersClickedItems(Result myResult) {

    }
}
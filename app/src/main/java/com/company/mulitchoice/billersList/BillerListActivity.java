package com.company.mulitchoice.billersList;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.company.mulitchoice.BaseBindingActivity;
import com.company.mulitchoice.R;
import com.company.mulitchoice.databinding.ActivityBillersBinding;
import com.company.mulitchoice.networkServices.BillerAdapterInterface;
import com.company.mulitchoice.utilities.Dialogs;
import com.company.mulitchoice.utilities.Resource;

import java.util.ArrayList;
import java.util.List;

public class BillerListActivity extends BaseBindingActivity<ActivityBillersBinding> implements BillerAdapterInterface {

    private BillerListViewModel myBillerListViewModel;
    private BillerListAdapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;

    private List<BillersResponseModel> myBillerListModel = new ArrayList<>();
    @Override
    protected ActivityBillersBinding layoutInflater() {
        return ActivityBillersBinding.inflate(getLayoutInflater());
    }

    @Override
    protected int layoutResources() {
        return R.layout.activity_billers;
    }
    @Override
    protected void inItComponent() {

        myBillerListViewModel = ViewModelProviders.of(this).get(BillerListViewModel.class);
        myBillerListViewModel.executeBillersListResponse("2s");
        inItSubscriber();
        recyclerView();
        initListener();

    }

    private void initListener() {
        getViewBinding().arrowBack.setOnClickListener(view -> {
               BillerListActivity.super.onBackPressed();
        });

        getViewBinding().searchBar.setOnClickListener(view -> {
            getViewBinding().searchView.setVisibility(View.VISIBLE);
            getViewBinding().tvCancel.setVisibility(View.VISIBLE);
            searchView();
        });

        getViewBinding().tvCancel.setOnClickListener(view -> {
            getViewBinding().searchView.setVisibility(View.GONE);
            getViewBinding().tvCancel.setVisibility(View.GONE);
        });

    }

    private void searchView() {
        getViewBinding().searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                String query = getViewBinding().searchView.getQuery().toString();

                if(!query.isEmpty()){
                    getViewBinding().tvCancel.setVisibility(View.GONE);
                }
                else if(query.isEmpty()){
                    getViewBinding().tvCancel.setVisibility(View.VISIBLE);
                }



                filterBillers(newText);
                return false;
            }
        });
    }

    private void filterBillers(String newText) {

        List<BillersResponseModel> myFilteredList = new ArrayList<>();
//        run a for loop to compare elements
        for(BillersResponseModel items : myBillerListModel){
            if(items.getName().toLowerCase().contains(newText.toLowerCase())){
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



    private void recyclerView() {
        getViewBinding().recyclerView.setHasFixedSize(true);
        myLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        getViewBinding().recyclerView.setLayoutManager(myLayoutManager);
    }

    private void inItSubscriber() {
        myBillerListViewModel.getMyBillerResponseLiveData().observe(this, new Observer<Resource<List<BillersResponseModel>>>() {
            @Override
            public void onChanged(Resource<List<BillersResponseModel>> billerListResponseModelResource) {
                switch(billerListResponseModelResource.status){
                    case LOADING:
                        getViewBinding().spinKit2.setVisibility(View.VISIBLE);
                        break;

                    case SUCCESS:

                        getViewBinding().spinKit2.setVisibility(View.GONE);
                        if(billerListResponseModelResource.data != null){
                            myBillerListModel = billerListResponseModelResource.data;
                        }
                        myAdapter = new BillerListAdapter(BillerListActivity.this, myBillerListModel, BillerListActivity.this);
                        getViewBinding().recyclerView.setAdapter(myAdapter);
                        break;

                    case ERROR:
                        getViewBinding().spinKit2.setVisibility(View.GONE);
                        String errorMessage = billerListResponseModelResource.message;
                        failureDialog(errorMessage);
                        break;
                }

            }
        });
    }

    @Override
    public void handleBillersClickedItems(BillersResponseModel myBillerListRequestModel) {

    }


}
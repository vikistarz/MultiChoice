package com.company.mulitchoice.transactionDetails;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.company.mulitchoice.networkServices.WebService;
import com.company.mulitchoice.retrofitApi.LogIn_SignUpApi;
import com.company.mulitchoice.utilities.Resource;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionDetailsViewModel extends ViewModel {

    public static final String TAG = "EventsViewModel";

    private MutableLiveData<Resource<EventLogModels>> myEventLogLiveData;

    public TransactionDetailsViewModel(){
        myEventLogLiveData = new MutableLiveData<>();
    }
    public MutableLiveData<Resource<EventLogModels>> getMyEventLogLiveData(){
        return myEventLogLiveData;
    }

    public void executeGetAllEvents(String value){
        WebService myWebservice = LogIn_SignUpApi.getMyRetrofit().create(WebService.class);
        Call<EventLogModels> myCall = myWebservice.performGetAllEvents(value);
        myEventLogLiveData.postValue(Resource.loading());
        myCall.enqueue(new Callback<EventLogModels>() {
            @Override
            public void onResponse(Call<EventLogModels> call, Response<EventLogModels> response) {

                if(response.isSuccessful() && response.body() != null && response != null){
                    myEventLogLiveData.postValue(Resource.success(response.body()));
                }
                else{
                    myEventLogLiveData.postValue(Resource.error(response.message()));
                    try{
                        if(response.errorBody() != null){
                            myEventLogLiveData.postValue(Resource.error(response.errorBody().string()));
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }

            @Override
            public void onFailure(Call<EventLogModels> call, Throwable t) {
                myEventLogLiveData.postValue(Resource.error(t.getMessage()));
            }
        });
    }

}

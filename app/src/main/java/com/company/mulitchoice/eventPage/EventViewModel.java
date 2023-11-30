package com.company.mulitchoice.eventPage;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.company.mulitchoice.networkServices.WebService;
import com.company.mulitchoice.retrofitApi.LogIn_SignUpApi;
import com.company.mulitchoice.utilities.Resource;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventViewModel extends ViewModel {

    public static final String TAG = "EventsViewModel";

    private MutableLiveData<Resource<EventResponseModel>> myEventLiveData;

    public EventViewModel(){
        myEventLiveData = new MutableLiveData<>();
    }
    public MutableLiveData<Resource<EventResponseModel>> getMyEventLiveData(){
        return myEventLiveData;
    }

    public void executeEventRequest(String title, String description, String customerId){
        EventRequestModel myEventRequestModel = new EventRequestModel();
        myEventRequestModel.setTitle(title);
        myEventRequestModel.setDescription(description);
        myEventRequestModel.setCustomerId(customerId);
        Log.d(TAG, "validate Event" + myEventRequestModel);


        WebService myWebservice = LogIn_SignUpApi.getMyRetrofit().create(WebService.class);
        Call<EventResponseModel> myCall = myWebservice.performCreateEvent(myEventRequestModel);
        myEventLiveData.postValue(Resource.loading());
        myCall.enqueue(new Callback<EventResponseModel>() {
            @Override
            public void onResponse(Call<EventResponseModel> call, Response<EventResponseModel> response) {

                if(response.isSuccessful() && response != null && response.body() != null){
                    myEventLiveData.postValue(Resource.success(response.body()));
                }
                else{
                    myEventLiveData.postValue(Resource.error(response.message()));
                    try {
                        if (response.errorBody() != null) {
                            myEventLiveData.postValue(Resource.error(response.errorBody().string()));
                        }
                    }
                    catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<EventResponseModel> call, Throwable t) {
                myEventLiveData.postValue(Resource.error(t.getMessage()));
            }
        });
    }
}

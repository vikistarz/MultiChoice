package com.company.mulitchoice.billersList;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.company.mulitchoice.networkServices.WebService;
import com.company.mulitchoice.retrofitApi.BillersApi;
import com.company.mulitchoice.utilities.Resource;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillerListViewModel extends ViewModel {

    private final  MutableLiveData<Resource<List<BillersResponseModel>>> myBillerResponseLiveData;

    public BillerListViewModel() {
        this.myBillerResponseLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Resource<List<BillersResponseModel>>> getMyBillerResponseLiveData(){
        return myBillerResponseLiveData;
    }

    public void executeBillersListResponse(String value){
        WebService myWebservice = BillersApi.getMyRetrofit().create(WebService.class);
        Call<List<BillersResponseModel>> myCall = myWebservice.getBillerResponse(value);
        myBillerResponseLiveData.postValue(Resource.loading());
        myCall.enqueue(new Callback<List<BillersResponseModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<BillersResponseModel>>call, @NonNull Response<List<BillersResponseModel>> response) {

                if(response.isSuccessful() && response.body() != null && response != null){
                    myBillerResponseLiveData.postValue(Resource.success(response.body()));
                }
                else{
                    myBillerResponseLiveData.postValue(Resource.error(response.message()));
                    try{
                        if(response.errorBody() != null){
                            myBillerResponseLiveData.postValue(Resource.error(response.errorBody().string()));
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<BillersResponseModel>> call, @NonNull Throwable t) {
                 myBillerResponseLiveData.postValue(Resource.error(t.getMessage()));
            }
        });
    }

}

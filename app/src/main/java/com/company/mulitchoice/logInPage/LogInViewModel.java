package com.company.mulitchoice.logInPage;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.company.mulitchoice.networkServices.WebService;
import com.company.mulitchoice.retrofitApi.LogIn_SignUpApi;
import com.company.mulitchoice.utilities.Resource;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInViewModel extends ViewModel {

    public static final String TAG = "LogInViewModel";
    private final MutableLiveData<Resource<LogInResponseModel>> myLogInLiveData;
    public LogInViewModel() {
        this.myLogInLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Resource<LogInResponseModel>> getMyLogInLiveData(){
        return myLogInLiveData;
    }

    public void executeLogInRequest(String username, String password){
        LogInRequestModel myLogInRequestModel = new LogInRequestModel();
        myLogInRequestModel.setUsername(username);
        myLogInRequestModel.setPassword(password);

        Log.d(TAG, "Validate User" + myLogInRequestModel);

        WebService myWebservice = LogIn_SignUpApi.getMyRetrofit().create(WebService.class);
        Call<LogInResponseModel> myCall = myWebservice.performLoginRequest(myLogInRequestModel);
        myLogInLiveData.postValue(Resource.loading());
        myCall.enqueue(new Callback<LogInResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<LogInResponseModel> call, @NonNull Response<LogInResponseModel> response) {

                if(response.isSuccessful() && response != null && response.body() != null){
                    myLogInLiveData.postValue(Resource.success(response.body()));
                }
                else{
                    myLogInLiveData.postValue(Resource.error(response.message()));
                    try {
                        if (response.errorBody() != null) {
                           myLogInLiveData.postValue(Resource.error(response.errorBody().string()));
                        }
                    }
                   catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LogInResponseModel> call, @NonNull Throwable t) {

                myLogInLiveData.postValue(Resource.error(t.getMessage()));
            }
        });
    }

}

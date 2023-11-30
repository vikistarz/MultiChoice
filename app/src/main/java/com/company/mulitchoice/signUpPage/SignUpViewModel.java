package com.company.mulitchoice.signUpPage;

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

public class SignUpViewModel extends ViewModel {

    public static final String TAG = "SignUpViewModel";

    public MutableLiveData<Resource<SignUpResponseModel>> mySignUpLiveData;
    public SignUpViewModel(){
        mySignUpLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Resource<SignUpResponseModel>>getMySignUpLiveData(){
        return mySignUpLiveData;
    }

    public void executeSignUpRequest(String firstName, String lastName, String phoneNumber, String username, String password){
        SignUpRequestModel mySignUpRequestModel = new SignUpRequestModel();
        mySignUpRequestModel.setFirstName(firstName);
        mySignUpRequestModel.setLastName(lastName);
        mySignUpRequestModel.setPhoneNumber(phoneNumber);
        mySignUpRequestModel.setUsername(username);
        mySignUpRequestModel.setPassword(password);


        Log.d(TAG, "check Post Request:" + mySignUpRequestModel);

        WebService myWebservice = LogIn_SignUpApi.getMyRetrofit().create(WebService.class);
        Call<SignUpResponseModel> myCall = myWebservice.getSignUpResponse(mySignUpRequestModel);

        mySignUpLiveData.postValue(Resource.loading());
        myCall.enqueue(new Callback<SignUpResponseModel>() {
            @Override
            public void onResponse( @NonNull Call<SignUpResponseModel> call, @NonNull Response<SignUpResponseModel> response) {

                if(response.isSuccessful() && response != null && response.body() != null){

                    mySignUpLiveData.postValue(Resource.success(response.body()));
                }
                else{
                    String errorMessage = response.message();
                    mySignUpLiveData.postValue(Resource.error(errorMessage));
                }
                try{
                    if(response.errorBody() != null){
                        mySignUpLiveData.postValue(Resource.error(response.errorBody().string()));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SignUpResponseModel> call, @NonNull Throwable t) {

                mySignUpLiveData.postValue(Resource.error(t.getMessage()));
            }
        });
    }



}

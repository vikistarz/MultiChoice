package com.company.mulitchoice.dashboardPage;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.company.mulitchoice.networkServices.WebService;
import com.company.mulitchoice.retrofitApi.SuperHeroApi;
import com.company.mulitchoice.utilities.Resource;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuperHerosViewModel extends ViewModel {

    private final MutableLiveData<Resource<List<SuperHerosModel>>> myHerosLiveData;


    public SuperHerosViewModel() {
        this.myHerosLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Resource<List<SuperHerosModel>>> getHerosLiveData(){
        return myHerosLiveData;
    }

    public void executeSuperHerosResponse(){
        WebService myWebservice = SuperHeroApi.getMyRetrofit().create(WebService.class);
        Call<List<SuperHerosModel>> myCall = myWebservice.getSuperHero();
        myHerosLiveData.postValue(Resource.loading());
        myCall.enqueue(new Callback<List<SuperHerosModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<SuperHerosModel>> call, @NonNull Response<List<SuperHerosModel>> response) {

                if(response.isSuccessful() && response.body() != null && response != null){
                    myHerosLiveData.postValue(Resource.success(response.body()));
                }
                else{
                    myHerosLiveData.postValue(Resource.error(response.message()));
                    try{
                        if(response.errorBody() != null){
                            myHerosLiveData.postValue(Resource.error(response.errorBody().string()));
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<SuperHerosModel>> call, @NonNull Throwable t) {
                 myHerosLiveData.postValue(Resource.error(t.getMessage()));
            }
        });
    }


}

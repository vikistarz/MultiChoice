package com.company.mulitchoice.networkServices;

import com.company.mulitchoice.billersList.BillersResponseModel;
import com.company.mulitchoice.dashboardPage.SuperHerosModel;
import com.company.mulitchoice.eventPage.EventRequestModel;
import com.company.mulitchoice.eventPage.EventResponseModel;
import com.company.mulitchoice.logInPage.LogInRequestModel;
import com.company.mulitchoice.logInPage.LogInResponseModel;
import com.company.mulitchoice.signUpPage.SignUpRequestModel;
import com.company.mulitchoice.signUpPage.SignUpResponseModel;
import com.company.mulitchoice.transactionDetails.EventLogModels;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WebService {

    @POST("api/v1/users/register")
    Call<SignUpResponseModel> getSignUpResponse(@Body SignUpRequestModel mySignUpFRequestModel);

    @POST("api/v1/users/login")
    Call<LogInResponseModel> performLoginRequest(@Body LogInRequestModel myLogInRequestModel);

    @POST("api/v1/events")
    Call<EventResponseModel> performCreateEvent(@Body EventRequestModel myEventsRequestModel);

    @GET("api/v1/events")
    Call<EventLogModels> performGetAllEvents(@Query("customerId") String value);

    @GET("marvel")
    Call<List<SuperHerosModel>> getSuperHero();

    @GET("/v3/7e1f415a-70f7-4400-9b90-c065b3605094")
    Call<List<BillersResponseModel>> getBillerResponse(@Query("mocky-delay") String value);
}

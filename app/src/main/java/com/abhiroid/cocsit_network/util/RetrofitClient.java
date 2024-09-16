package com.abhiroid.cocsit_network.util;

import com.abhiroid.cocsit_network.apis.UsersAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http://192.168.50.89/cocsit_network/";
    private static RetrofitClient retrofitClient;
    private Retrofit retrofit;

    //this constructor use for convert json response into the java object
    private RetrofitClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    //for get a single ton object to coder only
    public static synchronized RetrofitClient getInstance(){

        if(retrofitClient == null){
            retrofitClient = new RetrofitClient();
        }

        return retrofitClient;
    }

    public UsersAPI getUserApi(){
        return retrofit.create(UsersAPI.class);
    }
}

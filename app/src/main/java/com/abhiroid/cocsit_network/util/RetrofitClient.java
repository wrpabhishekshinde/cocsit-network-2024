package com.abhiroid.cocsit_network.util;

import com.abhiroid.cocsit_network.apis.PostAPI;
import com.abhiroid.cocsit_network.apis.UserEduAPI;
import com.abhiroid.cocsit_network.apis.UsersAPI;
import com.abhiroid.cocsit_network.model_response.PostModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http://192.168.1.215/cocsit_network/";

    private static RetrofitClient retrofitClient;
    private Retrofit retrofit;

    private OkHttpClient.Builder builder = new OkHttpClient.Builder();
    private HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

    //this constructor use for convert json response into the java object
//    private RetrofitClient(){
//        retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//    }



    private RetrofitClient(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
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

    public UserEduAPI getUserEduApi(){
        return  retrofit.create(UserEduAPI.class);
    }
    public PostAPI getPostApi(){
        return retrofit.create(PostAPI.class);
    }
}

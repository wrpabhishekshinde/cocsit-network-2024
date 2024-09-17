package com.abhiroid.cocsit_network.apis;

import com.abhiroid.cocsit_network.model_response.CreateUserResponse;
import com.abhiroid.cocsit_network.model_response.ProfileImage;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UsersAPI {

    //for create user only
    @FormUrlEncoded
    @POST("users/CreateUser.php")
    Call<CreateUserResponse> register(
        @Field("username") String username,
        @Field("email") String email,
        @Field("password") String  password,
        @Field("first_name") String  first_name,
        @Field("last_name") String  last_name,
        @Field("profile_pic") String  profile_pic,
        @Field("bio") String  bio
    );

    @FormUrlEncoded
    @POST("image_controller/UploadProfileImage.php")
    Call<ProfileImage> uploadProfileImg(
            @Field("title") String  title,
            @Field("image") String  image
    );


}

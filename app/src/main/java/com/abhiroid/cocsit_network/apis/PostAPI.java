package com.abhiroid.cocsit_network.apis;

import com.abhiroid.cocsit_network.model_response.PostModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostAPI {

    @FormUrlEncoded
    @POST("posts/CreateImagePost.php")
    Call<PostModel> insertPost(@Field("user_id") int user_id ,
                               @Field("content") String contentText,
                               @Field("post_img") String postImg
                               );

    @FormUrlEncoded
    @POST("posts/CreateTextPost.php")
    Call<PostModel> insertPost(@Field("user_id") int user_id ,
                               @Field("content") String contentText
    );


}

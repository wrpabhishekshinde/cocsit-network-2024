package com.abhiroid.cocsit_network.apis;

import com.abhiroid.cocsit_network.model_response.EduDetailsResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserEduAPI {

    @FormUrlEncoded
    @POST("edu_details/InsertDetails.php")
    Call<EduDetailsResponse> insertEduDetails(
            @Field("user_id") int user_id,
            @Field("class_div") String class_div,
            @Field("mobile") String mobile,
            @Field("dob") String dob,
            @Field("gender") String gender
    );

}

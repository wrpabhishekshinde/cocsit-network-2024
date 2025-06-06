package com.abhiroid.cocsit_network.model_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateUser {

    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("userId")
    @Expose
    private int userId;

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("message")
    @Expose
    private String message;

    public CreateUser(String error, String message , int userId) {
        this.error = error;
        this.message = message;
        this.userId = userId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

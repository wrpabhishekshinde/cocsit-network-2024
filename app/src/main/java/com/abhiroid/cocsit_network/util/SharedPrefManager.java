package com.abhiroid.cocsit_network.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.abhiroid.cocsit_network.model_response.User;

public class SharedPrefManager {

    private static final String SHARE_PREF_NAME = "cocsit-network";
    private SharedPreferences sharedPreferences;
    private Context context;
    private SharedPreferences.Editor editor;

    public SharedPrefManager(Context context){
        this.context = context;
    }

    public void saveUser(User user){
        sharedPreferences = context.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("id" , user.getId());
        editor.putString("username" , user.getUsername());
        editor.putString("email" , user.getEmail());
        editor.putString("password" , user.getPassword());
        editor.putString("fName" , user.getFirstName());
        editor.putString("lName" , user.getLastName());
        editor.putString("profilePic" , user.getProfilePic());
        editor.putString("bio" , user.getBio());
        editor.putString("createdAt" , user.getCreatedAt());
        editor.putString("updatedAt" , user.getUpdateAt());
        editor.putBoolean("logged" , true);
        editor.apply();
    }

    public boolean isLoogedIn(){
        sharedPreferences = context.getSharedPreferences(SHARE_PREF_NAME , Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("logged", false);
    }

    public User getUser(){
        sharedPreferences = context.getSharedPreferences(SHARE_PREF_NAME , Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt("id" , -1),
        sharedPreferences.getString("username" , null),
        sharedPreferences.getString("email" ,null),
        sharedPreferences.getString("password" , null),
        sharedPreferences.getString("fName" , null),
        sharedPreferences.getString("lName" , null),
        sharedPreferences.getString("profilePic" , null),
        sharedPreferences.getString("bio" , null),
        sharedPreferences.getString("createdAt" , null),
        sharedPreferences.getString("updatedAt" , null)
        );
    }

    public void logOut(){
        sharedPreferences = context.getSharedPreferences(SHARE_PREF_NAME , Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}

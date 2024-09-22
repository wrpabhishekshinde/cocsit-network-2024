package com.abhiroid.cocsit_network.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abhiroid.cocsit_network.R;
import com.abhiroid.cocsit_network.model_response.User;
import com.abhiroid.cocsit_network.model_response.UserResponse;
import com.abhiroid.cocsit_network.util.RetrofitClient;
import com.abhiroid.cocsit_network.util.SharedPrefManager;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment implements View.OnClickListener{

    private EditText etUserName , etUserPassword;
    private TextView tvForgotPass , tvSignup;
    private Button btnLogin;

    private SharedPrefManager sharedPrefManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        etUserName = view.findViewById(R.id.etUserName1);
        etUserPassword = view.findViewById(R.id.etUserPassword1);
        tvForgotPass = view.findViewById(R.id.tvForgotPass);
        tvSignup = view.findViewById(R.id.tvSignup);
        btnLogin = view.findViewById(R.id.btnLogin);

        tvForgotPass.setOnClickListener(this);
        tvSignup.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        sharedPrefManager = new SharedPrefManager(getContext());


        return view;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if(id == R.id.tvForgotPass){

        }
        if(id == R.id.tvSignup){
            loadFragment(new AuthenticationFragment());
        }
        if(id == R.id.btnLogin){
            setFields();
        }
    }

    private void setFields() {

        String username = etUserName.getText().toString().trim();
        String password = etUserPassword.getText().toString().trim();


        if(username.isEmpty()){
            etUserName.requestFocus();
            etUserName.setError("Please enter username");
            return;
        }
        if(password.isEmpty()){
            etUserPassword.requestFocus();
            etUserPassword.setError("Please enter password");
            return;
        }
        if(password.length() < 6){
            etUserPassword.requestFocus();
            etUserPassword.setError("Password may contain at least 6 letter");
            return;
        }

        authenticateUser(username , password);

    }

    private void authenticateUser(String username, String password) {

        Call<UserResponse> call = RetrofitClient.getInstance().getUserApi().authUsername(username, password);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                if(response.isSuccessful() && response.body() != null){

                    UserResponse userResponse = response.body();
                    if(userResponse.getError().equals("000")){

                        sharedPrefManager.saveUser(userResponse.getUser());//it will store all value of users

                        Toast.makeText(getContext(), userResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(getContext(), userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getContext(), "Response Fail", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Fail "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //load frag
    public void loadFragment(Fragment fragment){
        if(fragment != null) {

            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            ft.replace(R.id.frameLayout , fragment);
//            fm.popBackStack("Main" , FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ft.addToBackStack("Main Activity");
            ft.commit();
        }
    }


//    @Override
//    public void onStart() {
//        super.onStart();
//
//        if(sharedPrefManager.isLoogedIn()){
//            Toast.makeText(getContext(), "Logged In already", Toast.LENGTH_SHORT).show();
//        }
//    }
}
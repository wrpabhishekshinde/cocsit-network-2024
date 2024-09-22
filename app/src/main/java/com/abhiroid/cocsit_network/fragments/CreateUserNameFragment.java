package com.abhiroid.cocsit_network.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.abhiroid.cocsit_network.R;
import com.abhiroid.cocsit_network.model_response.UserResponse;
import com.abhiroid.cocsit_network.model_response.User;
import com.abhiroid.cocsit_network.util.RetrofitClient;
import com.abhiroid.cocsit_network.util.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateUserNameFragment extends Fragment implements View.OnClickListener {


    private EditText etUsername, etUserPassword, etConfirmUserPassword;
    private AppCompatButton btnNext;
    private int userId;

    SharedPrefManager sharedPrefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_user_name, container, false);

        etUsername = view.findViewById(R.id.etUsername);
        etUserPassword = view.findViewById(R.id.etUserPassword);
        etConfirmUserPassword = view.findViewById(R.id.etConfirmUserPassword);
        btnNext = view.findViewById(R.id.btnNext1);

        //for getting userid from bundle passing
        Bundle bundle = getArguments();
        if (bundle != null) {
            userId = bundle.getInt("userId");
        }

        //for save user data in shared preference
        sharedPrefManager = new SharedPrefManager(getContext());
        btnNext.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.btnNext1) {
            checkInputs();
        }
    }

    private void checkInputs() {

        String userName = etUsername.getText().toString().trim();
        String userPassword = etUserPassword.getText().toString().trim();
        String confirmUserPass = etConfirmUserPassword.getText().toString().trim();

        if (userName.isEmpty()) {
            etUsername.requestFocus();
            etUsername.setError("Please enter your username");
            return;

        }

        if (userPassword.isEmpty()) {
            etUserPassword.requestFocus();
            etUserPassword.setError("Please enter your password");
            return;
        }
        if (userPassword.length() < 8) {
            etUserPassword.requestFocus();
            etUserPassword.setError("Password may contain at least 8 letter");
            return;
        }
        if (!userPassword.equals(confirmUserPass)) {
            etConfirmUserPassword.requestFocus();
            etConfirmUserPassword.setError("Confirm your password correctly");
            return;
        }

        saveUsername(userId, userName, userPassword);
    }

    private void saveUsername(int id, String userName, String userPassword) {

        Call<UserResponse> call = RetrofitClient.getInstance().getUserApi().createUserName(id, userName, userPassword);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.isSuccessful() && response.body() != null){

                    UserResponse createUsernameResponse = response.body();
                    if(createUsernameResponse.getError().equals("000")){

                       sharedPrefManager.saveUser(createUsernameResponse.getUser());
                       Toast.makeText(getContext(), createUsernameResponse.getMessage(), Toast.LENGTH_SHORT).show();

                       loadFragment(new LoginFragment());

                    }else {
                        Toast.makeText(getContext(), createUsernameResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getContext(), "Response Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
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
}
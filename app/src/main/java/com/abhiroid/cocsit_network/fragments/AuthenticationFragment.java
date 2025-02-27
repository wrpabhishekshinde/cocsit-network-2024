package com.abhiroid.cocsit_network.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Looper;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abhiroid.cocsit_network.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;
import java.util.logging.LogRecord;


public class AuthenticationFragment extends Fragment implements View.OnClickListener {

    private TextView tvRegisterLink;
    private EditText etEmail, etPassword;
    private AppCompatButton btnLogin, btnSendLink;

    private  static final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private   String userEmail = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_authentication, container, false);

        tvRegisterLink = view.findViewById(R.id.tvRegisterLink);
        etEmail = view.findViewById(R.id.etEmailLogin);
        etPassword = view.findViewById(R.id.etPasswordPass);
        btnLogin = view.findViewById(R.id.btnNext);
        btnSendLink = view.findViewById(R.id.btnVerify);


        btnLogin.setOnClickListener(this);
        tvRegisterLink.setOnClickListener(this);
        btnSendLink.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();


        if(id == R.id.btnVerify){
            authenticateUser();
        }
        if (id == R.id.btnNext) {
            navigateCreateAc();
        }
        if (id == R.id.tvRegisterLink) {
            loadFragment(new LoginFragment());
        }
    }

    private void authenticateUser() {
        userEmail = etEmail.getText().toString().trim();
        String userPassword = etPassword.getText().toString().trim();


        if (userEmail.isEmpty()) {
            etEmail.requestFocus();
            etEmail.setError("Please enter your email");
            return;

        }
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            etEmail.requestFocus();
            etEmail.setError("Please enter valid email");
            return;
        }
        if (userPassword.isEmpty()) {
            etPassword.requestFocus();
            etPassword.setError("Please enter your password");
            return;
        }
        if (userPassword.length() < 6) {
            etPassword.requestFocus();
            etPassword.setError("Please enter valid password");
            return;
        }


        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    sendVerificationLink(firebaseAuth.getCurrentUser());
                } else {
                    Toast.makeText(getContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void sendVerificationLink(FirebaseUser currentUser) {

        if (currentUser != null) {

            currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Verify please", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Failed to verified", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }

    private void navigateCreateAc(){

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            // Reload user to refresh verification status
            currentUser.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (currentUser.isEmailVerified()) {
                        if (Objects.equals(currentUser.getEmail(), userEmail)) {
                            // Navigate to the next screen or show success

                            loadFragment(new SignUpFragment());

                        } else {
                            Toast.makeText(getContext(), "Emails do not match!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Please verify your email first.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
        }

    }


    public void loadFragment(Fragment fragment){
        if(fragment != null) {

            Bundle bundle = new Bundle();
            bundle.putString("email" , userEmail);
            fragment.setArguments(bundle);

            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            ft.replace(R.id.containrMainA , fragment);
//            fm.popBackStack("Main" , FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ft.addToBackStack("Main Activity");
            ft.commit();
        }
    }


}
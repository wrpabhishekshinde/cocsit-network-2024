package com.abhiroid.cocsit_network.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.abhiroid.cocsit_network.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileFragment extends Fragment {

    private RelativeLayout childContainer;
    private CardView profileCard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);



        return view;
    }



    //load other fragments ....
    public void loadFragment(Fragment fragment){
        if(fragment != null) {
            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frameLayout , fragment);
//            fm.popBackStack(ROOT_FRAGMENT_TAG , FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ft.addToBackStack("Profile Fragment");

            ft.commit();
        }
    }


}
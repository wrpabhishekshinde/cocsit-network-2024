package com.abhiroid.cocsit_network.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abhiroid.cocsit_network.R;

public class SearchFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_search, container, false);


        return view;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        //for hide toolbar of dashboard
//        HomeFragment.toolbar.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        //for visible toolbar of dashboard
//        HomeFragment.toolbar.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        //for hide toolbar of dashboard
//        HomeFragment.toolbar.setVisibility(View.GONE);
//    }
}
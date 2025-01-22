package com.abhiroid.cocsit_network.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abhiroid.cocsit_network.R;
import com.abhiroid.cocsit_network.adapter.NotiSugAdapter;
import com.abhiroid.cocsit_network.model.NotiSugMdoel;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private RecyclerView notiRecylerView;
    private ArrayList<NotiSugMdoel> notiSugMdoelArrayList = new ArrayList<NotiSugMdoel>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_search, container, false);
        notiRecylerView = view.findViewById(R.id.notiRecyclerView);
        notiRecylerView.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayoutManager.VERTICAL , false));

        notiSugMdoelArrayList.add(new NotiSugMdoel(R.drawable.post_image , "Ghost"));
        notiSugMdoelArrayList.add(new NotiSugMdoel(R.drawable.post_image , "Ghost"));
        notiSugMdoelArrayList.add(new NotiSugMdoel(R.drawable.post_image , "Ghost"));
        notiSugMdoelArrayList.add(new NotiSugMdoel(R.drawable.post_image , "Ghost"));
        notiSugMdoelArrayList.add(new NotiSugMdoel(R.drawable.post_image , "Ghost"));
        notiSugMdoelArrayList.add(new NotiSugMdoel(R.drawable.post_image , "Ghost"));

        notiRecylerView.setAdapter(new NotiSugAdapter(getContext() , notiSugMdoelArrayList));


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
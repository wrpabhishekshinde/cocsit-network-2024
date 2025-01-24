package com.abhiroid.cocsit_network.fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.abhiroid.cocsit_network.DashboardControllerActivity;
import com.abhiroid.cocsit_network.MainActivity;
import com.abhiroid.cocsit_network.R;
import com.abhiroid.cocsit_network.util.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomeFragment extends Fragment {


    private static final String ROOT_FRAGMENT_TAG = "cocsit_dashboard";


    public static DrawerLayout drawerLayout;
    NavigationView navigationView;
    public static Toolbar toolbar;
//    private BottomNavigationView bottomNavigationView;
    ActionBarDrawerToggle toggle;

    SharedPrefManager sharedPrefManager;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_home, container, false);
//for hide top status bar
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);





        drawerLayout = view.findViewById(R.id.drawerLayout);
//        navigationView = view.findViewById(R.id.navigationView);
        toolbar = view.findViewById(R.id.dashToolbar);
//        bottomNavigationView = view.findViewById(R.id.bottom_nav_view);


        ((DashboardControllerActivity)getActivity()).setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(getActivity() ,drawerLayout ,  toolbar  , R.string.open_drawer , R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        //for close drawer layout on the action of backpress if drawer layout is open
        closeDrawerLayout();

        //for initialise the shared pre manager
        sharedPrefManager = new SharedPrefManager(getContext());





        //for drawer layout
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
//
//                if(id == R.id.btnLogout){
//                    sharedPrefManager.logOut();
//                    loadFragment(new LoginFragment());
//                }
//
//                if(id == R.id.btnSetting){
//                    loadFragment(new SettingFragment());
//                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


//        //for bottom nav layout
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                int id = item.getItemId();
//
//                if(id == R.id.btnHome){
//                    loadFragment(new HomeFragment());
//                }
//                if(id == R.id.btnSearch){
//                    loadFragmentOnDash(new SearchFragment());
//                }
//                if(id == R.id.btnCreatePost){
//                    loadFragmentOnDash(new CreatePostFragment());
//                }
//                if(id == R.id.btnMessage){
//                    loadFragmentOnDash(new DMFragment());
//                }
//                if(id == R.id.btnProfile){
//                    loadFragmentOnDash(new ProfileFragment());
//                }
//
//
//                return true;
//            }
//        });


        return view;
    }



    //methods are here

    //load other fragments ....
    public void loadFragment(Fragment fragment){
        if(fragment != null) {

            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            ft.replace(R.id.frameLayout , fragment);

            fm.popBackStack(ROOT_FRAGMENT_TAG , FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ft.addToBackStack("Home Fragment");

            ft.commit();
        }
    }

    //load other fragments ....
   /* public void loadFragmentOnDash(Fragment fragment){
        if(fragment != null) {


            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            //for check which fragment in load
            Fragment currentFragment = fm.findFragmentById(R.id.dashFrameLayout);

            if(currentFragment != null){

               String existingFrag = fragment.getClass().getSimpleName();
               String currentFrag = currentFragment.getClass().getSimpleName();

               //if user loading that frag which is already in loading then we will pop back the stack
               if(existingFrag.equals(currentFrag)){
                   fm.popBackStack();
               }
            }

            ft.replace(R.id.dashFrameLayout , fragment);

            fm.popBackStack(ROOT_FRAGMENT_TAG , FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ft.addToBackStack("Home Fragment");

            ft.commit();
        }
    }*/

    public void  closeDrawerLayout(){

        //close drawer layout on backpressed..
        if(getActivity() != null) {
            getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START);
                    } else {
                        setEnabled(false);
                        getActivity().onBackPressed();
                    }

                }
            });
        }
    }

   /* public void setSelectedBottomNav() {


        FragmentManager fm = getParentFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.dashFrameLayout);


        if(frag != null){
            if(frag instanceof HomeFragment){
                bottomNavigationView.setSelectedItemId(R.id.btnHome);
            }
            else if (frag instanceof  SearchFragment) {
                bottomNavigationView.setSelectedItemId(R.id.btnSearch);
            }
            else if (frag instanceof  CreatePostFragment) {
                bottomNavigationView.setSelectedItemId(R.id.btnCreatePost);
            }
            else if (frag instanceof  DMFragment) {
                bottomNavigationView.setSelectedItemId(R.id.btnMessage);
            }
            else if (frag instanceof  ProfileFragment) {
                bottomNavigationView.setSelectedItemId(R.id.btnProfile);
            }
        }else {
            Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();
        }

    }*/

}
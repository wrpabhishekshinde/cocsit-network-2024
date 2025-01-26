package com.abhiroid.cocsit_network;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.abhiroid.cocsit_network.fragments.CreatePostFragment;
import com.abhiroid.cocsit_network.fragments.DMFragment;
import com.abhiroid.cocsit_network.fragments.HomeFragment;
import com.abhiroid.cocsit_network.fragments.NotificationFragment;
import com.abhiroid.cocsit_network.fragments.ProfileFragment;
import com.abhiroid.cocsit_network.fragments.SearchFragment;
import com.abhiroid.cocsit_network.fragments.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardControllerActivity extends AppCompatActivity {
//activity for dashboard controlle
    private static final String ROOT_FRAGMENT_TAG = "cocsit_dashboard1";
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard_controller);



//        bottomNavigationView = findViewById(R.id.bottom_nav_view);
//        loadFragment(new SettingFragment());
//
//
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
//                    loadFragment(new SearchFragment());
//                }
//                if(id == R.id.btnCreatePost){
//                    loadFragment(new CreatePostFragment());
//                }
//                if(id == R.id.btnMessage){
//                    loadFragment(new DMFragment());
//                }
//                if(id == R.id.btnProfile){
//                    loadFragment(new ProfileFragment());
//                }
//                return true;
//            }
//        });
    }

    //load other fragments ....
    public void loadFragment(Fragment fragment){
        if(fragment != null) {

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            ft.replace(R.id.frameLayoutDash, fragment);

            fm.popBackStack(ROOT_FRAGMENT_TAG , FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ft.addToBackStack("Home Fragment");

            ft.commit();
        }
    }
}
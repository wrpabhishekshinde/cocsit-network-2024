package com.abhiroid.cocsit_network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.abhiroid.cocsit_network.databinding.ActivityTestBottomNavBinding;
import com.abhiroid.cocsit_network.fragments.Home1Fragment;
import com.abhiroid.cocsit_network.fragments.MyProfileFragment;
import com.abhiroid.cocsit_network.fragments.MychatsFragment;
import com.abhiroid.cocsit_network.fragments.Search1Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Test_BottomNavActivity extends AppCompatActivity {

    ActivityTestBottomNavBinding binding;
    BottomNavigationView bottomNavigationView;
    private boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestBottomNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Load the default fragment (Home1Fragment) initially
        if (savedInstanceState == null) {
            loadFragment(new Home1Fragment());
        }


        // Handle bottom navigation item clicks
        binding.bottomNavTBNA.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Fragment selectedFragment = null;

                if (itemId == R.id.home1_nav) {
                    selectedFragment = getSupportFragmentManager().findFragmentByTag(Home1Fragment.class.getSimpleName());
                    if (selectedFragment == null) {
                        selectedFragment = new Home1Fragment();
                    }

                } else if (itemId == R.id.search1_nav) {

                    selectedFragment = getSupportFragmentManager().findFragmentByTag(Search1Fragment.class.getSimpleName());
                    if (selectedFragment == null) {
                        selectedFragment = new Search1Fragment();
                    }

                } else if (itemId == R.id.mychat_nav) {

                    selectedFragment = getSupportFragmentManager().findFragmentByTag(MychatsFragment.class.getSimpleName());
                    if (selectedFragment == null) {
                        selectedFragment = new MychatsFragment();
                    }
                } else if (itemId == R.id.myprofile_nav) {

                    selectedFragment = getSupportFragmentManager().findFragmentByTag(MyProfileFragment.class.getSimpleName());
                    if (selectedFragment == null) {
                        selectedFragment = new MyProfileFragment();
                    }

                } else {
                    Toast.makeText(Test_BottomNavActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    return false;
                }

                loadFragment(selectedFragment);
                return true;

            }
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.containerTBNA);
                // If the current fragment is not Home1Fragment, go to Home1Fragment
                if (!(currentFragment instanceof Home1Fragment)) {
                    loadFragment(new Home1Fragment());
                    binding.bottomNavTBNA.setSelectedItemId(R.id.home1_nav);  // Set the home item selected

                } else {
                    // If already in Home1Fragment, handle double back press to exit
                    if (doubleBackToExitPressedOnce) {
                        finish();
                    } else {
                        doubleBackToExitPressedOnce = true;
                        Toast.makeText(Test_BottomNavActivity.this, "Press back again to exit", Toast.LENGTH_SHORT).show();

                        // Reset the flag after 2 seconds
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                doubleBackToExitPressedOnce = false;
                            }
                        }, 2000);
                    }
                }

            }
        });

        //don't use this ...GS says
        /*// Handle back press
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();  // Go back to the previous fragment
                } else {
                    finish();  // Exit the activity if no more fragments in the back stack
                }
            }
        });*/
    }

    // Method to load the selected fragment
    private void loadFragment(androidx.fragment.app.Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.containerTBNA, fragment,fragment.getClass().getSimpleName()); // Use fragment tag to avoid recreation
        //ft.addToBackStack(null);  // Add the transaction to the back stack so the user can navigate back
        ft.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}

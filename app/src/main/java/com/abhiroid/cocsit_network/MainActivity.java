package com.abhiroid.cocsit_network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.abhiroid.cocsit_network.databinding.ActivityMainBinding;
import com.abhiroid.cocsit_network.fragments.ChatFragment;
import com.abhiroid.cocsit_network.fragments.HomeFragment;
import com.abhiroid.cocsit_network.fragments.ProfileFragment;
import com.abhiroid.cocsit_network.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private boolean doubleBackToExitPressedOnce = false;

    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Load the default fragment (Home1Fragment) initially
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }

        binding.bottomNavMainA.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();
                Fragment selectedFragment = null;

                if (itemId == R.id.home) {
                    selectedFragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
                    if (selectedFragment == null) {
                        selectedFragment = new HomeFragment();
                    }

                } else if (itemId == R.id.search) {

                    selectedFragment = getSupportFragmentManager().findFragmentByTag(SearchFragment.class.getSimpleName());
                    if (selectedFragment == null) {
                        selectedFragment = new SearchFragment();
                    }

                } else if (itemId == R.id.mychat) {

                    selectedFragment = getSupportFragmentManager().findFragmentByTag(ChatFragment.class.getSimpleName());
                    if (selectedFragment == null) {
                        selectedFragment = new ChatFragment();
                    }
                } else if (itemId == R.id.myprofile) {

                    selectedFragment = getSupportFragmentManager().findFragmentByTag(ProfileFragment.class.getSimpleName());
                    if (selectedFragment == null) {
                        selectedFragment = new ProfileFragment();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    return false;
                }

                loadFragment(selectedFragment);




                return true;
            }
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.containrMainA);
                // If the current fragment is not Home1Fragment, go to Home1Fragment
                if (!(currentFragment instanceof HomeFragment)) {
                    loadFragment(new HomeFragment());
                    binding.bottomNavMainA.setSelectedItemId(R.id.home);  // Set the home item selected

                } else {
                    // If already in Home1Fragment, handle double back press to exit
                    if (doubleBackToExitPressedOnce) {
                        finish();
                    } else {
                        doubleBackToExitPressedOnce = true;
                        Toast.makeText(MainActivity.this, "Press back again to exit", Toast.LENGTH_SHORT).show();

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

    }

    private void loadFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.containrMainA, fragment,fragment.getClass().getSimpleName()); // Use fragment tag to avoid recreation
        //ft.addToBackStack(null);  // Add the transaction to the back stack so the user can navigate back
        ft.commit();

    }//hello kay chaluy

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
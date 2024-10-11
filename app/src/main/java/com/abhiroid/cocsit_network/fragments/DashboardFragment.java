    package com.abhiroid.cocsit_network.fragments;

    import android.os.Bundle;

    import androidx.activity.OnBackPressedCallback;
    import androidx.annotation.NonNull;
    import androidx.fragment.app.Fragment;
    import androidx.fragment.app.FragmentManager;
    import androidx.fragment.app.FragmentTransaction;

    import android.view.LayoutInflater;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Toast;

    import com.abhiroid.cocsit_network.R;
    import com.google.android.material.bottomnavigation.BottomNavigationView;

    import java.util.Objects;

    public class DashboardFragment extends Fragment {

        private static final String ROOT_FRAGMENT_TAG = "dash_board";


        BottomNavigationView bottomNavigationView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

            bottomNavigationView = view.findViewById(R.id.bottomNav);

            //default frag load
            loadFragmentDash(new HomeFragment() , true);




            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    int id = item.getItemId();

                    if(id == R.id.btnHome){
                        loadFragmentDash(new HomeFragment() , false);
                    }
                    if(id == R.id.btnSearch){
                        loadFragmentDash(new SearchFragment() , false);
                    }
                    if(id == R.id.btnCreatePost){
                        loadFragmentDash(new CreatePostFragment(), false);
                    }
                    if(id == R.id.btnMessage){
                        loadFragmentDash(new DMFragment() , false);
                    }
                    if(id == R.id.btnProfile){
                        loadFragmentDash(new ProfileFragment() , false);
                    }


                    return true;
                }
            });



            //back pressed
            requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {


                    FragmentManager fm = getParentFragmentManager();
                    if(fm.getBackStackEntryCount() > 1){
                        fm.popBackStack();
                        Fragment frag = fm.findFragmentById(R.id.bottom_frameLayout);
                        updateBottomNavigation(frag);
                    }else {
                        setEnabled(false);
                        requireActivity().finish();
                    }
                }
            });




            return view;
        }



        //for load fragments ....
        public void loadFragmentDash(Fragment fragment , boolean flag) {

            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();


            if (fragment != null) {

                if(flag) {
                    ft.add(R.id.bottom_frameLayout , fragment);
                    fm.popBackStack(ROOT_FRAGMENT_TAG , FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    ft.addToBackStack("");
                }else {

                    if(!fragment.getClass().getSimpleName().equals(Objects.requireNonNull(fm.findFragmentById(R.id.bottom_frameLayout)).getClass().getSimpleName())) {
                        ft.replace(R.id.bottom_frameLayout, fragment);
                        fm.popBackStack(ROOT_FRAGMENT_TAG , FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        ft.addToBackStack("");
                    }
                }

                ft.commit();
            }
        }

        public void updateBottomNavigation(Fragment frag) {

            Toast.makeText(getContext(), "back pressed", Toast.LENGTH_SHORT).show();




            if(frag != null){

                if(frag instanceof HomeFragment){
                    bottomNavigationView.setSelectedItemId(R.id.btnHome);
                    Toast.makeText(getContext(), "home", Toast.LENGTH_SHORT).show();
                }
                if (frag instanceof  SearchFragment) {
                    bottomNavigationView.setSelectedItemId(R.id.btnSearch);
                    Toast.makeText(getContext(), "search", Toast.LENGTH_SHORT).show();
                }
                if (frag instanceof  CreatePostFragment) {
                    bottomNavigationView.setSelectedItemId(R.id.btnCreatePost);
                    Toast.makeText(getContext(), "post", Toast.LENGTH_SHORT).show();
                }
                if (frag instanceof  DMFragment) {
                    bottomNavigationView.setSelectedItemId(R.id.btnMessage);
                    Toast.makeText(getContext(), "message", Toast.LENGTH_SHORT).show();
                }
                if (frag instanceof  ProfileFragment) {
                    bottomNavigationView.setSelectedItemId(R.id.btnProfile);
                    Toast.makeText(getContext(), "profile", Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();
            }

        }


    }
package com.abhiroid.cocsit_network;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.abhiroid.cocsit_network.fragments.AuthenticationFragment;
import com.abhiroid.cocsit_network.fragments.BioProfileFragment;
import com.abhiroid.cocsit_network.fragments.CreateUserNameFragment;
import com.abhiroid.cocsit_network.fragments.DashboardFragment;
import com.abhiroid.cocsit_network.fragments.HomeFragment;
import com.abhiroid.cocsit_network.fragments.LoginFragment;
import com.abhiroid.cocsit_network.fragments.ProfileFragment;
import com.abhiroid.cocsit_network.fragments.SearchFragment;
import com.abhiroid.cocsit_network.fragments.SignUpFragment;
import com.abhiroid.cocsit_network.model_response.CreateUserResponse;
import com.abhiroid.cocsit_network.util.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadFragment(new ProfileFragment());
    }

    public void loadFragment(Fragment fragment){
        if(fragment != null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            ft.add(R.id.frameLayout , fragment);
//            fm.popBackStack("Main" , FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            ft.addToBackStack("Main Activity");
            ft.commit();
        }

    }//hello kay chaluy

}
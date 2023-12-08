package com.onogawean.sun.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.onogawean.sun.R;
import com.onogawean.sun.fragment.ChatFragment;
import com.onogawean.sun.fragment.DashboardFragment;
import com.onogawean.sun.fragment.HomeFragment;
//import com.onogawean.sun.fragmadent.LoginFragment;
import com.onogawean.sun.fragment.ProfileFragment;
import com.onogawean.sun.fragment.RegisterFragment;
import com.onogawean.sun.fragment.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    Button goToRegister;
    ImageView homeButton, dashboardButton, chatButton, settingsButton, profileButton;
    SwitchCompat switchMode;
    boolean nightMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public void setFragment(Class <? extends Fragment> fragment) {
        Fragment existingFragment = getSupportFragmentManager().findFragmentByTag(fragment.getName());
        String tag = fragment.getName();

        if (existingFragment == null) {
            try {
                Fragment newFragment = fragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.main_fragment , newFragment, tag)
                        .addToBackStack(null)
                        .commit();
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }

        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_fragment, existingFragment, tag)
                    .commit();
        }

    }


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFragment(HomeFragment.class);

        goToRegister = findViewById(R.id.sign_button);
        homeButton = findViewById(R.id.home_button);
        dashboardButton = findViewById(R.id.dashboard_button);
        chatButton = findViewById(R.id.chat_button);
        settingsButton = findViewById(R.id.settings_button);
        profileButton = findViewById(R.id.profile_button);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // User is already logged in, hide the sign-in button
            if (goToRegister != null) {
                goToRegister.setVisibility(View.GONE);
        }
        }


        goToRegister.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LoginRegisterActivity.class));
        });

        homeButton.setOnClickListener(v -> {
            setFragment(HomeFragment.class);
        });

        dashboardButton.setOnClickListener(v -> {
            setFragment(DashboardFragment.class);
        });

        chatButton.setOnClickListener(v -> {
            setFragment(ChatFragment.class);
        });

        settingsButton.setOnClickListener(v -> {
            setFragment(SettingsFragment.class);
        });

        profileButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        });


        switchMode = findViewById(R.id.switchMode);
        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("nightMode", false);

        if (nightMode){
            switchMode.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }


//        switchMode.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view){
//                if (nightMode){
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                    editor = sharedPreferences.edit();
//                    editor.putBoolean("nightMode", false);
//                } else {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                    editor = sharedPreferences.edit();
//                    editor.putBoolean("nightMode", true);
//                }
//                editor.apply();
//            }
//        });
    }

}
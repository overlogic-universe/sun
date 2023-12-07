package com.onogawean.sun.activities;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.onogawean.sun.R;
import com.onogawean.sun.fragment.LoginFragment;
import com.onogawean.sun.fragment.ProfileFragment;

public class ProfileActivity extends  AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        Fragment profileFragment = new ProfileFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().setReorderingAllowed(true);
        fragmentTransaction.add(R.id.login_register_fragment, profileFragment).commit();
    }
}

package com.onogawean.sun.activities;

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
import com.onogawean.sun.fragment.RegisterFragment;

public class LoginRegisterActivity extends AppCompatActivity {

    public static boolean isLogin = true;

    ImageView setLoginView, setRegisterView;

    public void setFragment(Class <? extends Fragment> fragment) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.login_register_fragment , fragment, null)
                .commit();
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        Fragment loginFragment = new LoginFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().setReorderingAllowed(true);
        fragmentTransaction.add(R.id.login_register_fragment, loginFragment).commit();
    }



}
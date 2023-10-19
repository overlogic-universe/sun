package com.onogawean.sun.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;


import com.onogawean.sun.R;
import com.onogawean.sun.fragment.LoginFragment;
import com.onogawean.sun.fragment.RegisterFragment;

public class MainActivity extends AppCompatActivity {

    Button goToRegister;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goToRegister = findViewById(R.id.sign_button);

        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginRegisterActivity.class));
            }
        });
    }
}
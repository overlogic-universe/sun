package com.onogawean.sun.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.onogawean.sun.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseAuth auth;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);
        EditText emailText, passText, nameText, confirmText;
        Button submitButton;
        emailText = view.findViewById(R.id.register_email);
        passText = view.findViewById(R.id.register_password);
        nameText = view.findViewById(R.id.register_name);
        confirmText = view.findViewById(R.id.register_confirm);
        submitButton = view.findViewById(R.id.register_button);
        TextView errorText = view.findViewById(R.id.extraText);
        auth = FirebaseAuth.getInstance();


        submitButton.setOnClickListener(v ->{
            String name, email, pass, confirm;
            name = String.valueOf(nameText.getText());
            email = String.valueOf(emailText.getText());
            pass = String.valueOf(passText.getText());
            confirm = String.valueOf(confirmText.getText());

            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(name) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(confirm))
            {
                errorText.setText("Tidak Boleh ada Field Kosong");
                errorText.setVisibility(View.VISIBLE);
            }else if(pass.length() < 8){
                errorText.setText("Password Minimal Terdiri dari 8 Karakter");
                errorText.setVisibility(View.VISIBLE);
            }else if(!pass.equals(confirm)){
                errorText.setText("Konfirmasi Password salah");
                errorText.setVisibility(View.VISIBLE);
            }
            else{
                errorText.setVisibility(View.INVISIBLE);
                registerUser(email, pass, name);
            }
        });

            ImageView registerViewButton = view.findViewById(R.id.ic_arrow_register);
        registerViewButton.setOnClickListener(v -> {
            Fragment loginFragment = new LoginFragment();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.login_register_fragment, loginFragment).commit();
        });

        return view;
    }
    private void registerUser(String email, String pass, String name) {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Generate user data on Firebase realtime database
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            String databasePath = String.format("mahasiswa");
                            DatabaseReference databaseReference = database.getReference();
                            //User user = new User(email,)
                            String key = databaseReference.push().getKey();
                            assert key != null;
                            DatabaseReference mahasiswaRef = databaseReference.child(databasePath).child(key);
                            mahasiswaRef.child("name").setValue(name);
                            mahasiswaRef.child("email").setValue(email);
                            mahasiswaRef.child("semester").setValue(1);

                            // Registration successful
                            // TODO change Toast to displayable text in user interface for both success and failed
                            Toast.makeText(getContext(), "Register User Successful", Toast.LENGTH_SHORT).show();
                            // TODO guide user to login page after register is done
                        } else {
                            // Registration failed
                            Toast.makeText(getContext(), "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
        ;}
}
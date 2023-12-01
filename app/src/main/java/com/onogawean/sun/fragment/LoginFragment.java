package com.onogawean.sun.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.android.material.textfield.TextInputEditText;
import com.onogawean.sun.R;

import com.onogawean.sun.activities.LoginRegisterActivity;
import com.onogawean.sun.fragment.RegisterFragment;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseAuth auth;


    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    protected static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ImageView loginViewButton = view.findViewById(R.id.ic_arrow_login);

        EditText emailText, passText;
        Button submitButton;
        emailText = view.findViewById(R.id.login_email);
        passText = view.findViewById(R.id.login_password);
        submitButton = view.findViewById(R.id.login_button);
        auth = FirebaseAuth.getInstance();

        submitButton.setOnClickListener(v ->{
            String email, pass;
            email = String.valueOf(emailText.getText());
            pass = String.valueOf(passText.getText());
            TextView emptyText = view.findViewById(R.id.emptyText);

            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
                emptyText.setVisibility(View.VISIBLE);
                return ;
            }else {
                loginUser(email, pass);
            }



        });

        loginViewButton.setOnClickListener(v -> {
            Fragment registerFragment = new RegisterFragment();

            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            //fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            fragmentTransaction.replace(R.id.login_register_fragment, registerFragment).commit();
        });
        return view;
    }
    public void loginUser(String email, String pass){
        auth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(requireActivity(), new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(getContext(), "Login telah Berhasil", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(requireActivity(), HomeFragment.class));
                getActivity().finish();
            }
        });
    }

}
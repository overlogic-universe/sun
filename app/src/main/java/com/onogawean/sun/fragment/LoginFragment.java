package com.onogawean.sun.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onogawean.sun.BuildConfig;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.onogawean.sun.R;

import com.onogawean.sun.activities.LoginRegisterActivity;
import com.onogawean.sun.activities.MainActivity;
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
    int variable = 0;
    GoogleSignInClient mGoogleSignInClient;


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
        TextView emptyText = view.findViewById(R.id.emptyText);
        ImageView login_google = view.findViewById(R.id.login_google);
        auth = FirebaseAuth.getInstance();
        String webClientId = BuildConfig.WEB_CLIENT_ID;


        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(webClientId)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this.getContext() , gso);
        auth = FirebaseAuth.getInstance();
        login_google.setOnClickListener(v -> {
            loginUser();
        });



        submitButton.setOnClickListener(v ->{
            String email, pass;
            email = String.valueOf(emailText.getText());
            pass = String.valueOf(passText.getText());

            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
                emptyText.setVisibility(View.VISIBLE);
                return ;
            }else {
                loginUser(email, pass);
                if(variable == 1){
                    emptyText.setText("Email tidak terdaftar");
                    emptyText.setVisibility(View.VISIBLE);
                } else if (variable == 2) {
                    emptyText.setText("Password tidak valid");
                    emptyText.setVisibility(View.VISIBLE);
                } else if (variable == 3) {
                    emptyText.setText("Login gagal");
                    emptyText.setVisibility(View.VISIBLE);
                }

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
        auth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener(requireActivity(), new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                variable = 0;
                Toast.makeText(getContext(), "Login telah Berhasil", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(requireActivity(), MainActivity.class));
                getActivity().finish();
            }
        })
                .addOnFailureListener(requireActivity(), new OnFailureListener() {
                    public void onFailure( Exception e) {
                        // Handle login failure
                        if (e instanceof FirebaseAuthInvalidUserException) {
                            variable = 1;
                            // User not registered
                            Toast.makeText(getContext(), "Email tidak terdaftar", Toast.LENGTH_SHORT).show();
                        } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            variable = 2;
                            // Invalid password
                             Toast.makeText(getContext(), "Password tidak valid", Toast.LENGTH_SHORT).show();
                        } else {
                            variable = 3;
                            // General error
                            Toast.makeText(getContext(), "Login gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private static final int RC_SIGN_IN = 9001;
    public void loginUser(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == RC_SIGN_IN){
            if (resultCode == Activity.RESULT_CANCELED) {
                // Handle the case where Google Sign-In was canceled by the user.
                Toast.makeText(getContext(), "Google Sign-In was canceled", Toast.LENGTH_SHORT).show();
            }else{
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);

                    googleFirebaseAuth(account.getIdToken());
                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    private void googleFirebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();
                    if(isNewUser) {
                        Toast.makeText(getContext(), "Account with this email is not exist, please register instead", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "Login success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(requireActivity(), MainActivity.class));
                        getActivity().finish();
                    }}
                else {
                    // Registration failed
                    Toast.makeText(getContext(), "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    }


package com.onogawean.sun.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onogawean.sun.R;
import com.onogawean.sun.activities.LoginRegisterActivity;
import com.onogawean.sun.activities.MainActivity;
import com.onogawean.sun.adapter.MatkulAdapter;

import java.util.ArrayList;

public class MataKuliahFragment extends Fragment {

    private RecyclerView matkulRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    ImageView matkulCard;
    TextView loginHint;
    FirebaseDatabase database;
    DatabaseReference mahasiswaRef, mataKuliahRef, absensiRef, serverStatus;
    FirebaseUser currentUser;
    public MataKuliahFragment() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mata_kuliah, container, false);
        matkulRecyclerView = view.findViewById(R.id.matkul_card_view);
        matkulCard = view.findViewById(R.id.matkul_card);
        layoutManager = new GridLayoutManager(requireContext(), 2);
        matkulRecyclerView.setLayoutManager(layoutManager);
        loginHint = view.findViewById(R.id.login_hint);

        database = FirebaseDatabase.getInstance();
        mahasiswaRef = database.getReference("mahasiswa");
        mataKuliahRef = database.getReference("mata_kuliah");
        absensiRef = database.getReference("absensi");
        serverStatus = database.getReference("server_status");

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = "";
        if (currentUser == null) {
            startActivity(new Intent(requireActivity(), LoginRegisterActivity.class));
            loginHint.setVisibility(View.VISIBLE);
        } else {
            loginHint.setVisibility(View.GONE);
            userEmail = currentUser.getEmail();
        }

        mahasiswaRef.orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.exists()) {
                        int semester = snapshot.child("semester").getValue(Integer.class);
                        mataKuliahRef.child("semester_" + semester).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ArrayList<String> mataKuliahList = (ArrayList<String>) snapshot.getValue();
                            MatkulAdapter matkulAdapter = new MatkulAdapter(mataKuliahList);
                            matkulRecyclerView.setAdapter(matkulAdapter);
                            matkulRecyclerView.setHasFixedSize(true);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("Firebase", "Error fetching data", error.toException());
                        }
                    });

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error fetching data", databaseError.toException());
            }
        });
        return view;
    }

}
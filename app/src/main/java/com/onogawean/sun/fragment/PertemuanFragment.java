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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onogawean.sun.R;
import com.onogawean.sun.activities.LoginRegisterActivity;
import com.onogawean.sun.adapter.DashboardAdapter;
import com.onogawean.sun.adapter.MatkulAdapter;
import com.onogawean.sun.adapter.PertemuanAdapter;

import java.util.ArrayList;


public class PertemuanFragment extends Fragment {
    private RecyclerView matkulRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    ImageView matkulCard;
    TextView loginHint;
    FirebaseDatabase database;
    DatabaseReference mahasiswaRef, mataKuliahRef, absensiRef, serverStatus;
    FirebaseUser currentUser;
    public PertemuanFragment() {
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

        ArrayList<String> pertemuanList = new ArrayList<>();

        for (int i = 1; i <= 14; i++) {
            pertemuanList.add(String.format("Pertemuan %d", i));
        }
        PertemuanAdapter matkulAdapter = new PertemuanAdapter(pertemuanList);
        matkulRecyclerView.setAdapter(matkulAdapter);
        matkulRecyclerView.setHasFixedSize(true);

        return view;
    }

}
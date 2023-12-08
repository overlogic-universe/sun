package com.onogawean.sun.fragment;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onogawean.sun.R;
import com.onogawean.sun.activities.MainActivity;

public class PresenceFragment extends Fragment {

    TextView jam_matkul, hint_matkul, btn_hadir, btn_izin, nama_matkul_card, pertemuan_matkul, dosen_matkul;
    String semester, matkul, pertemuan, nama_matkul;
    public PresenceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_presence, container, false);
        jam_matkul = view.findViewById(R.id.jam_matkul);
        hint_matkul = view.findViewById(R.id.absen_hint);
        btn_hadir = view.findViewById(R.id.btn_hadir);
        btn_izin = view.findViewById(R.id.btn_izin);
        nama_matkul_card = view.findViewById(R.id.nama_matkul);
        pertemuan_matkul = view.findViewById(R.id.perteman_matkul);
        dosen_matkul = view.findViewById(R.id.dosen_matkul);
        TextView[] textView = {
                jam_matkul,
                hint_matkul,
                btn_hadir,
                btn_izin,
                nama_matkul_card,
                pertemuan_matkul,
                dosen_matkul

        };

        for (TextView tv : textView) {
            setGradient(tv);
        }

        Bundle bundle = getArguments();
        if (bundle != null) {
            semester = bundle.getString("semester");
            matkul = bundle.getString("matakuliah");
            pertemuan = bundle.getString("pertemuan");
            nama_matkul = bundle.getString("nama_matkul");
        }

        nama_matkul_card.setText(nama_matkul);
        pertemuan_matkul.setText("Pertemuan Ke-" + pertemuan);
        dosen_matkul.setText("Tabelnya belum ada");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        btn_hadir.setOnClickListener((v) -> {
            assert currentUser != null;
            presence(currentUser.getEmail(), "masuk");
        });

        btn_izin.setOnClickListener((v) -> {
            assert currentUser != null;
            presence(currentUser.getEmail(), "izin");
        });

        return view;
    }

    void setGradient(TextView text) {
        TextPaint textPaint = new TextPaint();
        float width = textPaint.measureText(text.getText().toString());
        int startColor = Color.parseColor("#60F5D7");
        int endColor = Color.parseColor("#DBEA61");
        text.setTextColor(startColor);
        Shader shader = new LinearGradient(0, 0, width, text.getTextSize(), new int[] { startColor, endColor}, null , Shader.TileMode.CLAMP);
        text.getPaint().setShader(shader);
    }

    void presence(String email, String presence) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String databasePath = String.format("absensi/semester_%s/mk_%s/pertemuan_%s/%s/", semester, matkul, pertemuan, presence);
        DatabaseReference databaseReference = database.getReference();
        //User user = new User(email,)
        String key = databaseReference.push().getKey();
        databaseReference.child(databasePath).child(key).setValue(email);
        MataKuliahFragment mataKuliahFragment = new MataKuliahFragment();
        ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment, mataKuliahFragment)
                .commit();
    }

}
package com.onogawean.sun.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.play.core.integrity.p;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onogawean.sun.R;
import com.onogawean.sun.fragment.NoteFragment;
import com.onogawean.sun.fragment.PresenceFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PertemuanAdapter extends RecyclerView.Adapter<PertemuanAdapter.MyViewHolder> {
    private List<String> arr;
    public PertemuanAdapter(List<String> arr) {
        this.arr = arr;
    }
    FirebaseDatabase database;
    DatabaseReference mahasiswaRef, absensiRef, serverStatusRef;
    FirebaseAuth auth;
    String email, nama, nama_matkul;
    Integer pertemuan, semester;
    boolean presence;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matkul, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.matkulName.setText(arr.get(position));

        database = FirebaseDatabase.getInstance();
        mahasiswaRef = database.getReference("mahasiswa");
        serverStatusRef = database.getReference("server_status");
        absensiRef = database.getReference("absensi");
        email = auth.getInstance().getCurrentUser().getEmail();
        String userid = auth.getInstance().getUid();

/*        serverStatusRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pertemuan = snapshot.child("pertemuan").getValue(Integer.class);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mahasiswaRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.exists()) {
                        semester = dataSnapshot.child("semester").getValue(Integer.class);
                        nama = dataSnapshot.child("nama").getValue(String.class);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", "onCancelled: " + error);
            }
        });

        absensiRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot masuk = snapshot.child("semester_" + semester).child("mk_" + position).child("pertemuan_" + pertemuan).child("masuk");
                //DataSnapshot izin = snapshot.child("semseter_" + semester).child("mk_" + position).child("pertemuan_" + pertemuan).child("izin");
                Log.d("sa", "onDataChange: " + masuk.getChildrenCount());
                if (masuk.getChildrenCount() > 0) {
                    for (DataSnapshot emailSnapshot : masuk.getChildren()) {
                        if (emailSnapshot.getValue(String.class).equals(email)) {
                            holder.matkulCard.setImageResource(R.drawable.bg_gradient);
                            //presence = true;
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/




        setGradient(holder.matkulName);
        holder.matkulCard.setOnClickListener((v)->{
            Fragment noteFragment = new NoteFragment();
            Bundle bundle = new Bundle();

            bundle.putString("userid", userid);
            bundle.putString("matakuliah", nama_matkul);
            bundle.putString("pertemuan", String.format("%s", position));

            noteFragment.setArguments(bundle);

            ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_fragment, noteFragment)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView matkulCard;
        TextView matkulName, matkulCardName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            matkulCard = itemView.findViewById(R.id.matkul_card);
            matkulName = itemView.findViewById(R.id.matkul_card_name);
            matkulCardName = itemView.findViewById(R.id.nama_matkul);

        }
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
}

package com.onogawean.sun.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.onogawean.sun.R;

public class MatkulAdapter extends RecyclerView.Adapter<MatkulAdapter.MyViewHolder> {

    String arr[];

    public MatkulAdapter(String[] arr) {
        this.arr = arr;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matkul, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.matkulName.setText(arr[position]);
    }

    @Override
    public int getItemCount() {
        return arr.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView matkulName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            matkulName = itemView.findViewById(R.id.matkul_card_name);
        }
    }
}

package com.onogawean.sun.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onogawean.sun.R;
import com.onogawean.sun.adapter.MatkulAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MataKuliahFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MataKuliahFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MataKuliahFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MataKuliahFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MataKuliahFragment newInstance(String param1, String param2) {
        MataKuliahFragment fragment = new MataKuliahFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    private RecyclerView matkulRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    MatkulAdapter matkulAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mata_kuliah, container, false);
        matkulRecyclerView = view.findViewById(R.id.matkul_card_view);
        layoutManager = new GridLayoutManager(requireContext(), 2);
        matkulRecyclerView.setLayoutManager(layoutManager);
        matkulAdapter = new MatkulAdapter(new String[]{"PBO", "SO", "TBA", "PBO", "SO", "TBA", "PBO", "SO", "TBA"});

        matkulRecyclerView.setAdapter(matkulAdapter);
        matkulRecyclerView.setHasFixedSize(true);

        return view;
    }
}
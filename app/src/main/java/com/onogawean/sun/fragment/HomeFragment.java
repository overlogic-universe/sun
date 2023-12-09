package com.onogawean.sun.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.onogawean.sun.R;
import com.onogawean.sun.activities.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View view = inflater.inflate(R.layout.fragment_home, container, false);
        //LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_home, container,false);
        TextView mataKuliahButton = view.findViewById(R.id.btn_mata_kuliah);
        mataKuliahButton.setOnClickListener((v)->{
            if (getActivity() != null) {
                MainActivity mainActivity =  (MainActivity) getActivity();
                mainActivity.setFragment(MataKuliahFragment.class);
            }

        });
        //TextView titleBar = (TextView) view.findViewById(R.id.title_bar);
        //titleBar.setText("Home");
        TextView jadwalkuliahButton = view.findViewById(R.id.btn_jadwal_kuliah);
        jadwalkuliahButton.setOnClickListener((v)->{
            if (getActivity() != null) {
                MainActivity mainActivity =  (MainActivity) getActivity();
                mainActivity.setFragment(ModePengembang.class);
            }

        });
        TextView khsButton = view.findViewById(R.id.btn_kartu_hasil_studi);
        khsButton.setOnClickListener((v)->{
            if (getActivity() != null) {
                MainActivity mainActivity =  (MainActivity) getActivity();
                mainActivity.setFragment(ModePengembang.class);
            }

        });
        return view;
    }
}
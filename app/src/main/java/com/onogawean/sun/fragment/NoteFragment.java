package com.onogawean.sun.fragment;


import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.onogawean.sun.R;
import com.onogawean.sun.adapter.NoteAdapter;
import com.onogawean.sun.model.Note;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {


    EditText titleEditText,contentEditText;
    ImageButton CreatNote;

    RecyclerView recyclerView;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public NoteFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoteFragment.
     */
    // TODO: Rename and change types and number of parameters

    public static NoteFragment newInstance(String param1, String param2) {
        NoteFragment fragment = new NoteFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_note, container, false);
        titleEditText = view.findViewById(R.id.EditTitle);
        contentEditText = view.findViewById(R.id.EditContent);
        CreatNote = view.findViewById(R.id.addButton);
        CreatNote.setOnClickListener((v) -> CreateNote());


        return view;


    }


    private void CreateNote() {
        String noteTitle= titleEditText.getText().toString();
        String noteContent = contentEditText.getText().toString();
        if(noteTitle.isEmpty()){
            titleEditText.setError("Title is Required");
            return;
        }
        Note note= new Note();
        note.setTitle(noteTitle);
        note.setContent(noteContent);
        note.setTimestamp(Timestamp.now());
        saveNoteFirebase(note);

    }
    void saveNoteFirebase(Note note){
        DocumentReference documentReference;
        documentReference = UtilityNote.getCollectionRefNote().document();
        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    UtilityNote.showToast(getActivity(), "Note Added Successfully");
                    getActivity().finish();

                }
                else{
                    UtilityNote.showToast(getActivity(),"Note Failed to Added");
                }
            }
        });
    }

}
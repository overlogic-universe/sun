package com.onogawean.sun.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.onogawean.sun.R;
import com.onogawean.sun.adapter.ChatAdapter;
import com.onogawean.sun.model.Chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {
    RecyclerView recyclerView;
    EditText messageEditText;
    ImageButton sendButton;
    List<Chat> chatList;
    ChatAdapter chatAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
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
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_chat, container, false);;
        chatList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_view);
        messageEditText = view.findViewById(R.id.message_edit_text);
        sendButton = view.findViewById(R.id.send_btn);
        chatAdapter = new ChatAdapter(chatList);
        recyclerView.setAdapter(chatAdapter);
        LinearLayoutManager lm = new LinearLayoutManager (requireContext());
        lm.setStackFromEnd(true);
        recyclerView.setLayoutManager(lm);


        sendButton.setOnClickListener(v -> {
            String question = messageEditText.getText().toString().trim();
            addToChat(question,Chat.SENT_BY_ME);
            messageEditText.setText("");
            callRapid(question);
        });
        return view;
    }
    private void callRapid(String chat){
        Thread th = new Thread(()->{
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType,String.format("{ \"message\": \"%s\"}",chat));
            Request request = new Request.Builder()
                    .url("https://meta-llama-fast-api.p.rapidapi.com/mistralchat")
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("X-RapidAPI-Key", "")
                    .addHeader("X-RapidAPI-Host", "meta-llama-fast-api.p.rapidapi.com")
                    .build();

            try (Response response = client.newCall(request).execute()){
                addToChat(response.body().string(),Chat.SENT_BY_BOT);

            }catch (IOException e) {
                addToChat("Nahida sedang pusing",Chat.SENT_BY_BOT);

            }
        });
        th.start();

    }
    void addToChat(String chat,String sentBy){
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chatList.add(new Chat(chat,sentBy));
                chatAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(chatAdapter.getItemCount());
            }
        });



    }
}
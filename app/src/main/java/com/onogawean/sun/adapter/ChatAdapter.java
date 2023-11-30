package com.onogawean.sun.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.onogawean.sun.R;
import com.onogawean.sun.model.Chat;


import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter <ChatAdapter.MyViewHolder>{
    List<Chat>chatList;
    public ChatAdapter(List<Chat> messageList) {
        this.chatList = messageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat,null);
        MyViewHolder myViewHolder = new MyViewHolder(chatView);
        return myViewHolder;
    }

    //@Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Chat chat = chatList.get(position);
        if(chat.getSentBy().equals(Chat.SENT_BY_ME)){
            holder.leftChatView.setVisibility(View.GONE);
            holder.rightChatView.setVisibility((View.VISIBLE));
            holder.rightTextView.setText(chat.getMessage());
        }else{
            holder.rightChatView.setVisibility(View.GONE);
            holder.leftChatView.setVisibility((View.VISIBLE));
            holder.leftTextView.setText(chat.getMessage());

        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout leftChatView,rightChatView;
        TextView leftTextView,rightTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            leftChatView = itemView.findViewById(R.id.left_chat_view);
            rightChatView = itemView.findViewById(R.id.right_chat_view);
            leftTextView = itemView.findViewById(R.id.left_chat_text_view);
            rightTextView = itemView.findViewById(R.id.right_chat_text_view);
        }
    }
}

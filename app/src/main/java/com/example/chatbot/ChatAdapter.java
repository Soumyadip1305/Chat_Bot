package com.example.chatbot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<chatsModel> chatsModelsArrayList;
    Context context;

    public ChatAdapter(ArrayList<chatsModel> chatsModelsArrayList, Context context) {
        this.chatsModelsArrayList = chatsModelsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_holder, parent, false);
                return new userViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_reply, parent, false);
                return new botViewHolder(view);
        }
        return null;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        chatsModel chatsModel=chatsModelsArrayList.get(position);
        switch (chatsModelsArrayList.get(position).getSender()){
            case "user":
                ((userViewHolder)holder).userMsg.setText(chatsModel.getMessage());
                break;
            case "bot":
                ((botViewHolder)holder).botReply.setText(chatsModel.getMessage());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return chatsModelsArrayList.size();
    }
    @Override
    public int getItemViewType(int position){
        switch (chatsModelsArrayList.get(position).getSender()){
            case "user":
                return 0;
            case "bot":
                return 1;
            default:return -1;
        }
    }

    public static class userViewHolder extends RecyclerView.ViewHolder {
        TextView userMsg;

        public userViewHolder(@NonNull View itemView) {
            super(itemView);
            userMsg = itemView.findViewById(R.id.userMsg);
        }
    }

    public static class botViewHolder extends RecyclerView.ViewHolder {
        TextView botReply;

        public botViewHolder(@NonNull View itemView) {
            super(itemView);
            botReply = itemView.findViewById(R.id.bot_reply);
        }
    }
}

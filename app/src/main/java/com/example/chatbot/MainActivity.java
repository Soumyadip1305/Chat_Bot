package com.example.chatbot;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AppComponentFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText editText;
    ImageView imageView;
    ArrayList<chatsModel>chatsModelArrayList;
    ChatAdapter chatAdapter;
    private final String USER_KEY="user";
    private final String BOT_KEY="bot";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.chat_recycler);
        editText = findViewById(R.id.edit_msg);
        imageView = findViewById(R.id.sent_btn);
        chatsModelArrayList = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatsModelArrayList, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(chatAdapter);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Enter Your Message", Toast.LENGTH_SHORT).show();
                    return;
                }
                getResponse(editText.getText().toString());
                editText.setText(" ");
            }
        });
    }
    private void getResponse(String  message){
        chatsModelArrayList.add(new chatsModel(message,USER_KEY));
        chatAdapter.notifyDataSetChanged();
        String url="http://api.brainshop.ai/get?bid=170485&key=tXlfGq9Rkhbd33UP&uid=[uid]&msg\n="+message;
        String BASE_URL="http://api.brainshop.ai/";
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetroFitApi retroFitApi=retrofit.create(RetroFitApi.class);
        Call<MsgModel>call=retroFitApi.getMessage(url);
        call.enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {
                if (response.isSuccessful()){
                    MsgModel msgModel=response.body();
                    chatsModelArrayList.add(new chatsModel(msgModel.getCnt(),BOT_KEY));
                    chatAdapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(chatsModelArrayList.size()-1);
                }
            }
            @Override
            public void onFailure(Call<MsgModel> call, Throwable t) {
                chatsModelArrayList.add(new chatsModel("no response",BOT_KEY));
           chatAdapter.notifyDataSetChanged();
            }
        });
    }
}
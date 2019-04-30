package com.example.mac.firebasechat;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Date;

import adapter.ConvoAdapter;
import model.Message;

public class ChatActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ConvoAdapter mAdapter;
    LinearLayoutManager layoutManager;
    ArrayList<Message> listMessage = new ArrayList<>();

    EditText messageText;
    ImageButton envoiButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Liste des message test
        Message message1  = new Message();
        message1.setId_emmeteur("1");
        message1.setMessage("Bonjour");
        message1.setDate("2019-04-30 05:30:45");
        listMessage.add(message1);
        Message message2  = new Message();
        message2.setId_emmeteur("2");
        message2.setMessage("Bonjour, ça va?");
        message2.setDate("2019-04-30 05:31:05");
        listMessage.add(message2);
        Message message3  = new Message();
        message3.setId_emmeteur("1");
        message3.setMessage("Oui ça va tres biewn et vous?");
        message3.setDate("2019-04-30 05:32:10");
        listMessage.add(message3);
        Message message4  = new Message();
        message4.setId_emmeteur("2");
        message4.setMessage("Aussi super");
        message4.setDate("2019-04-30 05:33:09");
        listMessage.add(message4);
        Message message5  = new Message();
        message5.setId_emmeteur("1");
        message5.setMessage("Super cool");
        message5.setDate("2019-04-30 05:33:40");
        listMessage.add(message5);

        //instantioation du recyclerview

        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new ConvoAdapter(this, listMessage);
        recyclerView.setAdapter(mAdapter);

        messageText = findViewById(R.id.message);
        envoiButton = findViewById(R.id.btn_send);

        envoiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!messageText.getText().toString().equals("")){
                    Message message = new Message();
                    message.setMessage(messageText.getText().toString());
                    message.setId_emmeteur("1");
                    Date d = new Date();
                    CharSequence s  = DateFormat.format("yyyy-MM-dd HH:mm:ss", d.getTime());
                    message.setDate((String) s);
                    listMessage.add(message);
                    mAdapter.notifyDataSetChanged();
                    recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, mAdapter.getItemCount() - 1);
                    messageText.setText("");
                }
            }
        });
    }
}

package com.example.arman.chatapp30;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        final String RetrievedUsername = extras.getString("UsernameString");
        final String RetrievedChatroomName = extras.getString("ChatroomNameString");
        boolean isIncognito = extras.getBoolean("IncognitoBoolean", Boolean.TRUE);

        if (isIncognito = Boolean.TRUE){
            RetrievedUsername.equals("Not " + RetrievedUsername);
        } else {
            //Do nothing
        }

        final Firebase roomRef = new Firebase("https://boiling-heat-6636.firebaseio.com/messages/" + RetrievedChatroomName);
        final Firebase lastMessageRef = roomRef.child("lastMessage");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText chatEdit = (EditText) findViewById(R.id.ChatEditText);
                String chat = chatEdit.getText().toString();
                Message sentMessage = new Message(chat, RetrievedUsername);
                chatEdit.setText("");
                lastMessageRef.setValue(sentMessage);
            }
        });
        final ArrayList foodList = new ArrayList();
        ArrayAdapter<String> foodAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foodList);
        ListView foodView = (ListView) findViewById(R.id.ChatListView);
        foodView.setAdapter(foodAdapter);

        lastMessageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot Snapshot) {
                System.out.println(Snapshot.getValue());
                for (DataSnapshot postSnapshot: Snapshot.getChildren()) {
                    Message RetrievedMessage = postSnapshot.getValue(Message.class);
                    String RetrievedMessageStringForm = (RetrievedMessage.MessageToDisplay);
                    foodList.add(RetrievedMessageStringForm);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

}

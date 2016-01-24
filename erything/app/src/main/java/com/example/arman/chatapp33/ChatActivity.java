package com.example.arman.chatapp33;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;
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

        if (isIncognito = Boolean.TRUE) {
            final String RetrievedUsernameIncog = "";
            RetrievedUsernameIncog.equals("Not " + RetrievedUsername);
        } else {
            final String RetrievedUsernameIncog = "";
            RetrievedUsernameIncog.equals(RetrievedUsername);
        }
        final ArrayList ChatArray = new ArrayList();
        Firebase ref = new Firebase("https://boiling-heat-6636.firebaseio.com//" + RetrievedChatroomName);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());
                String LastMessage = new String();
                LastMessage.equals(snapshot.getValue());
                ChatArray.add(LastMessage);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        ListView chatView = (ListView) findViewById(R.id.ChatListView);
        BaseAdapter chatAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return 0;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return null;
            }
        };
        chatView.setAdapter(chatAdapter);

    }
        public void sendMessage(View view){
        String MessageSent = "";
            EditText ChatEdit = (EditText) findViewById(R.id.ChatEditText);
            final String Chat = ChatEdit.getText().toString();
            Bundle extras = getIntent().getExtras();
            final String RetrievedUsername = extras.getString("UsernameString");
            MessageSent = (RetrievedUsername + " @ " + System.currentTimeMillis() + ": " + Chat);
            String RetrievedChatroomName = extras.getString("ChatroomNameString");
            Firebase Ref = new Firebase("https://boiling-heat-6636.firebaseio.com/"+RetrievedChatroomName);
            ChatEdit.setText("");
            Ref.setValue(MessageSent);
    }



//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EditText chatEdit = (EditText) findViewById(R.id.ChatEditText);
//                String chat = chatEdit.getText().toString();
//                Message sentMessage = new Message(chat, RetrievedUsername);
//                chatEdit.setText("");
//                lastMessageRef.setValue(sentMessage);
//            }
//        });
    }


package com.example.arman.chatapp33;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {

    List<String> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        final String RetrievedUsername = extras.getString("UsernameString");
        final String RetrievedChatroomName = extras.getString("ChatroomNameString");
        setTitle(RetrievedChatroomName);
        //set up is done

        Firebase ref = new Firebase("https://boiling-heat-6636.firebaseio.com/"+RetrievedChatroomName);
        final ListView chatView = (ListView) findViewById(R.id.ChatListView);
        chatView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return messages.size();
            }

            @Override
            public Object getItem(int position) {
                return messages.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                if (convertView == null) {
                    convertView = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                }
                TextView message = (TextView) convertView.findViewById(android.R.id.text1);
                message.setText((String)getItem(position));
                message.setTextColor(Color.BLACK);

                return convertView;
            }
        });
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String s) {
                String LastMessage = snapshot.getValue(String.class);
                messages.add(LastMessage);
                BaseAdapter bad = (BaseAdapter) chatView.getAdapter();
                bad.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
        public void sendMessage(View view){
        String MessageSent = "";
            EditText ChatEdit = (EditText) findViewById(R.id.ChatEditText);
            final String Chat = ChatEdit.getText().toString();
            Bundle extras = getIntent().getExtras();
            final String RetrievedUsername = extras.getString("UsernameString");
            Date date = new Date(System.currentTimeMillis());
            DateFormat formatter = new SimpleDateFormat("HH:mm");
            String dateFormatted = formatter.format(date);
            MessageSent = (dateFormatted + " | " + RetrievedUsername + ": " + Chat);
            String RetrievedChatroomName = extras.getString("ChatroomNameString");
            Firebase Ref = new Firebase("https://boiling-heat-6636.firebaseio.com/"+RetrievedChatroomName);
            ChatEdit.setText("");
            Ref.push().setValue(MessageSent);

    }
}

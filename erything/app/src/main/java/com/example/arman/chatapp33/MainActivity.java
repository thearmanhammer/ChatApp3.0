package com.example.arman.chatapp33;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Switch;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);
        Firebase roomsRef = new Firebase("https://boiling-heat-6636.firebaseio.com");
        roomsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void enterChat(View view) {
        EditText getChatroomName = (EditText) findViewById(R.id.ChatroomEditText);
        final String chatroomname = getChatroomName.getText().toString();
        EditText getUsername = (EditText) findViewById(R.id.UsernameEditText);
        String username = getUsername.getText().toString();
        Switch getIncognito = (Switch) findViewById(R.id.IncognitoSwitch);
        Boolean incognito = Boolean.TRUE;
        incognito = getIncognito.isChecked();

        final Firebase Ref = new Firebase("https://boiling-heat-6636.firebaseio.com/" + chatroomname);
//        Ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    Ref.child(chatroomname);
//                }else{
//                    Ref.child(chatroomname).setValue("");
//                }
//            }
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                System.out.println("The read failed: " + firebaseError.getMessage());
//            }
//        });
        final String trueUsername;
        if(incognito == true){
            trueUsername = ("not "+username);
        } else if(incognito == false) {
            trueUsername = (username);
            Ref.push().setValue((username +" has entered."));
        } else {
            trueUsername = (username);
        }
        Intent goToChat = new Intent(MainActivity.this, ChatActivity.class);
        goToChat.putExtra("ChatroomNameString", chatroomname);
        goToChat.putExtra("UsernameString", trueUsername);
        MainActivity.this.startActivity(goToChat);

    }
}
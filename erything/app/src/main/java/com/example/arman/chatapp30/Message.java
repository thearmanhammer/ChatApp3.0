package com.example.arman.chatapp30;

/**
 * Created by Arman on 1/18/2016.
 */
public class Message {
    String chatContent;
    String Username;
    String UsernameAndTime;
    String MessageToDisplay;

    Message(String content, String username){
        chatContent.equals(content);
        Username.equals(username);
        UsernameAndTime.equals(Username + " @ " + System.currentTimeMillis());
        MessageToDisplay.equals(UsernameAndTime + ": " + chatContent);
    }
}

package model;

import java.util.ArrayList;

public class GroupChat {
    private ArrayList<Message> messages;

    public GroupChat(){
        this.messages = new ArrayList<Message>();
    }
    public void sendMessage(Message m){
        this.messages.add(m);
    }
}

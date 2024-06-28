package model;

import java.util.LinkedList;

public class PrivateChat extends Chat  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User receiver;

    public PrivateChat(){
        super(new LinkedList<Message>());
    }

    public User getReceiver(){
        return this.receiver;
    }

    public void setReceiver(User user){
        this.receiver = user;
    }
}

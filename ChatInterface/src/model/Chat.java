package model;

import java.io.Serializable;
import java.util.List;

public abstract class Chat implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Message> messages;
    
    protected Chat(List<Message> msgs){
        this.messages = msgs;
    }

    public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public void sendMessage(Message message){
        this.messages.add(0, message);
    }

    
}

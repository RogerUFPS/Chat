package model;

import java.rmi.Remote;
import java.util.ArrayList;

public interface ChatInterface extends Remote{
	public void sendMessage(Message m, User receiver);
	public boolean createUser(User newUser, ChatsObserver obs);
	public ArrayList<PrivateChat> getPrivateChats(User u);
}

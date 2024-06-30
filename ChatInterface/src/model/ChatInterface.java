package model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface ChatInterface extends Remote {
	public void sendMessage(Message m, User receiver) throws RemoteException;
	public boolean createUser(User newUser, ChatsObserver obs) throws RemoteException;
	public ArrayList<PrivateChat> getPrivateChats(User u) throws RemoteException;
	public void disconnectUser(User u, String ip, int puerto) throws RemoteException;
	public List<User> loadActiveUsers() throws RemoteException;
}

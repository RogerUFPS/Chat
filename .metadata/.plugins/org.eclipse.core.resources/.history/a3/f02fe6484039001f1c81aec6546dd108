package model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatsObserver extends Remote {
	public String getIP() throws RemoteException;
	public int getPort() throws RemoteException;
	public void receiveGroupMessage(Message m) throws RemoteException;
	public void receiveDirectMessage(Message m) throws RemoteException;
	public User getUser() throws RemoteException;
	public void updateDisplay() throws RemoteException;
	public void updateOnlineUsers() throws RemoteException;
}

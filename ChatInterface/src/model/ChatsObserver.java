package model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatsObserver extends Remote {
	public int getPort() throws RemoteException;;
	public void receiveGroupMessage(Message m) throws RemoteException;
	public void receiveDirectMessage(Message m, User sender) throws RemoteException;
}

package model;

import java.io.Serializable;
import java.rmi.RemoteException;

public interface ChatsObserver extends Serializable{
	public void receiveGroupMessage(Message m) throws RemoteException;
	public void receiveDirectMessage(Message m, User sender) throws RemoteException;
}

package model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatsObserver extends Remote {
	
	//Estos son los metodos que el cliente usara como server.
	//Es decir lo que el server principal podra usar del cliente
	public String getIP() throws RemoteException;
	public int getPort() throws RemoteException;
	public void receiveGroupMessage(Message m) throws RemoteException;
	public void receiveDirectMessage(Message m) throws RemoteException;
	public User getUser() throws RemoteException;
	public void updateMainChatDisplay() throws RemoteException;
	public void updateChatDisplay() throws RemoteException;
}

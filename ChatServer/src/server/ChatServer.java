package server;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import dao.ChatDAO;
import dao.UserDAO;
import model.ChatInterface;
import model.ChatsObserver;
import model.Message;
import model.PrivateChat;
import model.User;

import java.net.*;

public class ChatServer extends UnicastRemoteObject implements ChatInterface {

	public static void main(String[] args) throws Exception {
		(new ChatServer()).startServer();
	}

	private static final long serialVersionUID = 9L;
	private final int PUERTO = 1811;
	private LinkedList<ChatsObserver> suscriptores;

	public ChatServer() throws RemoteException {
		suscriptores = new LinkedList();
	}

	public void startServer() {
		try {
			String dirIP = (InetAddress.getLocalHost()).toString();
			System.out.println("Ecuchando en.. " + dirIP + ":" + PUERTO);
			Registry registry = LocateRegistry.createRegistry(PUERTO);
			registry.bind("server", (ChatInterface) this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean createUser(User newUser, ChatsObserver obs) throws RemoteException{
		System.out.println("Intentó ejecutar createUser de ChatServer");
		UserDAO dao = new UserDAO();
		boolean success = dao.addUser(newUser);
		System.out.println("Pero acá no llegó");
		if (success)
			this.suscriptores.add(obs);

		return success;
	}

	@Override
	public ArrayList<PrivateChat> getPrivateChats(User u) throws RemoteException{
		ChatDAO dao = new ChatDAO();
		return dao.getPrivateChat(u);
	}

	@Override
	public void sendMessage(Message m, User receiver) throws RemoteException{
		ChatDAO dao = new ChatDAO();
		dao.sendMessage(m, receiver);
		if (receiver == null) {
			this.suscriptores.forEach((suscriptor) -> {
				try {
					suscriptor.receiveGroupMessage(m);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}
	}

}

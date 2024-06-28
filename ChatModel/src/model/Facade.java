package model;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Facade implements ChatsObserver{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User me;
	private ChatInterface serverObj;
	private GroupChat groupchat;
	private ArrayList<PrivateChat> privateChats;

	private static Facade instance;
	private UIChatInterface controller;
	
	private Facade() {
		groupchat = new GroupChat();
		privateChats = new ArrayList<PrivateChat>();
		String serverAddress = "localhost";
		int serverPort = 1811;
		try {
			Registry registry = LocateRegistry.getRegistry(serverAddress, serverPort);
			serverObj = (ChatInterface)(registry.lookup("server"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Facade getInstance() {
		if(instance==null) {
			instance = new Facade();
		} 
		return instance;
	}

	public GroupChat getGroupChat(){
		return this.groupchat;
	}

	public User getUser(){
		return this.me;
	}
	
	public boolean createUser(String name) {
		this.me = new User(name);
		boolean success =false;
		try {
			success = serverObj.createUser(this.me, this);
		} catch (RemoteException e) {
			e.printStackTrace();
			System.out.println("ACA");
		}
		if(success)
			getPrivateChats();
		return success;
	}

	public ChatInterface getServerObj() {
		return serverObj;
	}

	public void setServerObj(ChatInterface serverObj) {
		this.serverObj = serverObj;
	}

	public void sendMessage(String message, User receiver) {
		Message msg = new Message();
		msg.setMessage(message);
		msg.setSender(this.me);
		try {
			serverObj.sendMessage(msg, receiver);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<PrivateChat> getPrivateChatsList() {
		return privateChats;
	}
	
	private void getPrivateChats() {
		try {
			privateChats = serverObj.getPrivateChats(this.me);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void receiveGroupMessage(Message m) {
		this.groupchat.sendMessage(m);
	}

	private PrivateChat searchPrivateChat(User u) {
		for (PrivateChat p : privateChats) {
			if (p.getReceiver().equals(u)) {
				return p;
			}
		}
		return null;
	}
	
	public void updateDisplay() {
		controller.updateDisplay();
	}
	
	public void setController(UIChatInterface controller) {
		this.controller = controller;
	}

	@Override
	public void receiveDirectMessage(Message m, User sender) {
		PrivateChat chat = searchPrivateChat(sender);
		chat.sendMessage(m);
		updateDisplay();
	}

}

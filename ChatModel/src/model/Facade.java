package model;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import model.*;

public class Facade implements ChatsObserver {
	private User me;
	private ChatInterface serverObj;
	private GroupChat groupchat;
	private ArrayList<PrivateChat> privateChats;

	public Facade() {
		groupchat = new GroupChat();
		privateChats = new ArrayList<PrivateChat>();

		String serverAddress = "localhost";
		int serverPort = 1811;
		try {
			Registry registry = LocateRegistry.getRegistry(serverAddress, serverPort);
			serverObj = (ChatInterface) (registry.lookup("server"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean createUser(String name) {
		this.me = new User(name);
		return serverObj.createUser(this.me, (ChatsObserver)this);
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
		serverObj.sendMessage(msg, receiver);
	}

	private void getPrivateChats() {
		privateChats = serverObj.getPrivateChats(this.me);
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

	@Override
	public void receiveDirectMessage(Message m, User sender) {
		PrivateChat chat = searchPrivateChat(sender);
		chat.sendMessage(m);
	}

}

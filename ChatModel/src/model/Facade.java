package model;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import controller.ChatWindowController;
import javafx.application.Platform;

public class Facade extends UnicastRemoteObject implements ChatsObserver{
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
	private ChatWindowController controlador;
	private int serverPort, clientPort; 
	
	private Facade() throws RemoteException{
		super();
		groupchat = new GroupChat();
		privateChats = new ArrayList<PrivateChat>();
		
		String serverAddress ="";
		try {
			serverAddress = (InetAddress.getLocalHost()).getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		serverPort = 1811;
		clientPort = 2812;
		
		try {
			Registry registry = LocateRegistry.getRegistry(serverAddress, serverPort);
			serverObj = (ChatInterface)(registry.lookup("server"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Facade getInstance() {
		if(instance==null) {
			try {
				instance = new Facade();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		me.setOnline(true);
		boolean success =false;
		try {
			serverUp();
			success = serverObj.createUser(this.me, (ChatsObserver)this);
		} catch (RemoteException e) {
			e.printStackTrace();
		} 
		
		if(success)
			getPrivateChats();
		return success;
	}

	private void serverUp(){
		Thread t = new Thread(() -> {
			try {
				Registry r = LocateRegistry.createRegistry(getPort());
				r.bind("client", (ChatsObserver)this);
			}catch(Exception e) {
				e.printStackTrace();
			}
		});
		
		t.start();
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
		DataTransfer.getInstance().setChat(groupchat);
		updateDisplay();
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
		//controller.updateDisplay();
		controlador.updateDisplay();
	}
	
	public void setController(UIChatInterface controller) {
		this.controller = controller;
	}

	public void setController2(ChatWindowController c){
		this.controlador = c;
	}

	@Override
	public void receiveDirectMessage(Message m, User sender) {
		PrivateChat chat = searchPrivateChat(sender);
		chat.sendMessage(m);
		updateDisplay();
	}

	@Override
	public int getPort() {
		return clientPort;
	}

}

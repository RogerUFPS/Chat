package model;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controller.ChatWindowController;
import controller.MainChatWindows;
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
	private String clientAddress, serverAddress;
	
	private Facade() throws RemoteException{
		super();
		groupchat = new GroupChat();
		privateChats = new ArrayList<PrivateChat>();
		
		serverAddress = "26.193.129.51";
		try {
			clientAddress = (InetAddress.getLocalHost()).getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		serverPort = 1811;
		clientPort = 2813;
		
		
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

	public List<User> loadActiveUsers(){
		try{
			
			List<User> list = serverObj.loadActiveUsers();
			list.remove(this.me);
			return list;
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
	}

	public GroupChat getGroupChat(){
		return this.groupchat;
	}

	@Override
	public User getUser(){
		return this.me;
	}
	
	public void disconnectUser() {
		try {
			serverObj.disconnectUser(me);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
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
		t.interrupt();
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
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		msg.setDate(timestamp);
		try {
			if(receiver != null) {
				Chat c = searchPrivateChat(receiver);
				c.sendMessage(msg);
				DataTransfer.getInstance().setChat(c);
				updateDisplay();				
			} 
			serverObj.sendMessage(msg, receiver);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<PrivateChat> getPrivateChatsList() {
		getPrivateChats();
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
		System.out.println("Buscando chat privado con " + u.getUsername() + " (linea 173 facade)");
		for (PrivateChat p : privateChats) {
			System.out.println("Iterando por chat privado con " + p.getReceiver().getUsername() + " (linea 175)");
			if (p.getReceiver().equals(u)) {
				return p;
			}
		}
		return null;
	}
	
	public void updateDisplay() {
		if(controller !=null) {
			Platform.runLater(() -> controller.updateDisplay());
		}
		if(controlador!=null) {			
			Platform.runLater(() -> controlador.updateDisplay());
		}
	}
	
	public void setController(MainChatWindows controller) {
		this.controller = controller;
	}

	public void setController2(ChatWindowController c){
		this.controlador = c;
	}

	@Override
	public void receiveDirectMessage(Message m) {
		PrivateChat chat = searchPrivateChat(m.getSender());
		chat.sendMessage(m);
		DataTransfer.getInstance().setChat(chat);
		updateDisplay();
	}

	@Override
	public int getPort() throws RemoteException{
		return clientPort;
	}

	@Override
	public String getIP() throws RemoteException {
		return clientAddress;
	}

}

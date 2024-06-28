package model;

public class DataTransfer {

	private static DataTransfer instance;
	private Chat chat;
	
	
	private DataTransfer() {}
	
	public static DataTransfer getInstance() {
		if(instance==null) {
			instance = new DataTransfer();
		}
		return instance;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public static void setInstance(DataTransfer instance) {
		DataTransfer.instance = instance;
	}
	
	
	
}

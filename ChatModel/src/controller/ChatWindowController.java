package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import model.Chat;
import model.DataTransfer;
import model.Facade;
import model.GroupChat;
import model.Message;
import model.PrivateChat;
import model.UIChatInterface;
import model.User;

public class ChatWindowController implements UIChatInterface {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private GridPane chatGrid;

	@FXML
	private Label chat_LBL;

	@FXML
	private TextArea chat_TXT_A;

	@FXML
	private ScrollPane scrollChat;

	private Chat chat;

	private Facade f;

	private int nRow = 0;

	@FXML
	void sendMessage(ActionEvent event) {
		String msg = chat_TXT_A.getText().trim();
		if (msg.isBlank())
			return;
		User receiver = null;
		if (chat instanceof PrivateChat) {
			PrivateChat pc = (PrivateChat) chat;
			receiver = pc.getReceiver();
		}

		f.sendMessage(msg, receiver);
		updateDisplay();
	}

	@FXML
	void initialize() {
		assert chatGrid != null : "fx:id=\"chatGrid\" was not injected: check your FXML file 'ChatWindow.fxml'.";
		assert chat_LBL != null : "fx:id=\"chat_LBL\" was not injected: check your FXML file 'ChatWindow.fxml'.";
		assert chat_TXT_A != null : "fx:id=\"chat_TXT_A\" was not injected: check your FXML file 'ChatWindow.fxml'.";
		assert scrollChat != null : "fx:id=\"scrollChat\" was not injected: check your FXML file 'ChatWindow.fxml'.";

		setChat();
		f = Facade.getInstance();
		f.setController2(this);
        scrollChat.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		//loadDisplay();
	}

	private void setChat() {
		chat = DataTransfer.getInstance().getChat();
	}

	@Override
	public void updateDisplay() {
		GridPane msg = new GridPane();
		Label text;

		Message m = chat.getMessages().get(chat.getMessages().size()-1);

		if (m.getSender().equals(f.getUser())) {
			msg.setAlignment(Pos.BASELINE_LEFT);
		} else {
			msg.setAlignment(Pos.BASELINE_RIGHT);
		}
		//Esto no lo encuentra
		//m.getDate().toString() + " " + m.getSender() + ":" 
		
		text = new Label(m.getMessage());

		text.setFont(new Font("Arial", 12));
		text.setPrefHeight(20);
		text.setMinHeight(20);
		text.setMaxHeight(20);

		msg.setStyle("-fx-border-color: transparent transparent black transparent; -fx-border-width: 2px;");
		msg.add(text, 0, nRow);
		chatGrid.add(msg, 0, nRow);
		nRow++;
	}

	public void loadDisplay(){
        
    	for(Message h: chat.getMessages()) {
    		GridPane msg = new GridPane();
    	    Label text;

            text = new Label(h.getMessage());
    	
            if(chat.getMessages().get(chat.getMessages().size()-1).getSender().equals(f.getUser())){    		
                msg.setAlignment(Pos.BASELINE_LEFT);
            } else {
                msg.setAlignment(Pos.BASELINE_RIGHT);
            }
            
            text.setFont(new Font("Arial", 24));
            text.setPrefHeight(20);            
            text.setMinHeight(20);
            text.setMaxHeight(20);
    	
            msg.setStyle("-fx-border-color: transparent transparent black transparent; -fx-border-width: 2px;");
            msg.add(text, 0, nRow);
            chatGrid.add(msg, 0, nRow);
            nRow++;
    	}
    	
    }

}

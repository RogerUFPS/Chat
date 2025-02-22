package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Chat;
import model.DataTransfer;
import model.Facade;
import model.Message;
import model.PrivateChat;
import model.UIChatInterface;
import model.User;

public class MainChatWindows implements UIChatInterface {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label Chat_LBL;

	@FXML
	private GridPane gridChats;

	@FXML
	private GridPane gridOnline;

	@FXML
	private ScrollPane scrollOnline;

	@FXML
	private ScrollPane scrollChat;

	private int nRow = 0;
	private int nRowC = 0;

	private Facade f;

	private DataTransfer dt;

	void cargarGeneral() {
		GridPane chat;
		Label nombreChat;
		chat = new GridPane();
		nombreChat = new Label("Chat general");
		nombreChat.setFont(new Font("Cascadia Code", 14));
		nombreChat.setPrefHeight(60);
		nombreChat.setMinHeight(60);
		nombreChat.setMaxHeight(60);
		nombreChat.setStyle("-fx-text-fill: #39FFFA;");
		chat.setAlignment(Pos.CENTER);
		chat.setStyle("-fx-border-color: transparent transparent #39FFFA transparent; -fx-border-width: 1px;");
		chat.setOnMouseClicked(e -> {
			ejecutarChat(f.getGroupChat(), e);
		});

		chat.add(nombreChat, 0, 0);
		gridChats.add(chat, 0, nRow);
		nRow++;
	}

	public PrivateChat obtenerChat(User n) {
		PrivateChat nuevo = null;
		for (PrivateChat p : f.getPrivateChatsList()) {
			if (p.getReceiver().equals(n)) {
				nuevo = p;
				return nuevo;
			}
		}
		if (nuevo == null) {
			nuevo = new PrivateChat();
			nuevo.setReceiver(n);
		}
		return nuevo;
	}

	void ejecutarChat(Chat c, Event e) {
		dt.setChat(c);
		Node node = (Node) e.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ChatWindow.fxml"));
			Pane root = loader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	void cargarConectados() {
		GridPane user;
		Label username;
		for (User s : f.loadActiveUsers()) {
			user = new GridPane();
			username = new Label(s.getUsername());
			username.setFont(new Font("Cascadia Code", 12));
			username.setPrefHeight(20);
			username.setMinHeight(20);
			username.setMaxHeight(20);
			username.setStyle("-fx-text-fill: #39FFFA;");
			user.setPrefHeight(20);
			user.setMaxHeight(20);
			user.setMinHeight(20);
			username.setAlignment(Pos.CENTER);
			username.setWrapText(true);
			user.setStyle("-fx-border-color: transparent transparent #39FFFA transparent; -fx-border-width: 2px;");
			user.setOnMouseClicked(e -> {
				ejecutarChat(obtenerChat(s), e);
			});
			user.add(username, 0, 0);
			gridOnline.add(user, 0, nRowC++);
		}
	}

	void cargarChats() {
		ArrayList<PrivateChat> pc = f.getPrivateChatsList();
		GridPane chat;
		Label nombrePersona;

		//Uso bublesort para organizar chats dependiendo de cual es el mensaje que llego despues
		for (int i = 0; i < pc.size(); i++) {
		    for (int j = 0; j < pc.size() - 1 - i; j++) {
		        PrivateChat chat1 = pc.get(j);
		        PrivateChat chat2 = pc.get(j + 1);
		        if (!chat1.getReceiver().getUsername().equals("null") && 
		            !chat2.getReceiver().getUsername().equals("null") && 
		            chat1.getMessages().size() > 0 && 
		            chat2.getMessages().size() > 0) {

		            Message lastMessageChat1 = chat1.getMessages().get(0); 
		            Message lastMessageChat2 = chat2.getMessages().get(0); 
		            
		            int comparisonResult = lastMessageChat1.compareTo(lastMessageChat2);

		            if (comparisonResult < 0) {
		                PrivateChat temp = pc.get(j);
		                pc.set(j, pc.get(j + 1));
		                pc.set(j + 1, temp);
		            }
		        }
		    }
		}

		for (PrivateChat a : pc) {
			if (!a.getReceiver().getUsername().equals("null")) {
				chat = new GridPane();
				nombrePersona = new Label(a.getReceiver().getUsername());
				nombrePersona.setFont(new Font("Cascadia code", 14));
				nombrePersona.setPrefHeight(60);
				nombrePersona.setMinHeight(60);
				nombrePersona.setMaxHeight(60);
				nombrePersona.setStyle("-fx-text-fill: #39FFFA;");
				nombrePersona.setWrapText(true);
				chat.setAlignment(Pos.CENTER);
				chat.setStyle("-fx-border-color: transparent transparent #39FFFA transparent; -fx-border-width: 1px;");
				chat.setOnMouseClicked(e -> {
					ejecutarChat((Chat) a, e);
				});
				chat.add(nombrePersona, 0, 0);
				gridChats.add(chat, 0, nRow);
				nRow++;
			}
		}
	}

	@Override
	public void updateDisplay() {
		gridChats.getChildren().clear();
		gridOnline.getChildren().clear();
		cargarGeneral();
		cargarChats();
		cargarConectados();
	}

	@FXML
	void initialize() {
		assert Chat_LBL != null : "fx:id=\"Chat_LBL\" was not injected: check your FXML file 'MainChatWindow.fxml'.";
		assert gridChats != null : "fx:id=\"gridChats\" was not injected: check your FXML file 'MainChatWindow.fxml'.";
		assert gridOnline != null
				: "fx:id=\"gridOnline\" was not injected: check your FXML file 'MainChatWindow.fxml'.";
		assert scrollChat != null
				: "fx:id=\"scrollChat\" was not injected: check your FXML file 'MainChatWindow.fxml'.";
		scrollChat.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollOnline.setHbarPolicy(ScrollBarPolicy.NEVER);
		f = Facade.getInstance();
		dt = DataTransfer.getInstance();
		f.setMainChatController(this);
		cargarGeneral();
		cargarChats();
		cargarConectados();
	}

}

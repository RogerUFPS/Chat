package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class MainChatWindows{

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
    private ScrollPane scrollChat;
    
    private static int nRow;

    void createChat(String nombreChat) {
    	int miNumero = nRow;
    	GridPane chat = new GridPane();
    	Label nombrePersona = new Label(nRow+"");
    	nombrePersona.setFont(new Font("Arial", 24));
    	
    	nombrePersona.setPrefHeight(60);            
    	nombrePersona.setMinHeight(60);
    	nombrePersona.setMaxHeight(60);
    	chat.setAlignment(Pos.CENTER);
    	
    	chat.setStyle("-fx-border-color: transparent transparent black transparent; -fx-border-width: 2px;");
    
    	chat.setOnMouseClicked(e -> {
            System.out.println("Hola, sabes, oprimiste a " + miNumero);
        });
    	scrollChat.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);    	 
    	chat.add(nombrePersona, 0, 0);
    	gridChats.add(chat, 0, nRow);
    	nRow++;
    }
    
    
    
    @FXML
    void initialize() {
        assert Chat_LBL != null : "fx:id=\"Chat_LBL\" was not injected: check your FXML file 'MainChatWindow.fxml'.";
        assert gridChats != null : "fx:id=\"gridChats\" was not injected: check your FXML file 'MainChatWindow.fxml'.";
        assert gridOnline != null : "fx:id=\"gridOnline\" was not injected: check your FXML file 'MainChatWindow.fxml'.";
        assert scrollChat != null : "fx:id=\"scrollChat\" was not injected: check your FXML file 'MainChatWindow.fxml'.";

    }

}

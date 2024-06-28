package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.DataTransfer;
import model.Facade;
import model.PrivateChat;
import model.UIChatInterface;

public class MainChatWindows implements UIChatInterface{

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
        
    private int nRow = 0;
    
    private Facade f;
    
    private DataTransfer dt;

    void cargarGeneral() {
    	GridPane chat;
    	Label nombrePersona;
    	chat = new GridPane();
    	nombrePersona = new Label("Chat general");
    	nombrePersona.setFont(new Font("Arial", 24));
    	nombrePersona.setPrefHeight(60);            
    	nombrePersona.setMinHeight(60);
    	nombrePersona.setMaxHeight(60);
    	chat.setAlignment(Pos.CENTER);
    	
    	chat.setStyle("-fx-border-color: transparent transparent black transparent; -fx-border-width: 2px;");
    	chat.setOnMouseClicked(e -> {
    		 dt.setChat(f.getGroupChat());
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
        });
    	
    	scrollChat.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);    	 
    	chat.add(nombrePersona, 0, 0);
    	gridChats.add(chat, 0, nRow);
    	nRow++;
    }
    
    void cargarChats() {
    	ArrayList<PrivateChat> pc = f.getPrivateChatsList();
    	GridPane chat;
    	Label nombrePersona;
    	
    	for(PrivateChat a: pc) {
    		chat = new GridPane();
        	nombrePersona = new Label(a.getReceiver().getUsername());
        	nombrePersona.setFont(new Font("Arial", 24));
        	nombrePersona.setPrefHeight(60);            
        	nombrePersona.setMinHeight(60);
        	nombrePersona.setMaxHeight(60);
        	chat.setAlignment(Pos.CENTER);
        	
        	chat.setStyle("-fx-border-color: transparent transparent black transparent; -fx-border-width: 2px;");
        
        	chat.setOnMouseClicked(e -> {
                dt.setChat(a);
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
            });
        	scrollChat.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);    	 
        	chat.add(nombrePersona, 0, 0);
        	gridChats.add(chat, 0, nRow);
        	nRow++;
    	}
    }
    
    @Override
	public void updateDisplay() {
    	
    	
    	
	}
    
    
    
    @FXML
    void initialize() {
        assert Chat_LBL != null : "fx:id=\"Chat_LBL\" was not injected: check your FXML file 'MainChatWindow.fxml'.";
        assert gridChats != null : "fx:id=\"gridChats\" was not injected: check your FXML file 'MainChatWindow.fxml'.";
        assert gridOnline != null : "fx:id=\"gridOnline\" was not injected: check your FXML file 'MainChatWindow.fxml'.";
        assert scrollChat != null : "fx:id=\"scrollChat\" was not injected: check your FXML file 'MainChatWindow.fxml'.";
        
        f = Facade.getInstance();
        dt = DataTransfer.getInstance();
        cargarGeneral();
        cargarChats();
        
    }

}

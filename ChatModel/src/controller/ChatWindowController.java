package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class ChatWindowController ilmepments CurrentChatInterface{

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
    void initialize() {
        assert chatGrid != null : "fx:id=\"chatGrid\" was not injected: check your FXML file 'ChatWindow.fxml'.";
        assert chat_LBL != null : "fx:id=\"chat_LBL\" was not injected: check your FXML file 'ChatWindow.fxml'.";
        assert chat_TXT_A != null : "fx:id=\"chat_TXT_A\" was not injected: check your FXML file 'ChatWindow.fxml'.";

    }

}

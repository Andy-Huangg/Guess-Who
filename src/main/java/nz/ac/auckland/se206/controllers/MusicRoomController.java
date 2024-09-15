package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.ChatHandler;

public class MusicRoomController {
  @FXML private Rectangle rectAlfred;
  @FXML private TextArea txtChat;
  @FXML private TextField txtInput;
  @FXML private Button btnSend;

  public void initialize() {}

  @FXML
  private void handleAlfredInteraction(MouseEvent event) {
    enableChat();
    txtChat.appendText(ChatHandler.setCharacter("alfred"));
  }

  @FXML
  private void enableChat() {
    txtChat.setVisible(true);
    txtInput.setVisible(true);
    btnSend.setVisible(true);
  }

  @FXML
  private void disableChat() {
    txtChat.setVisible(false);
    txtInput.setVisible(false);
    btnSend.setVisible(false);
  }
}

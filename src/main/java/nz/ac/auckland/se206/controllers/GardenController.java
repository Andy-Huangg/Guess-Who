package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class GardenController {
  @FXML private Rectangle rectBruce;
  @FXML private TextArea txtChat;
  @FXML private TextField txtInput;
  @FXML private Button btnSend;

  public void initialize() {}

  @FXML
  private void handleRectangleClick(MouseEvent event) throws IOException {
    enableChat();
    Rectangle clickedRectangle = (Rectangle) event.getSource();
    MainLayoutController.getContext().handleRectangleClick(event, clickedRectangle.getId());
    // txtChat.appendText(ChatHandler.setCharacter("bruce"));
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

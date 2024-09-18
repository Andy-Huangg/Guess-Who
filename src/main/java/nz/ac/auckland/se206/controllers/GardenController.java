package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.apiproxy.chat.openai.ChatMessage;
import nz.ac.auckland.se206.ChatHandler;

public class GardenController implements ChatSceneController {
  @FXML private Rectangle rectBruce;
  @FXML private TextArea txtChat;
  @FXML private TextField txtInput;
  @FXML private Button btnSend;

  private ChatHandler chatHandler;

  public void initialize() {
    chatHandler = new ChatHandler("Bruce");
  }

  @FXML
  public void onSendMessage(ActionEvent event) {
    String message = txtInput.getText().trim();
    if (message.isEmpty()) {
      return;
    }
    chatHandler.sendMessage(message, this);
    txtInput.clear();
  }

  @Override
  public void appendChatMessage(ChatMessage msg) {
    Platform.runLater(() -> txtChat.appendText(msg.getContent() + "\n"));
  }

  @FXML
  private void handleRectangleClick(MouseEvent event) throws IOException, InterruptedException {
    enableChat();
    chatHandler.setCharacter("Bruce");
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

package nz.ac.auckland.se206.controllers;

import java.nio.file.Paths;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import nz.ac.auckland.apiproxy.chat.openai.ChatMessage;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.ChatHandler;

public abstract class ChatSceneController {
  @FXML protected TextArea txtChat;
  @FXML protected TextField txtInput;
  @FXML protected Button btnSend;

  protected ChatHandler chatHandler;
  protected boolean isInteracted = false;
  protected MediaPlayer mediaPlayer;

  // Common initialization logic
  public void initialize(String characterName) {
    chatHandler = new ChatHandler(characterName);
    txtInput.setOnKeyPressed(
        event -> {
          if (event.getCode() == KeyCode.ENTER) {
            try {
              onSendMessage(null); // You can pass null since the ActionEvent is not needed here
            } catch (ApiProxyException e) {
              e.printStackTrace(); // Handle your exception
            }
          }
        });
  }

  @FXML
  public void onSendMessage(ActionEvent event) throws ApiProxyException {
    String message = txtInput.getText().trim();
    if (message.isEmpty()) {
      return;
    }

    if (!isInteracted) {
      isInteracted = true;
      setInteractedFlag(true); // Calls the subclass-specific method to set interaction flag
    }

    chatHandler.sendMessage(message, this);
    txtChat.clear();
    txtInput.clear();
  }

  // Abstract method for subclass to implement setting interaction flag
  protected abstract void setInteractedFlag(boolean interacted);

  public void playIntroAudio(String audioFileName) {
    System.out.println(mediaPlayer);
    if (mediaPlayer != null) {
      mediaPlayer.stop(); // Stop current audio
      mediaPlayer.dispose(); // Release resources held by the current media player
    }
    String audioFilePath = "src/main/resources/sounds/" + audioFileName;
    Media media = new Media(Paths.get(audioFilePath).toUri().toString());
    mediaPlayer = new MediaPlayer(media);
    mediaPlayer.play();
  }

  @FXML
  public void enableChat() {
    txtChat.setVisible(true);
    txtInput.setVisible(true);
    btnSend.setVisible(true);
  }

  @FXML
  public void disableChat() {
    txtChat.setVisible(false);
    txtInput.setVisible(false);
    btnSend.setVisible(false);
  }

  public void appendChatMessage(ChatMessage msg) {
    Platform.runLater(() -> txtChat.appendText(msg.getContent() + "\n"));
  }
}

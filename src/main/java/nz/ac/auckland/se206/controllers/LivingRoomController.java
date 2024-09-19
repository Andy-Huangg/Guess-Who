package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.nio.file.Paths;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.apiproxy.chat.openai.ChatMessage;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.ChatHandler;

public class LivingRoomController implements ChatSceneController {
  @FXML private Rectangle rectSaul;
  @FXML private TextArea txtChat;
  @FXML private TextField txtInput;
  @FXML private Button btnSend;

  private ChatHandler chatHandler;
  private boolean isSaulInteracted = false;
  private MediaPlayer mediaPlayer;

  public void initialize() {
    chatHandler = new ChatHandler("Saul");
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

    if (!isSaulInteracted) {
      isSaulInteracted = true;
      App.setSaulInteracted(isSaulInteracted);
    }
    chatHandler.sendMessage(message, this);
    txtChat.clear();
    txtInput.clear();
  }

  @FXML
  private void handleRectangleClick(MouseEvent event) throws IOException, InterruptedException {
    enableChat();
    playIntroAudio("Saul.mp3");
    txtChat.appendText("Hi Detective, Saul here. Strange to be involved in something like this");
    chatHandler.setCharacter("saul");
  }

  public void playIntroAudio(String audioFileName) {
    String audioFilePath = "src/main/resources/sounds/" + audioFileName;
    Media media = new Media(Paths.get(audioFilePath).toUri().toString());
    mediaPlayer = new MediaPlayer(media);
    mediaPlayer.play();
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

  @Override
  public void appendChatMessage(ChatMessage msg) {
    Platform.runLater(() -> txtChat.appendText(msg.getContent() + "\n"));
  }
}

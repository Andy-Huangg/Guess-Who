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
  public static boolean readyToSendMessage = true;
  @FXML protected TextArea txtChat;
  @FXML protected TextField txtInput;
  @FXML protected Button btnSend;

  private volatile boolean stopChecking = false;
  protected ChatHandler chatHandler;
  protected boolean isInteracted = false;
  protected MediaPlayer mediaPlayer;

  /**
   * Initializes the chat scene controller
   *
   * @param characterName the name of the character
   */
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

  /** Enables the send button */
  public void enableSend() {
    btnSend.setDisable(false);
  }

  /** Disables the send button */
  public void disableSend() {
    readyToSendMessage = false;
    btnSend.setDisable(true);
  }

  /** Checks if the message is ready to send */
  public void checkForReadyToSend() {
    if (readyToSendMessage) {
      enableSend();
      stopChecking = true;
    }
  }

  /**
   * Sends a message to the chat
   *
   * @param event send message button pressed
   * @throws ApiProxyException
   */
  @FXML
  public void onSendMessage(ActionEvent event) throws ApiProxyException {
    String message = txtInput.getText().trim();
    if (readyToSendMessage == false) {
      return;
    }
    if (TextAnimator.getIsRunning()) {
      return;
    }

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
    TextAnimator.setRunningCount(0);
    stopChecking = false;
    TextAnimator text = new TextAnimator(message, txtChat);
    text.startAnimation();
    disableSend();
    checkForReadyToSend();

    Thread checkerThread =
        new Thread(
            () -> {
              while (stopChecking == false) {
                checkForReadyToSend();
                try {
                  Thread.sleep(100);
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
              }
            });
    checkerThread.setDaemon(true); // Ensure the thread is terminated when the application exits
    checkerThread.start();
  }

  /**
   * Sets the interacted flag
   *
   * @param interacted the interacted flag
   */
  protected abstract void setInteractedFlag(boolean interacted);

  public void playIntroAudio(String audioFileName) {
    String audioFilePath = "src/main/resources/sounds/" + audioFileName;
    Media media = new Media(Paths.get(audioFilePath).toUri().toString());
    mediaPlayer = new MediaPlayer(media);
    mediaPlayer.play();
  }

  /**
   * Appends a chat message to the chat
   *
   * @param msg the chat message
   */
  public void appendChatMessage(ChatMessage msg) {
    Platform.runLater(
        () -> {
          TextAnimator text = new TextAnimator(msg.getContent(), txtChat);
          txtChat.appendText("\n");
          text.startAnimation();
        });
  }
}

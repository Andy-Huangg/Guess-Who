package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.nio.file.Paths;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.ChatHandler;

public class EndSceneController {

  @FXML private Label labelResult; // Label for the end message
  @FXML private Label labelReason; // Label for the feedback
  @FXML private Button btnnPlayAgain; // Button to restart the game
  @FXML private Button btnQuit; // Button to exit the game

  private ChatHandler chatHandler = new ChatHandler("owner");
  private MediaPlayer mediaPlayer;

  /**
   * Initializes the end scene view. It will display the end message and the score.
   *
   * @throws ApiProxyException
   * @throws InterruptedException
   */
  @FXML
  public void initialize() throws ApiProxyException, InterruptedException {
    // Check game state and set according text
    if (App.isTimeUp()) {
      playEndAudio("timesup.mp3");
      labelResult.setText("Time's Up! You Lost!");
      labelReason.setText("");
    } else if (!App.isWinner()) {
      playEndAudio("guesswrong.mp3");
      labelResult.setText("You Guessed Wrong! You Lost!");
      labelReason.setText("");
    } else {
      // Get the reason of the user and handle it accordingly
      Thread loadingPrompt =
          new Thread(
              () -> {
                try {
                  chatHandler.setCharacter("owner");
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
                Platform.runLater(
                    () -> {
                      try {
                        getReason(App.getGuessReason());
                      } catch (ApiProxyException e) {
                        e.printStackTrace();
                      }
                    });
              });
      loadingPrompt.start();
      playEndAudio("guessright.mp3");
      labelResult.setText("You Guessed Right!");
    }
  }

  /**
   * Get the reason of the user's guess
   *
   * @param reason the reason of the user's guess
   * @throws ApiProxyException
   */
  private void getReason(String reason) throws ApiProxyException {
    chatHandler.sendReason(reason, this);
  }

  /**
   * Set the reason of the user's guess
   *
   * @param reason the reason of the user's guess
   */
  public void setReason(String reason) {
    Platform.runLater(() -> labelReason.setText(reason));
  }

  /**
   * Play the end audio
   *
   * @param audioFileName the name of the audio file
   */
  public void playEndAudio(String audioFileName) {
    String audioFilePath = "src/main/resources/sounds/" + audioFileName;
    Media media = new Media(Paths.get(audioFilePath).toUri().toString());
    mediaPlayer = new MediaPlayer(media);
    mediaPlayer.play();
  }

  /**
   * Restart the game
   *
   * @param event the action event triggered by clicking the play again button
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onRestart(ActionEvent event) throws IOException {
    App.restartGame(labelResult); // Restart the game
  }

  /** Exit the game */
  @FXML
  private void onQuit() {
    Platform.exit(); // This closes the application
  }
}

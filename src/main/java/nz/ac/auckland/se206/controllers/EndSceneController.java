package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.ChatHandler;

public class EndSceneController {

  @FXML private Label labelResult; // Label for the end message
  @FXML private Label labelReason; // Label for the feedback
  @FXML private Button btnnPlayAgain; // Button to restart the game
  @FXML private Button btnQuit; // Button to exit the game

  private ChatHandler chatHandler = new ChatHandler("owner");

  /**
   * Initializes the end scene view. It will display the end message and the score.
   *
   * @throws ApiProxyException
   * @throws InterruptedException
   */
  @FXML
  public void initialize() throws ApiProxyException, InterruptedException {
    if (!App.isWinner()) {
      labelResult.setText("You Guessed Wrong! You Lost!");
      labelReason.setText("");
    } else {
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
      labelResult.setText("You Guessed Right!");
    }
  }

  private void getReason(String reason) throws ApiProxyException {
    chatHandler.sendReason(reason, this);
  }

  public void setReason(String reason) {
    Platform.runLater(() -> labelReason.setText(reason));
  }

  @FXML
  private void onRestart(ActionEvent event) throws IOException {
    App.restartGame(labelResult); // Restart the game
  }

  @FXML
  private void onQuit() {
    Platform.exit(); // This closes the application
  }
}

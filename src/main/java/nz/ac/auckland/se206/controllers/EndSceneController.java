package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.App;

public class EndSceneController {

  @FXML private Label labelResult; // Label for the end message
  @FXML private Label labelReason; // Label for the score
  @FXML private Button btnnPlayAgain; // Button to restart the game
  @FXML private Button btnQuit; // Button to exit the game

  private static int score = 0; // Score of the player

  /** Initializes the end scene view. It will display the end message and the score. */
  @FXML
  public void initialize() {
    if (App.isWinner()) {
      labelResult.setText("Correct! You Won!");
    } else {
      labelResult.setText("Wrong! You Lost!");
    }
    labelReason.setText("You got this right because blah blah blah");
  }

  @FXML
  private void onRestart(ActionEvent event) throws IOException {
    App.restartGame(); // Restart the game
  }

  @FXML
  private void onQuit() {
    Platform.exit(); // This closes the application
  }
}

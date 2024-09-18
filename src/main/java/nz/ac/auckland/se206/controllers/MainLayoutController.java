package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.GameStateContext;
import nz.ac.auckland.se206.speech.TextToSpeech;

/**
 * Controller class for the room view. Handles user interactions within the room where the user can
 * chat with customers and guess their profession.
 */
public class MainLayoutController {

  @FXML private Label timerLabel; // Label for the countdown timer
  @FXML private AnchorPane centrePane; // Pane for loading different rooms
  @FXML private static AnchorPane navBar; // Pane for the navigation bar
  @FXML private ImageView gardenImage, livingroomImage, studyImage, musicroomImage;
  private int timeRemaining = 300; // 5 minutes = 300 seconds
  private Timeline countdown;

  private static int clueCount = 0;

  private static boolean isFirstTimeInit = true;
  private static GameStateContext context = new GameStateContext();

  /**
   * Initializes the room view. If it's the first time initialization, it will provide instructions
   * via text-to-speech.
   */
  @FXML
  public void initialize() {
    if (isFirstTimeInit) {
      TextToSpeech.speak("Chat with the three customers, and guess who is the");
      isFirstTimeInit = false;
    }
    startTimer();
    loadStudy();
  }

  @FXML
  private void loadStudy() {
    AnchorPane crimeScene = loadFXML("crimescene");
    clearImageOpacity();
    studyImage.setOpacity(0.7);
    centrePane.getChildren().setAll(crimeScene);
  }

  @FXML
  public void loadGarden() {
    Pane garden = loadFXML("garden");
    clearImageOpacity();
    gardenImage.setOpacity(0.7);
    centrePane.getChildren().setAll(garden);
  }

  @FXML
  public void loadLivingRoom() {
    Pane livingroom = loadFXML("livingroom");
    clearImageOpacity();
    livingroomImage.setOpacity(0.7);
    centrePane.getChildren().setAll(livingroom);
  }

  @FXML
  public void loadMusicRoom() {
    Pane musicroom = loadFXML("musicroom");
    clearImageOpacity();
    musicroomImage.setOpacity(0.7);
    centrePane.getChildren().setAll(musicroom);
  }

  private void clearImageOpacity() {
    studyImage.setOpacity(1);
    gardenImage.setOpacity(1);
    livingroomImage.setOpacity(1);
    musicroomImage.setOpacity(1);
  }

  @FXML
  public static void enoughClues() {
    if (clueCount == 1 && navBar != null) {
      navBar.setVisible(true);
    }
  }

  private AnchorPane loadFXML(String fxmlFile) {
    try {
      return FXMLLoader.load(getClass().getResource("/fxml/" + fxmlFile + ".fxml"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new AnchorPane();
  }

  public static void incrementClueCount() {
    clueCount++;
    enoughClues();
  }

  /**
   * Handles mouse clicks on rectangles representing people in the room.
   *
   * @param event the mouse event triggered by clicking a rectangle
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void handleRectangleClick(MouseEvent event) throws IOException {
    Rectangle clickedRectangle = (Rectangle) event.getSource();
    context.handleRectangleClick(event, clickedRectangle.getId());
  }

  /**
   * Handles the guess button click event.
   *
   * @param event the action event triggered by clicking the guess button
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void handleGuessClick(ActionEvent event) throws IOException {
    if (clueCount < 3) {
      // TODO notify user must interact with all clue before continue;
      return;
    }
    // TODO allow player to guess
  }

  // Method to start the countdown timer
  private void startTimer() {
    countdown =
        new Timeline(
            new KeyFrame(
                Duration.seconds(1),
                event -> {
                  timeRemaining--;
                  int minutes = timeRemaining / 60;
                  int seconds = timeRemaining % 60;
                  timerLabel.setText(String.format("%02d:%02d", minutes, seconds));

                  if (timeRemaining <= 0) {
                    endInteractionPhase(); // End interactions when the timer reaches zero
                  }
                }));
    countdown.setCycleCount(Timeline.INDEFINITE);
    countdown.play();
  }

  private void endInteractionPhase() {
    timerLabel.setText("Time's up! Make your guess.");
  }
}

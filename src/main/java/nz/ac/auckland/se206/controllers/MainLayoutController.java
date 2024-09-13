package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.GameStateContext;
import nz.ac.auckland.se206.speech.TextToSpeech;

/**
 * Controller class for the room view. Handles user interactions within the room where the user can
 * chat with customers and guess their profession.
 */
public class MainLayoutController {

  @FXML private Label timerLabel; // Label for the countdown timer
  @FXML private AnchorPane centrePane; // Pane for loading different rooms

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
    loadStudy();
  }

  @FXML
  private void loadStudy() {
    AnchorPane crimeScene = loadFXML("crimescene");
    centrePane.getChildren().setAll(crimeScene);
  }

  @FXML
  public void loadGarden() {
    Pane garden = loadFXML("garden");
    centrePane.getChildren().setAll(garden);
  }

  @FXML
  public void loadLivingRoom() {
    Pane livingroom = loadFXML("livingroom");
    centrePane.getChildren().setAll(livingroom);
  }

  @FXML
  public void loadMusicRoom() {
    Pane musicroom = loadFXML("musicroom");
    centrePane.getChildren().setAll(musicroom);
  }

  private AnchorPane loadFXML(String fxmlFile) {
    try {
      return FXMLLoader.load(getClass().getResource("/fxml/" + fxmlFile + ".fxml"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new AnchorPane();
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    System.out.println("Key " + event.getCode() + " pressed");
  }

  /**
   * Handles the key released event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyReleased(KeyEvent event) {
    System.out.println("Key " + event.getCode() + " released");
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
    context.handleGuessClick();
  }
}

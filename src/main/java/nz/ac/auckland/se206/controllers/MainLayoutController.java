package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.nio.file.Paths;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import nz.ac.auckland.se206.App;

/**
 * Controller class for the room view. Handles user interactions within the room where the user can
 * chat with customers and guess their profession.
 */
public class MainLayoutController {

  private static boolean isFirstTimeInit = true;
  @FXML private static AnchorPane navBar; // Pane for the navigation bar

  @FXML private Label timerLabel; // Label for the countdown timer
  @FXML private AnchorPane centrePane; // Pane for loading different rooms
  @FXML private ImageView gardenImage;
  @FXML private ImageView livingroomImage;
  @FXML private ImageView studyImage;
  @FXML private ImageView musicroomImage;
  private int timeRemaining = 300; // 5 minutes = 300 seconds
  private boolean stopTimer = false;
  private MediaPlayer mediaPlayer;

  /**
   * Initializes the room view. If it's the first time initialization, it will provide instructions
   * via text-to-speech.
   */
  @FXML
  public void initialize() {
    if (isFirstTimeInit) {
      isFirstTimeInit = false;
    }
    startTimer();
    loadStudy();
  }

  @FXML
  private void loadStudy() {
    loadFXML("crimescene");
    clearImageOpacity();
    studyImage.setOpacity(0.7);
  }

  @FXML
  public void loadGarden() {
    loadFXML("garden");
    clearImageOpacity();
    gardenImage.setOpacity(0.7);
  }

  @FXML
  public void loadLivingRoom() {
    loadFXML("livingroom");
    clearImageOpacity();
    livingroomImage.setOpacity(0.7);
  }

  @FXML
  public void loadMusicRoom() {
    loadFXML("musicroom");
    clearImageOpacity();
    musicroomImage.setOpacity(0.7);
  }

  private void clearImageOpacity() {
    studyImage.setOpacity(1);
    gardenImage.setOpacity(1);
    livingroomImage.setOpacity(1);
    musicroomImage.setOpacity(1);
  }

  private void loadFXML(String fxmlFile) {
    // Create a new background thread to load the FXML file
    Thread fxmlLoaderThread =
        new Thread(
            () -> {
              try {
                AnchorPane loadedPane =
                    FXMLLoader.load(getClass().getResource("/fxml/" + fxmlFile + ".fxml"));
                Platform.runLater(
                    () -> {
                      centrePane.getChildren().setAll(loadedPane);
                    });
              } catch (IOException e) {
                e.printStackTrace();
              }
            });

    fxmlLoaderThread.setDaemon(true); // Ensure the thread is terminated when the application exits
    fxmlLoaderThread.start();
  }

  public void playAudio(String audioFileName) {
    String audioFilePath = "src/main/resources/sounds/" + audioFileName;
    Media media = new Media(Paths.get(audioFilePath).toUri().toString());
    mediaPlayer = new MediaPlayer(media);
    mediaPlayer.play();
  }

  /**
   * Handles the guess button click event.
   *
   * @param event the action event triggered by clicking the guess button
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void handleGuessClick() throws IOException {
    if (App.isEnoughInteraction() && App.isClueInteracted()) {
      stopTimer();
      App.openGuessWindow(timerLabel);
      return;
    }
    playAudio("investigatemore.mp3");
  }

  // Method to start the countdown timer
  private void startTimer() {
    // Create a new background thread for the timer
    Thread timerThread =
        new Thread(
            () -> {
              while (timeRemaining >= 0 && !stopTimer) {
                // Update the timerLabel on the JavaFX Application Thread
                Platform.runLater(
                    () -> {
                      int minutes = timeRemaining / 60;
                      int seconds = timeRemaining % 60;
                      timerLabel.setText(String.format("%02d:%02d", minutes, seconds));

                      // End interactions if time runs out
                      if (timeRemaining <= 0) {
                        try {
                          stopTimer();
                          if (App.isEnoughInteraction() && App.isClueInteracted()) {
                            App.openGuessWindow(timerLabel);
                          } else {
                            App.setTimeUp(true);
                            App.openEndGameWindow(timerLabel);
                          }
                        } catch (IOException e) {
                          e.printStackTrace();
                        }
                      }
                    });
                try {
                  // Sleep for 1 second
                  Thread.sleep(1000);
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }

                // Decrease the remaining time
                timeRemaining--;
              }
            });
    timerThread.setDaemon(true); // Ensures thread is closed when the application exits
    timerThread.start(); // Start the background thread
  }

  // Call this method to stop the timer if needed
  private void stopTimer() {
    stopTimer = true;
  }
}

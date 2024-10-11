package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.nio.file.Paths;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;

/**
 * Controller class for the room view. Handles user interactions within the room where the user can
 * chat with customers and guess their profession.
 */
public class MainLayoutController {

  @FXML private Label timerLabel; // Label for the countdown timer
  @FXML private AnchorPane centrePane; // Pane for loading different rooms
  @FXML private AnchorPane studyPane;
  @FXML private AnchorPane navBar;
  @FXML private ImageView gardenImage;
  @FXML private ImageView livingroomImage;
  @FXML private ImageView studyImage;
  @FXML private ImageView musicroomImage;
  @FXML private Button btnGuess;
  @FXML private Pane taskPane;
  @FXML private Text suspectCounter;
  @FXML private Text clueCounter;
  @FXML private Rectangle coverRect;
  @FXML private Text studyText;
  @FXML private Text gardenText;
  @FXML private Text livingRoomText;
  @FXML private Text musicRoomText;

  private int timeRemaining = 284; // 5 minutes = 300 seconds
  private boolean stopTimer = false;
  private MediaPlayer mediaPlayer;

  /**
   * Initializes the room view. If it's the first time initialization, it will provide instructions
   * via text-to-speech.
   */
  @FXML
  public void initialize() {
    startTimer();
    try {
      // loading up the crimescene fxml file
      studyPane = FXMLLoader.load(getClass().getResource("/fxml/" + "crimescene" + ".fxml"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    loadStudy();
  }

  /** Handles the study room button click event. */
  @FXML
  private void loadStudy() {
    if (ChatSceneController.readyToSendMessage == false) {
      return;
    }
    // separate thread to load the study room so the main thread doesn't freeze
    Thread thread =
        new Thread(
            () -> {
              Platform.runLater(
                  () -> {
                    centrePane.getChildren().setAll(studyPane);
                    clearImageOpacity();
                    studyImage.setOpacity(0.7);
                  });
            });
    // closed thread when the application exits
    thread.setDaemon(true);
    thread.start();
  }

  /** Handles the garden room button click event. */
  @FXML
  public void loadGarden() {
    if (ChatSceneController.readyToSendMessage == false) {
      return;
    }
    loadScene("garden");
    clearImageOpacity();
    gardenImage.setOpacity(0.7);
  }

  /** Handles the living room button click event. */
  @FXML
  public void loadLivingRoom() {
    if (ChatSceneController.readyToSendMessage == false) {
      return;
    }
    loadScene("livingroom");
    clearImageOpacity();
    livingroomImage.setOpacity(0.7);
  }

  /** Handles the music room button click event. */
  @FXML
  public void loadMusicRoom() {
    if (ChatSceneController.readyToSendMessage == false) {
      return;
    }
    loadScene("musicroom");
    clearImageOpacity();
    musicroomImage.setOpacity(0.7);
  }

  /**
   * Handles the mouse entered event on the images. Enlarges the text when the mouse hovers over the
   * image.
   *
   * @param event the mouse event
   */
  @FXML
  private void handleImageEntered(MouseEvent event) {
    // Get the image that the mouse is hovering over
    ImageView currentImage = (ImageView) event.getTarget();
    String imageIdentification = currentImage.getId();

    switch (imageIdentification) {
      // Enlarge the text when the mouse hovers over the image
      case "studyImage":
        studyText.getStyleClass().add("enlarge");
        break;
      case "gardenImage":
        gardenText.getStyleClass().add("enlarge");
        break;
      case "livingroomImage":
        livingRoomText.getStyleClass().add("enlarge");
        break;
      case "musicroomImage":
        musicRoomText.getStyleClass().add("enlarge");
        break;
      default:
        break;
    }
  }

  /**
   * Handles the mouse exited event on the images. Removes the enlarged text when the mouse leaves
   * the image.
   *
   * @param event the mouse event
   */
  @FXML
  private void handleImageExited(MouseEvent event) {
    ImageView currentImage = (ImageView) event.getTarget();
    String imageIdentification = currentImage.getId();

    // Remove the enlarge effect when the mouse exits the image
    switch (imageIdentification) {
      // remove the enlarged text when the mouse leaves the image
      case "studyImage":
        studyText.getStyleClass().remove("enlarge");
        break;
      case "gardenImage":
        gardenText.getStyleClass().remove("enlarge");
        break;
      case "livingroomImage":
        livingRoomText.getStyleClass().remove("enlarge");
        break;
      case "musicroomImage":
        musicRoomText.getStyleClass().remove("enlarge");
        break;
      default:
        break;
    }
  }

  private void clearImageOpacity() {
    studyImage.setOpacity(1);
    gardenImage.setOpacity(1);
    livingroomImage.setOpacity(1);
    musicroomImage.setOpacity(1);
  }

  /**
   * Displays the number of tasks completed so far. If all tasks are completed, the guess button
   * will be displayed.
   */
  public void displayTasks() {
    // Display the number of clues and suspects interacted with so far
    boolean clueInteractedWith = App.isClueInteracted();
    if (clueInteractedWith) {
      clueCounter.setText("1/1"); // Only one clue interaction needed
    }
    int suspectsInteractedWith = App.getSuspectsInteracted();
    suspectCounter.setText(suspectsInteractedWith + "/3"); // Three suspects to interact with

    // Display the guess button if all conditions are met
    if (clueInteractedWith && suspectsInteractedWith >= 3) {
      taskPane.setVisible(false);
      btnGuess.setVisible(true);
    }
  }

  /**
   * Loads the FXML file specified by the parameter.
   *
   * @param fxmlFile the FXML file to load
   */
  private void loadScene(String fxmlFile) {
    // Create a new background thread to load the FXML file
    Thread fxmlLoaderThread =
        new Thread(
            () -> {
              try {
                AnchorPane loadedPane =
                    FXMLLoader.load(getClass().getResource("/fxml/" + fxmlFile + ".fxml"));
                if (studyPane == null) {
                  studyPane = loadedPane;
                }
                Platform.runLater(
                    () -> {
                      if (studyPane != null && fxmlFile.equals("crimescene")) {
                        centrePane.getChildren().setAll(studyPane);
                      } else {
                        centrePane.getChildren().setAll(loadedPane);
                        navBar.setDisable(true); // Disable the navBar

                        // Create a PauseTransition to re-enable the navBar after 4 seconds
                        PauseTransition pause = new PauseTransition(Duration.seconds(3));
                        pause.setOnFinished(
                            event -> navBar.setDisable(false)); // Re-enable navBar after 4 seconds
                        pause.play(); // Start the timer
                      }
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
    btnGuess.setDisable(true);

    // Create a new thread to handle the delay
    new Thread(
            () -> {
              try {
                // Sleep for 4 seconds
                Thread.sleep(4000);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              // Re-enable the button on the JavaFX Application Thread
              Platform.runLater(() -> btnGuess.setDisable(false));
            })
        .start();
  }

  // Method to start the countdown timer
  private void startTimer() {
    // Create a new background thread for the timer
    Thread timerThread =
        new Thread(
            () -> {
              try {
                transitionToMain();
                Thread.sleep(4500);
              } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }
              coverRect.setVisible(false);
              while (timeRemaining >= 0 && !stopTimer) {
                // Update the timerLabel on the JavaFX Application Thread
                Platform.runLater(
                    () -> {
                      int minutes = timeRemaining / 60;
                      int seconds = timeRemaining % 60;
                      timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
                      displayTasks();

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

  /* Method to transition to the main screen */
  public void transitionToMain() {
    coverRect.setVisible(true);
    FadeTransition temp = new FadeTransition();
    temp.setDuration(Duration.millis(4500));
    temp.setToValue(0);
    temp.setNode(coverRect);
    temp.play();
  }
}

package nz.ac.auckland.se206;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import nz.ac.auckland.se206.speech.FreeTextToSpeech;

/**
 * This is the entry point of the JavaFX application. This class initializes and runs the JavaFX
 * application.
 */
public class App extends Application {

  private static Scene scene;
  private static Parent mainLayout;
  private static String guessReason;
  private static boolean isWinner;
  private static boolean isTimeUp = false;
  private static boolean isBruceInteracted = false;
  private static boolean isSaulInteracted = false;
  private static boolean isAlfredInteracted = false;
  private static boolean isClueInteracted = false;

  /**
   * The main method that launches the JavaFX application.
   *
   * @param args the command line arguments
   */
  public static void main(final String[] args) {
    launch();
  }

  /**
   * Sets the root of the scene to the specified FXML file.
   *
   * @param fxml the name of the FXML file (without extension)
   * @throws IOException if the FXML file is not found
   */
  public static void setRoot(String fxml) throws IOException {
    scene.setRoot(loadFxml(fxml));
  }

  /**
   * Loads the FXML file and returns the associated node. The method expects that the file is
   * located in "src/main/resources/fxml".
   *
   * @param fxml the name of the FXML file (without extension)
   * @return the root node of the FXML file
   * @throws IOException if the FXML file is not found
   */
  public static Parent loadFxml(final String fxml) throws IOException {
    return new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml")).load();
  }

  /**
   * Opens the guessing stage window.
   *
   * @param event a label in the current scene
   * @throws IOException if the FXML file is not found
   */
  public static void openGuessWindow(Label event) throws IOException {
    FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/guess.fxml"));
    Parent root = loader.load();
    Stage stage = (Stage) event.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Opens the end game window.
   *
   * @param event a label in the current scene
   * @throws IOException if the FXML file is not found
   */
  public static void openEndGameWindow(Label event) throws IOException {
    FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/endscene.fxml"));
    Parent root = loader.load();
    Stage stage = (Stage) event.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Restarts the game by setting the scene back to the initial layout.
   *
   * @param event a label in the current scene
   * @throws IOException if the FXML file is not found
   */
  public static void restartGame(Label event) throws IOException {
    setAlfredInteracted(false);
    setBruceInteracted(false);
    setSaulInteracted(false);
    setWinner(false);
    setClueInteracted(false);
    FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/opening.fxml"));
    Parent root = loader.load();
    Stage stage = (Stage) event.getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show(); // Reset the scene to the initial one
  }

  /**
   * Preloads the main layout.
   *
   * @param event a rectangle in the current scene
   */
  public static void preloadMainLayout(Rectangle event) {
    // Load the main layout in a separate thread to prevent the application from freezing
    new Thread(
            () -> {
              FXMLLoader temp = new FXMLLoader(App.class.getResource("/fxml/mainlayout.fxml"));
              try {
                mainLayout = temp.load();
              } catch (IOException e) {
                e.printStackTrace();
              }
            })
        .start(); // Start the thread
  }

  /**
   * Switches the scene to the main game layout.
   *
   * @param event a rectangle in the current scene
   * @throws IOException if the FXML file is not found
   */
  public static void switchMainGame(Rectangle event) throws IOException {
    Stage stage = (Stage) event.getScene().getWindow();
    scene = new Scene(mainLayout);
    stage.setScene(scene);
    stage.show(); // Reset the scene to the initial one
  }

  public static void setBruceInteracted(boolean isInteracted) {
    isBruceInteracted = isInteracted;
  }

  public static void setSaulInteracted(boolean isInteracted) {
    isSaulInteracted = isInteracted;
  }

  public static void setAlfredInteracted(boolean isInteracted) {
    isAlfredInteracted = isInteracted;
  }

  public static boolean isBruceInteracted() {
    return isBruceInteracted;
  }

  public static boolean isSaulInteracted() {
    return isSaulInteracted;
  }

  public static boolean isAlfredInteracted() {
    return isAlfredInteracted;
  }

  /**
   * Get the number of suspects interacted.
   *
   * @return the number of suspects interacted
   */
  public static int getSuspectsInteracted() {
    // increment count if the suspect is interacted for first time
    int count = 0;
    if (isBruceInteracted()) {
      count++;
    }
    if (isSaulInteracted()) {
      count++;
    }
    if (isAlfredInteracted()) {
      count++;
    }
    return count; // return the number of suspects interacted
  }

  /**
   * Check if the user has interacted with all suspects.
   *
   * @return true if the user has interacted with all suspects, false otherwise
   */
  public static boolean isEnoughInteraction() {
    if (isBruceInteracted && isSaulInteracted && isAlfredInteracted) {
      return true;
    }
    return false;
  }

  public static void setClueInteracted(boolean isInteracted) {
    isClueInteracted = isInteracted;
  }

  public static boolean isClueInteracted() {
    return isClueInteracted;
  }

  public static void setWinner(boolean isWinner) {
    App.isWinner = isWinner;
  }

  public static boolean isWinner() {
    return isWinner;
  }

  public static void setGuessReason(String reason) {
    guessReason = reason;
  }

  public static String getGuessReason() {
    return guessReason;
  }

  public static void setTimeUp(boolean isTimeUp) {
    App.isTimeUp = isTimeUp;
  }

  public static boolean isTimeUp() {
    return isTimeUp;
  }

  /**
   * This method is invoked when the application starts. It loads and shows the "room" scene.
   *
   * @param stage the primary stage of the application
   * @throws IOException if the "src/main/resources/fxml/room.fxml" file is not found
   */
  @Override
  public void start(final Stage stage) throws IOException {
    Parent root = loadFxml("opening");
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    stage.setOnCloseRequest(event -> handleWindowClose(event));
    root.requestFocus();
  }

  /**
   * This method is invoked when the application is closed. It deallocates the synthesizer.
   *
   * @param event the window event
   */
  private void handleWindowClose(WindowEvent event) {
    FreeTextToSpeech.deallocateSynthesizer();
  }
}

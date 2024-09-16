package nz.ac.auckland.se206;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.input.MouseEvent;
import nz.ac.auckland.se206.states.GameOver;
import nz.ac.auckland.se206.states.GameStarted;
import nz.ac.auckland.se206.states.GameState;
import nz.ac.auckland.se206.states.Guessing;

/**
 * Context class for managing the state of the game. Handles transitions between different game
 * states and maintains game data such as the professions and rectangle IDs.
 */
public class GameStateContext {

  private final String rectIdToGuess;
  private final Map<String, String> characterStories;
  private final GameStarted gameStartedState;
  private final Guessing guessingState;
  private final GameOver gameOverState;
  private GameState gameState;

  /** Constructs a new GameStateContext and initializes the game states and professions. */
  public GameStateContext() {
    gameStartedState = new GameStarted(this);
    guessingState = new Guessing(this);
    gameOverState = new GameOver(this);

    gameState = gameStartedState; // Initial state
    rectIdToGuess = "rectBruce";

    String storyAlfred = loadFileAsString("prompts/alfred.txt");
    String storyBruce = loadFileAsString("prompts/bruce.txt");
    String storySaul = loadFileAsString("prompts/saul.txt");

    // Store the stories in a map for each character
    characterStories = new HashMap<>();
    characterStories.put("rectAlfred", storyAlfred);
    characterStories.put("rectBruce", storyBruce);
    characterStories.put("rectSaul", storySaul);
  }

  // Utility method to load the entire contents of a file as a String
  private String loadFileAsString(String filePath) {
    StringBuilder fileContent = new StringBuilder();
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

      if (inputStream == null) {
        throw new IllegalStateException("File not found: " + filePath);
      }

      String line;
      while ((line = reader.readLine()) != null) {
        fileContent.append(line).append("\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return fileContent.toString();
  }

  public String getStoryForCharacter(String characterKey) {
    return characterStories.get(characterKey);
  }

  //   Map<String, Object> obj = null;
  //   Yaml yaml = new Yaml();
  //   try (InputStream inputStream =
  //       GameStateContext.class.getClassLoader().getResourceAsStream("data/professions.yaml")) {
  //     if (inputStream == null) {
  //       throw new IllegalStateException("File not found!");
  //     }
  //     obj = yaml.load(inputStream);
  //   } catch (IOException e) {
  //     e.printStackTrace();
  //   }

  //   @SuppressWarnings("unchecked")
  //   List<String> professions = (List<String>) obj.get("professions");

  //   Random random = new Random();
  //   Set<String> randomProfessions = new HashSet<>();
  //   while (randomProfessions.size() < 3) {
  //     String profession = professions.get(random.nextInt(professions.size()));
  //     randomProfessions.add(profession);
  //   }

  //   String[] randomProfessionsArray = randomProfessions.toArray(new String[3]);
  //   rectanglesToProfession = new HashMap<>();
  //   rectanglesToProfession.put("rectPerson1", randomProfessionsArray[0]);
  //   rectanglesToProfession.put("rectPerson2", randomProfessionsArray[1]);
  //   rectanglesToProfession.put("rectPerson3", randomProfessionsArray[2]);

  //   int randomNumber = random.nextInt(3);
  //   rectIdToGuess =
  //       randomNumber == 0 ? "rectPerson1" : ((randomNumber == 1) ? "rectPerson2" :
  // "rectPerson3");
  //   professionToGuess = rectanglesToProfession.get(rectIdToGuess);
  // }

  /**
   * Sets the current state of the game.
   *
   * @param state the new state to set
   */
  public void setState(GameState state) {
    this.gameState = state;
  }

  /**
   * Gets the initial game started state.
   *
   * @return the game started state
   */
  public GameState getGameStartedState() {
    return gameStartedState;
  }

  /**
   * Gets the guessing state.
   *
   * @return the guessing state
   */
  public GameState getGuessingState() {
    return guessingState;
  }

  /**
   * Gets the game over state.
   *
   * @return the game over state
   */
  public GameState getGameOverState() {
    return gameOverState;
  }

  /**
   * Gets the profession to be guessed.
   *
   * @return the profession to guess
   */
  // public String getProfessionToGuess() {
  //   return professionToGuess;
  // }

  /**
   * Gets the ID of the rectangle to be guessed.
   *
   * @return the rectangle ID to guess
   */
  public String getRectIdToGuess() {
    return rectIdToGuess;
  }

  /**
   * Gets the profession associated with a specific rectangle ID.
   *
   * @param rectangleId the rectangle ID
   * @return the profession associated with the rectangle ID
   */
  public String getPerson(String rectangleId) {
    return characterStories.get(rectangleId);
  }

  /**
   * Handles the event when a rectangle is clicked.
   *
   * @param event the mouse event triggered by clicking a rectangle
   * @param rectangleId the ID of the clicked rectangle
   * @throws IOException if there is an I/O error
   */
  public void handleRectangleClick(MouseEvent event, String rectangleId) throws IOException {
    gameState.handleRectangleClick(event, rectangleId);
  }

  /**
   * Handles the event when the guess button is clicked.
   *
   * @throws IOException if there is an I/O error
   */
  public void handleGuessClick() throws IOException {
    gameState.handleGuessClick();
  }
}

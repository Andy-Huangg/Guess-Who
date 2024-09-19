package nz.ac.auckland.se206;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Context class for managing the state of the game. Handles transitions between different game
 * states and maintains game data such as the professions and rectangle IDs.
 */
public class GameStateContext {

  private final Map<String, String> characterStories;

  /** Constructs a new GameStateContext and initializes the game states and professions. */
  public GameStateContext() {

    String storyAlfred = loadFileAsString("prompts/alfred.txt");
    String storyBruce = loadFileAsString("prompts/bruce.txt");
    String storySaul = loadFileAsString("prompts/saul.txt");

    // Store the stories in a map for each character
    characterStories = new HashMap<>();
    characterStories.put("Alfred", storyAlfred);
    characterStories.put("Bruce", storyBruce);
    characterStories.put("Saul", storySaul);
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
}

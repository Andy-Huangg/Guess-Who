package nz.ac.auckland.se206.prompts;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Utility class for prompt engineering. This class provides methods to load and fill prompt
 * templates with dynamic data.
 */
public class PromptEngineering {

  /**
   * Returns a prompt template filled with the provided data.
   *
   * @param promptId the ID of the prompt template to load
   * @return the prompt template filled with the provided data
   */
  public static String getPrompt(String promptId) {
    try {
      // Load the prompt template file from resources
      URL resourceUrl =
          PromptEngineering.class.getClassLoader().getResource("prompts/" + promptId + ".txt");
      // change the profession to the given one.
      String template = loadTemplate(resourceUrl.toURI());
      // Fill the template with the provided data
      return template;
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
      throw new IllegalArgumentException("Error loading or filling the prompt template.", e);
    }
  }

  /**
   * Loads the content of a template file from the specified file path.
   *
   * @param filePath the URI of the file to load
   * @return the content of the template file as a string
   * @throws IOException if there is an error reading the file
   */
  private static String loadTemplate(URI filePath) throws IOException {
    return new String(Files.readAllBytes(Paths.get(filePath)));
  }
}

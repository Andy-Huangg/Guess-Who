package nz.ac.auckland.se206;

import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionRequest;
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionResult;
import nz.ac.auckland.apiproxy.chat.openai.ChatMessage;
import nz.ac.auckland.apiproxy.chat.openai.Choice;
import nz.ac.auckland.apiproxy.config.ApiProxyConfig;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.controllers.ChatSceneController;
import nz.ac.auckland.se206.controllers.EndSceneController;
import nz.ac.auckland.se206.prompts.PromptEngineering;

/** Handles the chat logic between the user and chatGPT. */
public class ChatHandler {
  private ChatCompletionRequest chatCompletionRequest;
  private String character;

  /**
   * Constructor for ChatHandler.
   *
   * @param character the character for the chat
   */
  public ChatHandler(String character) {
    this.character = character;
  }

  /**
   * Set the character for the chat.
   *
   * @param character the character for the chat
   * @throws InterruptedException when the thread is interrupted
   */
  public void setCharacter(String character) throws InterruptedException {

    this.character = character;

    Thread backgroundThread = // use the background thread to run GPT so it don't lag the UI
        new Thread(
            () -> {
              try {
                ApiProxyConfig config = ApiProxyConfig.readConfig();
                chatCompletionRequest =
                    new ChatCompletionRequest(config)
                        .setN(1)
                        .setTemperature(0.2)
                        .setTopP(0.2)
                        .setMaxTokens(100);
                runGpt(
                    new ChatMessage(
                        "system", getSystemPrompt())); // load the system prompt into GPT
              } catch (ApiProxyException e) {
                e.printStackTrace();
              }
            });
    backgroundThread.start();
  }

  private String getSystemPrompt() {
    return PromptEngineering.getPrompt(character);
  }

  /**
   * Send the user's message to chatGPT.
   *
   * @param message the user's message
   * @param controller the controller to update the UI
   * @throws ApiProxyException when there is an error with the API proxy
   */
  public void sendMessage(String message, ChatSceneController controller) throws ApiProxyException {
    Thread backgroundThread =
        new Thread(
            () -> {
              try {
                ChatMessage msg = new ChatMessage("user", message);

                ChatMessage response = runGpt(msg);
                controller.appendChatMessage(response); // Update UI with response
              } catch (ApiProxyException e) {
                e.printStackTrace();
              }
            });
    backgroundThread.start();
  }

  /**
   * Send the user's reason for guessing the thief to chatGPT.
   *
   * @param message the user's reason for guessing the thief
   * @param controller the controller to update the UI
   * @throws ApiProxyException when there is an error with the API proxy
   */
  public void sendReason(String message, EndSceneController controller) throws ApiProxyException {
    // Creates a thread to Send the user's reason for guessing the thief to chatGPT
    Thread backgroundThread =
        new Thread(
            () -> {
              try {
                ChatMessage msg = new ChatMessage("user", message);
                ChatMessage response = runGpt(msg);
                controller.setReason(response.getContent());
              } catch (ApiProxyException e) {
                e.printStackTrace();
              }
            });
    backgroundThread.start();
  }

  private ChatMessage runGpt(ChatMessage msg) throws ApiProxyException {
    chatCompletionRequest.addMessage(msg);

    ChatCompletionResult result = chatCompletionRequest.execute();
    Choice response = result.getChoices().iterator().next();
    chatCompletionRequest.addMessage(response.getChatMessage());
    return response.getChatMessage();
  }
}

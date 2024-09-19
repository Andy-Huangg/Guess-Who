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

public class ChatHandler {
  private ChatCompletionRequest chatCompletionRequest;
  private String character;

  public ChatHandler(String character) {
    this.character = character;
  }

  // Set the profession and initialize ChatCompletionRequest
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
                        .setTopP(0.5)
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

  public void sendMessage(String message, ChatSceneController controller) throws ApiProxyException {
    Thread backgroundThread =
        new Thread(
            () -> {
              try {
                ChatMessage msg = new ChatMessage("user", message);
                controller.appendChatMessage(msg); // Call controller to update UI

                ChatMessage response = runGpt(msg);
                controller.appendChatMessage(response); // Update UI with response
              } catch (ApiProxyException e) {
                e.printStackTrace();
              }
            });
    backgroundThread.start();
  }

  public void sendReason(String message, EndSceneController controller) throws ApiProxyException {
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

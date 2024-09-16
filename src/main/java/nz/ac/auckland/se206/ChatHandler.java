package nz.ac.auckland.se206;

import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionRequest;
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionResult;
import nz.ac.auckland.apiproxy.chat.openai.ChatMessage;
import nz.ac.auckland.apiproxy.chat.openai.Choice;
import nz.ac.auckland.apiproxy.config.ApiProxyConfig;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.prompts.PromptEngineering;

public class ChatHandler {

  private static ChatCompletionRequest chatCompletionRequest;

  /**
   * Sets the character for the chat context and initializes the ChatCompletionRequest.
   *
   * @param characterID the charcter to set
   */
  public static String setCharacter(String characterID) {
    try {
      ApiProxyConfig config = ApiProxyConfig.readConfig();
      chatCompletionRequest =
          new ChatCompletionRequest(config)
              .setN(1)
              .setTemperature(0.5)
              .setTopP(0.5)
              .setMaxTokens(100);
      return runGpt(new ChatMessage("system", PromptEngineering.getPrompt(characterID)));
    } catch (ApiProxyException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static String runGpt(ChatMessage msg) throws ApiProxyException {
    chatCompletionRequest.addMessage(msg);
    try {
      ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();
      Choice result = chatCompletionResult.getChoices().iterator().next();
      chatCompletionRequest.addMessage(result.getChatMessage());
      return result.getChatMessage().getContent();
    } catch (ApiProxyException e) {
      e.printStackTrace();
      return null;
    }
  }
}

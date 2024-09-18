package nz.ac.auckland.se206;

import javafx.scene.control.TextArea;
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionRequest;
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionResult;
import nz.ac.auckland.apiproxy.chat.openai.ChatMessage;
import nz.ac.auckland.apiproxy.chat.openai.Choice;
import nz.ac.auckland.apiproxy.config.ApiProxyConfig;
import nz.ac.auckland.apiproxy.exceptions.ApiProxyException;
import nz.ac.auckland.se206.controllers.ChatSceneController;
import nz.ac.auckland.se206.prompts.PromptEngineering;

public class ChatHandler {
  private ChatCompletionRequest chatCompletionRequest;
  private String character;
  private TextArea chatTextArea;
  private String profession;

  public ChatHandler(String character) {
    this.character = character;
  }

  // Set the profession and initialize ChatCompletionRequest
  public void setCharacter(String character) throws InterruptedException {

    this.character = character;

    try {
      ApiProxyConfig config = ApiProxyConfig.readConfig();
      chatCompletionRequest =
          new ChatCompletionRequest(config)
              .setN(1)
              .setTemperature(0.2)
              .setTopP(0.5)
              .setMaxTokens(100);

      runGpt(new ChatMessage("system", getSystemPrompt()));
    } catch (ApiProxyException e) {
      e.printStackTrace();
    }
  }

  private String getSystemPrompt() {
    return PromptEngineering.getPrompt(character);
  }

  public void sendMessage(String message, ChatSceneController controller) {
    ChatMessage msg = new ChatMessage("user", message);
    controller.appendChatMessage(msg); // Call controller to update UI
    new Thread(
            () -> {
              try {
                ChatMessage response = runGpt(msg);
                controller.appendChatMessage(response); // Update UI with response
              } catch (ApiProxyException | InterruptedException e) {
                e.printStackTrace();
              }
            })
        .start();
  }

  private ChatMessage runGpt(ChatMessage msg) throws ApiProxyException, InterruptedException {
    chatCompletionRequest.addMessage(msg);

    ChatCompletionResult result = chatCompletionRequest.execute();
    Choice response = result.getChoices().iterator().next();
    chatCompletionRequest.addMessage(response.getChatMessage());
    return response.getChatMessage();
  }
}

// public class ChatHandler {

//   private static ChatCompletionRequest chatCompletionRequest;
//   private TextArea chatArea;

//   public ChatHandler(TextArea chatArea) {
//     this.chatArea = chatArea;
//   }

//   /**
//    * Sets the character for the chat context and initializes the ChatCompletionRequest.
//    *
//    * @param characterID the charcter to set
//    */
//   public static String setCharacter(String characterID) {
//     try {
//       ApiProxyConfig config = ApiProxyConfig.readConfig();
//       chatCompletionRequest =
//           new ChatCompletionRequest(config)
//               .setN(1)
//               .setTemperature(0.5)
//               .setTopP(0.5)
//               .setMaxTokens(100);
//       return runGpt(new ChatMessage("system", PromptEngineering.getPrompt(characterID)));
//     } catch (ApiProxyException e) {
//       e.printStackTrace();
//       return null;
//     }
//   }

//   public static String runGpt(ChatMessage msg) throws ApiProxyException {
//     chatCompletionRequest.addMessage(msg);
//     try {
//       ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();
//       Choice result = chatCompletionResult.getChoices().iterator().next();
//       chatCompletionRequest.addMessage(result.getChatMessage());
//       return result.getChatMessage().getContent();
//     } catch (ApiProxyException e) {
//       e.printStackTrace();
//       return null;
//     }
//   }

//   @FXML
//   public void onSendMessage(String userMessage) throws ApiProxyException, IOException {
//     if (userMessage.isEmpty()) {
//       return;
//     }

//     ChatMessage msg = new ChatMessage("user", userMessage);
//     appendChatMessage(msg);
//     runGpt(msg);
//   }

//   private void appendChatMessage(ChatMessage msg) {
//     chatArea.appendText(msg.getContent() + "\n");
//   }
// }

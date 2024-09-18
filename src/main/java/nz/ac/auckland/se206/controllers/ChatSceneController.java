package nz.ac.auckland.se206.controllers;

import nz.ac.auckland.apiproxy.chat.openai.ChatMessage;

public interface ChatSceneController {
  void appendChatMessage(ChatMessage msg); // Method to update the chat
}

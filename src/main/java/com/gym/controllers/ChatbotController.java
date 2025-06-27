package com.gym.controllers;

import com.gym.models.Chatbot;

public class ChatbotController {
    private Chatbot chatbot;

    public ChatbotController() {
        this.chatbot = new Chatbot();
    }

    public String sendMessage(String message) {
        return chatbot.getResponse(message);
    }
}

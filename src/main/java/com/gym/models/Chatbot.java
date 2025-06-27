package com.gym.models;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class Chatbot {
    private Map<String, String> faq;
    private List<String> chatHistory;

    public Chatbot() {
        this.faq = new HashMap<>();
        this.chatHistory = new ArrayList<>();
        
        faq.put("how do i register", "You can register by clicking the 'Sign Up' button on the main page and filling in your details.");
        faq.put("reset password", "Click the 'Forgot Password' link on the login page to reset your password.");
        faq.put("renew membership", "Go to the 'Membership' tab in your profile and click 'Renew'.");
    }

    public String getResponse(String userInput) {
        chatHistory.add("User: " + userInput);
        String response = "I'm not sure how to answer that. Can you try asking differently? You can also create a support ticket for help.";

        for (Map.Entry<String, String> entry : faq.entrySet()) {
            if (userInput.toLowerCase().contains(entry.getKey())) {
                response = entry.getValue();
                break;
            }
        }
        
        chatHistory.add("Bot: " + response);
        return response;
    }
    
    public List<String> getChatHistory() {
        return chatHistory;
    }
}

package com.gym.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SupportTicket {
    private String ticketId;
    private String memberId;
    private String title;
    private String description;
    private String status;
    private LocalDateTime creationDate;
    private List<String> responses;

    public SupportTicket(String memberId, String title, String description) {
        this.ticketId = UUID.randomUUID().toString();
        this.memberId = memberId;
        this.title = title;
        this.description = description;
        this.status = "Open";
        this.creationDate = LocalDateTime.now();
        this.responses = new ArrayList<>();
    }

    public String getTicketId() { return ticketId; }
    public String getMemberId() { return memberId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public List<String> getResponses() { return responses; }

    public void addResponse(String response, String responderId) {
        this.responses.add("From " + responderId + " at " + LocalDateTime.now() + ": " + response);
    }
}

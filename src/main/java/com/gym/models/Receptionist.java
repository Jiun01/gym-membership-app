package com.gym.models;

public class Receptionist extends Staff {

    public Receptionist(String userId, String password, String staffId, String name) {
        super(userId, password, staffId, name, "Receptionist");
    }

    public void respondToSupportTicket(SupportTicket ticket, String response) {
        ticket.addResponse(response, getUserId());
        ticket.setStatus("Answered");
        System.out.println("Responded to ticket: " + ticket.getTicketId());
    }

    public void handleContactRequest(Member member, String requestDetails) {
        System.out.println("Handling contact request from " + member.getName() + ": " + requestDetails);
        SupportTicket contactTicket = member.createSupportTicket("Contact Request", requestDetails);
        contactTicket.setStatus("In Progress");
    }
}

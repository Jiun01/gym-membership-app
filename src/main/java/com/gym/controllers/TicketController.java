package com.gym.controllers;

import com.gym.models.Member;
import com.gym.models.SupportTicket;
import java.util.List;
import java.util.ArrayList;

public class TicketController {
    private final List<SupportTicket> allTickets = new ArrayList<>();

    public SupportTicket createTicket(Member member, String title, String description) {
        SupportTicket ticket = member.createSupportTicket(title, description);
        allTickets.add(ticket);
        System.out.println("New ticket created: " + ticket.getTicketId());
        return ticket;
    }

    public List<SupportTicket> getTicketsForMember(Member member) {
        return member.getSupportTickets();
    }
    
    public List<SupportTicket> getAllTickets() {
        return allTickets;
    }
}

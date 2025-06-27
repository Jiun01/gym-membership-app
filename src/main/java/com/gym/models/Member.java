package com.gym.models;

import java.util.ArrayList;
import java.util.List;

public class Member extends User {
    private String name;
    private String email;
    private String phoneNumber;
    private Membership membership;
    private List<SupportTicket> supportTickets;

    public Member(String userId, String password, String name, String email, String phoneNumber) {
        super(userId, password, "Member");
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.supportTickets = new ArrayList<>();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public Membership getMembership() { return membership; }
    public void setMembership(Membership membership) { this.membership = membership; }
    public List<SupportTicket> getSupportTickets() { return supportTickets; }

    public void updateProfile(String name, String email, String phoneNumber) {
        this.setName(name);
        this.setEmail(email);
        this.setPhoneNumber(phoneNumber);
    }
    
    public void renewMembership() {
        if (this.membership != null) { this.membership.renew(); }
    }

    public SupportTicket createSupportTicket(String title, String description) {
        SupportTicket newTicket = new SupportTicket(this.getUserId(), title, description);
        this.supportTickets.add(newTicket);
        return newTicket;
    }
}

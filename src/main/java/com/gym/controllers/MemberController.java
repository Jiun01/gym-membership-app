package com.gym.controllers;

import com.gym.models.Member;
import com.gym.models.Membership;
import java.time.LocalDate;

public class MemberController {
    private Member member;

    public MemberController(Member member) {
        this.member = member;
        if (member.getMembership() == null) {
             member.setMembership(new Membership("Basic", LocalDate.now(), 49.99));
        }
    }

    public String viewProfile() {
        return "Name: " + member.getName() + "\nEmail: " + member.getEmail() + "\nPhone: " + member.getPhoneNumber();
    }
    
    public String viewMembership() {
        Membership m = member.getMembership();
        if (m != null) {
            return "Plan: " + m.getPlanType() + "\nStatus: " + m.getStatus() + "\nEnd Date: " + m.getEndDate();
        }
        return "No active membership found.";
    }

    public void renewMembership() {
        member.renewMembership();
    }
}

package com.gym.controllers;

import com.gym.models.Admin;
import com.gym.models.Receptionist;
import com.gym.models.SupportTicket;
import com.gym.models.Member;
import java.util.List;

public class StaffController {
    
    public void manageMemberByAdmin(Admin admin, Member member, String newName, String newEmail) {
        admin.manageMemberAccount(member, newName, newEmail);
    }

    public void respondToTicketByReceptionist(Receptionist receptionist, SupportTicket ticket, String response) {
        receptionist.respondToSupportTicket(ticket, response);
    }
}

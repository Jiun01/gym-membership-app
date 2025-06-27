package com.gym.models;

public class Admin extends Staff {

    public Admin(String userId, String password, String staffId, String name) {
        super(userId, password, staffId, name, "Admin");
    }

    public void changeMembershipPricing(Membership membership, double newPrice) {
        membership.setPrice(newPrice);
        System.out.println("Membership price updated to: " + newPrice);
    }

    public void manageMemberAccount(Member member, String name, String email) {
        member.setName(name);
        member.setEmail(email);
        System.out.println("Member " + member.getUserId() + "'s account has been updated.");
    }
}

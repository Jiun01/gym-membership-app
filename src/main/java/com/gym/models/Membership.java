package com.gym.models;

import java.time.LocalDate;

public class Membership {
    private String planType;
    private LocalDate startDate;
    private LocalDate endDate;
    private double price;
    private String status;

    public Membership(String planType, LocalDate startDate, double price) {
        this.planType = planType;
        this.startDate = startDate;
        this.endDate = startDate.plusYears(1);
        this.price = price;
        this.updateStatus();
    }

    public String getPlanType() { return planType; }
    public void setPlanType(String planType) { this.planType = planType; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getStatus() { updateStatus(); return status; }

    public void renew() {
        if (this.status.equals("Active")) {
            this.endDate = this.endDate.plusYears(1);
        } else {
            this.startDate = LocalDate.now();
            this.endDate = this.startDate.plusYears(1);
        }
        updateStatus();
        System.out.println("Membership renewed. New end date: " + this.endDate);
    }

    private void updateStatus() {
        if (LocalDate.now().isAfter(this.endDate)) {
            this.status = "Expired";
        } else {
            this.status = "Active";
        }
    }
}

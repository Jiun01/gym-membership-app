package com.gym.models;

public abstract class Staff extends User {
    private String staffId;
    private String name;

    public Staff(String userId, String password, String staffId, String name, String userType) {
        super(userId, password, userType);
        this.staffId = staffId;
        this.name = name;
    }

    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}

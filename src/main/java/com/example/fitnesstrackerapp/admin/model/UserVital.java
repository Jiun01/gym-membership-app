package com.example.fitnesstrackerapp.admin.model;


public class UserVital {

    private Long userId;
    private String age;
    private String height;
    private String current_weight;
    private String target_weight;

    public UserVital(Long userid, String age, String height, String currentWeight, String targetWeight) {
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getCurrent_weight() {
        return current_weight;
    }

    public void setCurrent_weight(String current_weight) {
        this.current_weight = current_weight;
    }

    public String getTarget_weight() {
        return target_weight;
    }

    public void setTarget_weight(String target_weight) {
        this.target_weight = target_weight;
    }

    public UserVital() {
    }


    public UserVital(String age, String height, String current_weight, String target_weight) {
        this.age = age;
        this.height = height;
        this.current_weight = current_weight;
        this.target_weight = target_weight;
    }

    @Override
    public String toString() {
        return "\n{\n\"userId\":" + userId + ",\n\"age\":" + age + ",\n\"height\":" + height + ",\n\"current_weight\":" + current_weight + ",\n\"target_weight\":" + target_weight + "\n}";

    }

}

package com.smarttraffic.traffic_management_system.dto;

public class RegisterRequestDto {
    private String name;
    private String badgeId;
    private String password;
    private String phone;
    private String email;
    private String jurisdictionArea;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(String badgeId) {
        this.badgeId = badgeId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJurisdictionArea() {
        return jurisdictionArea;
    }

    public void setJurisdictionArea(String jurisdictionArea) {
        this.jurisdictionArea = jurisdictionArea;
    }
}
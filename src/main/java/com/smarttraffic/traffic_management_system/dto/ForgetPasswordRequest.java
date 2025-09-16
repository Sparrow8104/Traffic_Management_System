package com.smarttraffic.traffic_management_system.dto;

public class ForgetPasswordRequest {
    private String emailOrBadgeId;

    public String getEmailOrBadgeId() {
        return emailOrBadgeId;
    }

    public void setEmailOrBadgeId(String emailOrBadgeId) {
        this.emailOrBadgeId = emailOrBadgeId;
    }
}

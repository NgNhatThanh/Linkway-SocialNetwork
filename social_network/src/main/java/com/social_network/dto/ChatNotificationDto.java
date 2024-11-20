package com.social_network.dto;

public class ChatNotificationDto {

    private Integer chatNotificationId;
    private String userId;
    private String firstName;
    private String lastName;

    public ChatNotificationDto() {
    }

    public ChatNotificationDto(Integer chatNotificationId, String userId, String firstName, String lastName) {
        this.chatNotificationId = chatNotificationId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getChatNotificationId() {
        return chatNotificationId;
    }

    public void setChatNotificationId(Integer chatNotificationId) {
        this.chatNotificationId = chatNotificationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

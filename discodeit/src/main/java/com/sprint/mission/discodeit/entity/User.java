package com.sprint.mission.discodeit.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class User{
    private final UUID id;
    private final Long createdAt;
    private Long updatedAt;
    private String userName;
    private String email;
    private String phone;

    public User(String username, String email, String phone) {
        this.id = UUID.randomUUID();
        long now = System.currentTimeMillis();
        this.createdAt = now;
        this.updatedAt = now;
        this.userName = username;
        this.email = email;
        this.phone = phone;
    }

    public UUID getId() {
        return id;
    }

    public String getCreatedAt() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(this.createdAt));
    }

    public String getUpdatedAt() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(this.updatedAt));
    }

    public String getUsername() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void update(String newUsername, String newEmail, String newPhone) {
        this.userName = newUsername;
        this.email = newEmail;
        this.phone = newPhone;
    }

    @Override
    public String toString() {
        return "유저: " + userName + "\n" +
                "이메일: " + email + "\n" +
                "전화번호: " + phone  + "\n" +
                "UserID: " + id;
    }

}

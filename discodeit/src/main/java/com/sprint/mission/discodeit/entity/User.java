package com.sprint.mission.discodeit.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class User{
    private final UUID id;
    private final long createdAt;
    private long updatedAt;
    private String Username;
    private String Email;
    private String Phone;

    public User(String username, String email, String phone) {
        this.id = UUID.randomUUID();
        long now = System.currentTimeMillis();
        this.createdAt = now;
        this.updatedAt = now;
        this.Username = username;
        this.Email = email;
        this.Phone = phone;
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
        return Username;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhone() {
        return Phone;
    }

    public void update(String newUsername, String newEmail, String newPhone) {
        this.Username = newUsername;
        this.Email = newEmail;
        this.Phone = newPhone;
    }

    @Override
    public String toString() {
        return "유저: " + Username + "\n" +
                "이메일: " + Email + "\n" +
                "전화번호: " + Phone  + "\n" +
                "UserID: " + id;
    }

}

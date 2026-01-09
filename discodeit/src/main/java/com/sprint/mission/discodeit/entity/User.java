package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class User extends BasicEntity{
    private UUID id;
    private long createdAt;
    private long updatedAt;
    private String Username;
    private String Email;
    private String Phone;

    public User(String username, String email, String phone) {
        super();
        this.Username = username;
        this.Email = email;
        this.Phone = phone;
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
}

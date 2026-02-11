package com.sprint.mission.discodeit.entity;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private Instant createdAt;
    private Instant updatedAt;
    //
    private String username;
    private String email;
    private String password;
    private UUID profiledId;
    private MultipartFile profile;

    public User(String username, String email, String password) {
        this.id = UUID.randomUUID();
        Instant now = Instant.now();
        this.createdAt = now;
        //
        this.username = username;
        this.email = email;
        this.password = password;
        //
        this.profiledId = UUID.randomUUID();
        this.profile = profile;
    }

    public void update(String newUsername, String newEmail, String newPassword) {
        boolean anyValueUpdated = false;
        if (newUsername != null && !newUsername.equals(this.username)) {
            this.username = newUsername;
            anyValueUpdated = true;
        }
        if (newEmail != null && !newEmail.equals(this.email)) {
            this.email = newEmail;
            anyValueUpdated = true;
        }
        if (newPassword != null && !newPassword.equals(this.password)) {
            this.password = newPassword;
            anyValueUpdated = true;
        }

        if (anyValueUpdated) {
            this.updatedAt = Instant.now();
        }
    }
}

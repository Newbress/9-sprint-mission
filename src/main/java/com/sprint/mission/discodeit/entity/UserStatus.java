package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Getter
public class UserStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private UUID userId;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant lastConnection;

    public UserStatus(UUID userId, Instant lastConnection) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
        this.lastConnection = now;
    }

    public boolean isOnline(){
        return Duration.between(lastConnection, Instant.now()).toMinutes() >=5;
    }
    public void update(Instant newLastConnection){
        boolean anyValueUpdated = false;
        if(newLastConnection != null && ! newLastConnection.equals(this.lastConnection)){
            this.lastConnection = newLastConnection;
            anyValueUpdated = true;
        }

    }
}

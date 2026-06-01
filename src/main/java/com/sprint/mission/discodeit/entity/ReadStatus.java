package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;


@Getter
public class ReadStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private UUID userId;
    private UUID channelId;

    private Instant lastRead;
    private Instant createdAt;
    private Instant updatedAt;

    public ReadStatus(UUID userId, UUID channelId, Instant lastRead) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.channelId = channelId;
        Instant now = Instant.ofEpochSecond(System.currentTimeMillis());
        this.createdAt = now;
        this.updatedAt = now;
        this.lastRead = now;
    }
    public void update(Instant newLastRead) {
        boolean anyValueUpdated = false;

        if(newLastRead != null && !newLastRead.equals(this.lastRead)) {
            this.lastRead = newLastRead;
            anyValueUpdated = true;
        }
    }
}

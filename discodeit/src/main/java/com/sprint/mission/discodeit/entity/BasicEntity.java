package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class BasicEntity {
    private UUID id;
    private long createdAt;
    private long updatedAt;

    public BasicEntity() {
        this.id = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = this.createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }
}

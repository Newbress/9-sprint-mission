package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class BinaryContent implements Serializable {
    private static final long serialVersionUID = 1L;
    private final UUID id; // binary파일들의 아이디
    private final UUID userId; //
    private final UUID messageId;
    private byte[] content;
    private String contentType;
    private Instant createdAt;



    public BinaryContent(UUID id, UUID userId, UUID messageId, byte[] content, String contentType) {
        this.id = id;
        this.userId = userId;
        this.messageId = messageId;
        this.content = content;
        this.contentType = contentType;
        Instant now = Instant.now();
        this.createdAt = now;
    }
}

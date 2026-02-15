package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class BinaryContent implements Serializable {
    private static final long serialVersionUID = 1L;
    private final UUID id; // binary파일들의 아이디
    private String fileName;
    private byte[] bytes;
    private String contentType;
    private final Instant createdAt;



    public BinaryContent(String fileName, String contentType, byte[] data) {
        this.id = UUID.randomUUID();
        this.fileName = fileName;
        this.bytes = data;
        this.contentType = contentType;
        Instant now = Instant.now();
        this.createdAt = now;
    }
}

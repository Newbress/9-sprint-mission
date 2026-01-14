package com.sprint.mission.discodeit.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Channel {
    private final UUID id;
    private final Long createdAt;
    private Long updatedAt;
    private String Chatroom; //chat room

    public Channel(String chatroom) {
        this.id = UUID.randomUUID();
        long now = System.currentTimeMillis();
        this.createdAt = now;
        this.updatedAt = now;
        this.Chatroom = chatroom;
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

    public String getChatroom() {
        return Chatroom;
    }

    public void update(String newChatroom) {
        this.Chatroom = newChatroom;

    }

    @Override
    public String toString() {
        return "채널 이름='" + Chatroom + "\n" +
                "채널 ID: "+ id + "\n";
    }
}

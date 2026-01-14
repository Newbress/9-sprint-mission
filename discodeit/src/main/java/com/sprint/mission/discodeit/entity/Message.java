package com.sprint.mission.discodeit.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Message {
    private final UUID id;
    private final Long createdAt;
    private Long updatedAt;
    private String userName;
    private final UUID chatRoomid;
    private String content;


    public Message( UUID chatroomid, String userName, String content) {
        this.id = UUID.randomUUID();
        long now = System.currentTimeMillis();
        this.createdAt = now;
        this.updatedAt = now;
        this.chatRoomid = chatroomid;
        this.userName = userName;
        this.content = content;
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

    public String getUserName() {
        return userName;
    }

    public UUID getChatRoomid() {
        return chatRoomid;
    }

    public String getContent() {
        return content;
    }

    public void update( String newContent) {
        this.content = newContent;
    }

    @Override
    public String toString() {
        return "채널 ID: " + chatRoomid + "\n" +
                "작성자 ID: " + userName + "\n" +
                "메세지 내용: " + content + "\n" +
                "메세지 ID: " + id + "\n";
    }
}

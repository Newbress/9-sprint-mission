package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private final UUID id;
    private final Long createdAt;
    private Long updatedAt;
    private String userName;
    private String channelName;
    private String content;
    private UUID userId;
    private UUID channelId;


    public Message( String channelName, String userName, String content) {
        this.id = UUID.randomUUID();
        long now = System.currentTimeMillis();
        this.createdAt = now;
        this.updatedAt = now;
        this.channelName = channelName;
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

    public String getChannelName() {
        return channelName;
    }

    public String getContent() {
        return content;
    }

    public Object getUserId() {
        return id;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    public Object getChannelId() {
        return id;
    }

    public void setChannelId(UUID channelId) {
        this.channelId = channelId;
    }

    public void update( String newContent) {
        this.content = newContent;
    }

    @Override
    public String toString() {
        return "채널 ID: " + channelName + "\n" +
                "작성자 ID: " + userName + "\n" +
                "메세지 내용: " + content + "\n" +
                "메세지 ID: " + id + "\n";
    }


}

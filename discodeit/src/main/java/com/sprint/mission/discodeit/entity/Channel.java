package com.sprint.mission.discodeit.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Channel {
    private final UUID id;
    private final Long createdAt;
    private Long updatedAt;
    private String channelName; //chat room

    public Channel(String channelName) {
        this.id = UUID.randomUUID();
        long now = System.currentTimeMillis();
        this.createdAt = now;
        this.updatedAt = now;
        this.channelName = channelName;
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

    public String getChannelName() {
        return channelName;
    }

    public void update(String newchannelName) {
        this.channelName = newchannelName;

    }

    @Override
    public String toString() {
        return "채널 이름: " + channelName + "\n" +
                "채널 ID: "+ id + "\n";
    }
}

package com.sprint.mission.discodeit.entity;


import java.util.UUID;

public class Message extends BasicEntity {
    private UUID id;
    private long createdAt;
    private long updatedAt;

    private User Author;
    private Channel Channel;
    private String Content;

    public Message( Channel channel, User author, String content) {
        super(); //공통 필드 초기화
        this.Channel = channel;
        this.Author = author;
        this.Content = content;
    }

    public User getAuthor() {
        return Author;
    }

    public Channel getChannel() {
        return Channel;
    }

    public String getContent() {
        return Content;
    }

    public void update(User newAuthor, Channel newChannel, String newContent, String newEmoji) {
        this.Author = newAuthor;
        this.Channel = newChannel;
        this.Content = newContent;
    }
}

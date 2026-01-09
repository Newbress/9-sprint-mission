package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.service.ChannelService;

import java.util.UUID;

public class Channel extends BasicEntity {
    private String Chname;
    private User Author ;
    private String Title;
    private String Content;

    public Channel(String chname, String title, User author, String content) {
        super();
        this.Chname = chname;
        this.Author = author;
        this.Title = title;
        this.Content = content;
    }

    public String getChannel() {
        return Chname;
    }

    public User getAuthor() {

        return Author;
    }

    public String getTitle() {

        return Title;
    }

    public String getContent() {

        return Content;
    }

    public void update(String newChname, User newAuthor, String newTitle, String newContent) {
        this.Chname = newChname;
        this.Author = newAuthor;
        this.Title = newTitle;
        this.Content = newContent;

    }

}

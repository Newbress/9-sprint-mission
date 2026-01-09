package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.ChannelService;
import java.util.ArrayList;
import java.util.List;

public class JCFUserService implements UserService , MessageService, ChannelService {
    private final List<User> data;

    public JCFUserService() {
        this.data = new ArrayList<>();
    }
    String Username;
    String Email;
    String Phone;


    //User
    @Override
    public User addUser(User user) {
        boolean flag = data.add(user);
        if(flag) {
            System.out.println("추가 했다");
            return user;
        }else {
            System.out.println("추가 안됨");
            return null;
        }
    }
    @Override
    public User getUser(String Username) {
        return null;
    }

    @Override
    public List<User> getUser() {
        return List.of();
    }

    @Override
    public User editUser(String newUsername, String newEmail, String newPhone) {
        return null;
    }

    @Override
    public boolean delUser(String Username) {
        return false;
    }

    //Message

    @Override
    public Message addMsg(Message msg) {
        return null;
    }

    @Override
    public Message getMsg(String Content) {
        return null;
    }

    @Override
    public Message editMsg(String newContent) {
        return null;
    }

    @Override
    public boolean delMsg(String Content) {
        return false;
    }

    //Channel

    @Override
    public Channel addCh(Channel ch) {
        return null;
    }

    @Override
    public Channel getCh(String Chname) {
        return null;
    }

    @Override
    public Channel editCh(String newChname, String Title, String newContent) {
        return null;
    }

    @Override
    public boolean delCh(String Chname, String Title, String Content) {
        return false;
    }
}

package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

public class JCFUserService implements UserService {
    private final Map<UUID, User> data = new HashMap<>();

    @Override
    public User addUser(String inputUsername, String inputEmail, String inputPhone) {
        User newusers = new User(inputUsername, inputEmail, inputPhone);
        data.put(newusers.getId(), newusers);
        return newusers;
    }

    @Override
    public User getUserName(String userName) {
        return null;
    }


    @Override
    public User getUserEmail(String email) {
        for(User user : data.values()){
            user.getEmail();
            if(user.getEmail().equals(email)){
                return user;
            }
        }
        throw new IllegalArgumentException("해당 이메일 없음");
    }

    @Override
    public User getUserPhone(String phone) {
        for(User user : data.values()){
            user.getPhone();
            if(user.getPhone().equals(phone)) {
                return user;
            }
        }
        throw new IllegalArgumentException("해당 전화번호 없음");
    }

    @Override
    public List<User> getall() {
        return new ArrayList<>(data.values());
    }

    @Override
    public User editUser(UUID id, String newUsername, String newEmail, String newPhone) {
        User user = data.get(id);
        if(user == null) {
            return null;
        }
        user.update(newUsername, newEmail, newPhone);
        data.put(id, user);
        return user;

    }

    @Override
    public boolean delUser(UUID id) {
        return data.remove(id) != null;
    }
}

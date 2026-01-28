package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.util.*;

public class JCFUserRepository implements UserRepository {
    private final Map<UUID, User> data = new HashMap<>();

    @Override
    public User saveUser(User user) {
        data.put(user.getId(), user);
        return user;
    }

    @Override
    public User findUserName(String userName) {
        for (User user : data.values()) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
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
    public User findById(UUID id) {
        return null;
    }

    @Override
    public User findByContactInfo(String input) {
        return null;
    }

    @Override
    public List<User> getall() {
        return new ArrayList<>(data.values());
    }

    @Override
    public User editUser(User findThing, String newUsername, String newEmail, String newPhone) {
        User user = data.get(findThing.getId());
        user.update(newUsername, newEmail, newPhone);
        return user;

    }

    @Override
    public boolean delUser(UUID id) {
        return data.remove(id) != null;
    }
}


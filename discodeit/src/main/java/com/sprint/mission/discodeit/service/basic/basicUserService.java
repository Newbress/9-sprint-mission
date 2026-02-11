package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class basicUserService implements UserService {
    private final Map<UUID, User> data = new HashMap<>();
    private final UserRepository userRepository;

    public basicUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(String inputUsername, String inputEmail, String inputPhone) {
        User user = new User(inputUsername, inputEmail, inputPhone);
        return userRepository.saveUser(user);
    }

    @Override
    public User findUserName(String userName) {
        return userRepository.findUserName(userName);
    }

    @Override
    public User getUserEmail(String email) {
        return userRepository.findByContactInfo(email);
    }

    @Override
    public User getUserPhone(String phone) {
        return userRepository.findByContactInfo(phone);
    }

    @Override
    public User fileFindID(UUID id) {
        return null;
    }

    @Override
    public List<User> getall() {
        return userRepository.getall();
    }

    @Override
    public User editUser(User findThing, String newUsername, String newEmail, String newPhone) {
        User user = new User(newUsername, newEmail,newPhone);
        return userRepository.saveUser(user);
    }

    @Override
    public boolean delUser(UUID id) {
        return userRepository.delUser(id);
    }
}

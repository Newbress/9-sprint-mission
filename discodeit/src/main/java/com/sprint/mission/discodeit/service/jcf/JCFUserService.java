package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

public class JCFUserService implements UserService {
    private final Map<UUID, User> data = new HashMap<>();
    private final UserRepository userRepository;

    public JCFUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(String inputUsername, String inputEmail, String inputPhone) {
        User newusers = new User(inputUsername, inputEmail, inputPhone);
        return userRepository.saveUser(newusers);
    }

    @Override
    public User findUserName(String userName) {
        return userRepository.findUserName(userName);
    }


    @Override
    public User getUserEmail(String email) {
        return userRepository.getUserEmail(email);
    }

    @Override
    public User getUserPhone(String phone) {
        return userRepository.getUserPhone(phone);
    }

    @Override
    public User fileFindID(UUID id) {
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

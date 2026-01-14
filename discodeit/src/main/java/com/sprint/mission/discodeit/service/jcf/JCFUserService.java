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
    public User addUser(User user) {
       return data.put(user.getId(), user);

    }

    @Override
    public User getUser(UUID id) {
        return data.get(id);
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
        } else {
            user.update(newUsername, newEmail, newPhone);
            data.put(id, user);
            return user;
        }
    }

    @Override
    public boolean delUser(UUID id) {
        return data.remove(id) != null;
    }
}

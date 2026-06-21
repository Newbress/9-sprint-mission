package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.DTO.User.LoginRequest;
import com.sprint.mission.discodeit.entity.User;

public interface AuthService {
    User login(LoginRequest dto);
}

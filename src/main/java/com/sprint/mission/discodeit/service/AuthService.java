package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.DTO.User.AuthDTO;
import com.sprint.mission.discodeit.entity.User;

public interface AuthService {
    User login(AuthDTO dto);
}

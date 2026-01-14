package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;
import java.util.List;
import java.util.UUID;

public interface UserService {
    // 생성
    User addUser(User user);

    // 조회
    User getUser(UUID id);

    // 전체 조회
    List<User> getall();

    // 수정
    User editUser(UUID id, String newUsername, String newEmail, String newPhone);

    // 삭제
    boolean delUser(UUID id);
}

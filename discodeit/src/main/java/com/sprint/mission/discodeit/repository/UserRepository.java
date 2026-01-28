package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User saveUser(User user);

    // 조회
    User findUserName(String userName);

    User getUserEmail(String email);

    User getUserPhone(String phone);

    User findById(UUID id);

    User findByContactInfo(String input);
    // 전체 조회
    List<User> getall();

    // 수정
    User editUser(User findThing,String newUsername, String newEmail, String newPhone);

    // 삭제
    boolean delUser(UUID id);

}

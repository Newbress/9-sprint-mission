package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;
import java.util.List;
import java.util.UUID;

public interface UserService {
    // 생성
    User addUser(String inputUsername, String inputEmail, String inputPhone);

    // 조회
    User getUserName(String userName);

    User getUserEmail(String email);

    User getUserPhone(String phone);

    // 전체 조회
    List<User> getall();

    // 수정
    User editUser(User findThing,String newUsername, String newEmail, String newPhone);

    // 삭제
    boolean delUser(UUID id);
}

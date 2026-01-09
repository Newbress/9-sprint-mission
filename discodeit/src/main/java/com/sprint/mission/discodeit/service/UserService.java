package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;
import java.util.List;

public interface UserService {
    //생성
    User addUser(User user);

    //조회
    User getUser(String Username);

    //전체 조회
    List<User> getUser();

    //수정
    User editUser(String newUsername, String newEmail, String newPhone);

    //삭제
    boolean delUser(String Username);
}

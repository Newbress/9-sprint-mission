package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserStatusRepository {
    UserStatus save(UserStatus userStatus);
    Optional<UserStatus> findId(UUID id);
    Optional<UserStatus> findByUserId(UUID userId);
    List<UserStatus> findAll();
    boolean existsById(UUID id);
    boolean existsByIdAndUserId(UUID id, UUID userId);
    void deleteById(UUID id);
    void deleteAllByUserId(UUID userId);
}

package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatusRepository extends JpaRepository<UserStatus,UUID> {

  UserStatus save(UserStatus userStatus);

  Optional<UserStatus> findByUserId(UUID userId);

  boolean existsById(UUID id);

  void deleteById(UUID id);

  void deleteByUserId(UUID userId);
}

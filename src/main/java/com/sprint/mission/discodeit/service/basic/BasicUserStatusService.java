package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.DTO.UserStatus.UserStatusCreateDTO;
import com.sprint.mission.discodeit.entity.DTO.UserStatus.UserStatusResponseDTO;
import com.sprint.mission.discodeit.entity.DTO.UserStatus.UserStatusUpdateDTO;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicUserStatusService implements UserStatusService {
    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;

    @Override
    public UserStatus createDTO(UserStatusCreateDTO dto) {
        if(!userRepository.existsById(dto.userId())){
            throw new IllegalArgumentException("존재하지 않은 사용자입니다");
        }
        if(userStatusRepository.existsByIdAndUserId(dto.id(), dto.userId())){
            throw new IllegalArgumentException("이미 존재합니다.");
        }
        UserStatus userStatus = new UserStatus(dto.userId(), dto.lastConnection());
        return userStatusRepository.save(userStatus);
    }

    @Override
    public UserStatus find(UUID id) {
        return userStatusRepository.findId(id)
                .orElseThrow(()-> new NoSuchElementException("not found"));
    }

    @Override
    public List<UserStatus> findAll() {
        return userStatusRepository.findAll();
    }

    @Override
    public UserStatusResponseDTO updateDTO(UUID id, UserStatusUpdateDTO dto) {
        UserStatus userStatus = userStatusRepository.findId(id)
                .orElseThrow(() -> new NoSuchElementException("id not found " + id + " not found"));
        userStatus.update(dto.lastConnection());
        userStatusRepository.save(userStatus);

        return new UserStatusResponseDTO(
                userStatus.getLastConnection()
        );
    }

    @Override
    public UserStatusResponseDTO updateByUserId(UUID userId, UserStatusUpdateDTO dto) {
        UserStatus userStatus = userStatusRepository.findId(userId)
                .orElseThrow(() -> new NoSuchElementException("id not found " + userId + " not found"));
        userStatus.update(dto.lastConnection());
        userStatusRepository.save(userStatus);

        return new UserStatusResponseDTO(
                userStatus.getLastConnection()
        );
    }

    @Override
    public void delete(UUID id) {
        if(!userStatusRepository.existsById(id)){
            throw new NoSuchElementException("not found id " + id);
        }
        userStatusRepository.deleteById(id);
    }
}

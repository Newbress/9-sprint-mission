package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.DTO.ReadStatus.ReadStatusCreateDTO;
import com.sprint.mission.discodeit.entity.DTO.ReadStatus.ReadStatusResponseDTO;
import com.sprint.mission.discodeit.entity.DTO.ReadStatus.ReadStatusUpdateDTO;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicReadStatusService implements ReadStatusService {
    private final ReadStatusRepository readStatusRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

    @Override
    public ReadStatus createDTO(ReadStatusCreateDTO dto) {
        if(dto.userId() == null || !userRepository.existsById(dto.userId())){
            throw new IllegalArgumentException("존재하지 않거나 이미 존재하는 아이디입니다.");
        }
        if(dto.channelId() == null || !channelRepository.existsById(dto.channelId())){
            throw new IllegalArgumentException("존재하지 않거나 이미 존재하는 채널입니다.");
        }
        if(readStatusRepository.existByUserIdAndChannelId(dto.userId(), dto.channelId())){
            throw new IllegalArgumentException("이미 존재합니다.");
        }
        ReadStatus readStatus = new ReadStatus(dto.userId(), dto.channelId(), dto.lastRead());
        return readStatusRepository.save(readStatus);
    }

    @Override
    public ReadStatus find(UUID id) {
        return readStatusRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("not found"));
    }

    @Override
    public List<ReadStatus> findAllByUserId(UUID userId) {
        if(userRepository.existsById(userId)){
            throw new NoSuchElementException("User not found with id " + userId + " not found");
        }
        return readStatusRepository.findAll();
    }

    @Override
    public ReadStatusResponseDTO updateDTO(UUID id, ReadStatusUpdateDTO dto) {
        ReadStatus readStatus = readStatusRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("id not found " + id));
        readStatus.update(dto.lastRead());
        readStatusRepository.save(readStatus);

        return new ReadStatusResponseDTO(
                readStatus.getLastRead()
        );
    }

    @Override
    public void delete(UUID id) {
        if(!readStatusRepository.existById(id)){
            throw new NoSuchElementException("not found id" + id);
        }
        readStatusRepository.deleteById(id);
    }
}

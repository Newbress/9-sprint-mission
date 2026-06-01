package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.DTO.ReadStatus.CreateReadStatusRequest;
import com.sprint.mission.discodeit.entity.DTO.ReadStatus.UpdateReadStatusRequest;
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
    public ReadStatus create(CreateReadStatusRequest request) {
        if(request.userId() == null || !userRepository.existsById(request.userId())){
            throw new IllegalArgumentException("존재하지 않거나 이미 존재하는 아이디입니다.");
        }
        if(request.channelId() == null || !channelRepository.existsById(request.channelId())){
            throw new IllegalArgumentException("존재하지 않거나 이미 존재하는 채널입니다.");
        }
        if(readStatusRepository.existByUserIdAndChannelId(request.userId(), request.channelId())){
            throw new IllegalArgumentException("이미 존재합니다.");
        }
        ReadStatus readStatus = new ReadStatus(request.userId(), request.channelId(), request.lastRead());
        return readStatusRepository.save(readStatus);
    }



    @Override
    public ReadStatus find(UUID id) {
        return readStatusRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("not found"));
    }

    @Override
    public List<ReadStatus> findAllByUserId(UUID userId) {
        if(!userRepository.existsById(userId)){
            throw new NoSuchElementException("User not found with id " + userId + " not found");
        }
        return readStatusRepository.findAll();
    }

    @Override
    public ReadStatus update(UUID id, UpdateReadStatusRequest request) {
        ReadStatus readStatus = readStatusRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("id not found " + id));
        readStatus.update(request.lastRead());
        readStatusRepository.save(readStatus);

        return readStatus;
    }

    @Override
    public void delete(UUID id) {
        if(!readStatusRepository.existById(id)){
            throw new NoSuchElementException("not found id" + id);
        }
        readStatusRepository.deleteById(id);
    }
}

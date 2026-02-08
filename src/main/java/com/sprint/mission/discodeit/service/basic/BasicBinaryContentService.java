package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.DTO.BinaryContent.BinaryContentCreateDTO;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicBinaryContentService implements BinaryContentService {
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public BinaryContent createDTO(BinaryContentCreateDTO dto) {
        BinaryContent binaryContent = new BinaryContent(dto.id(), dto.userId(), dto.MessageId(), dto.content(), dto.contentType());
        return binaryContentRepository.save(binaryContent);
    }

    @Override
    public BinaryContent find(UUID id) {
        return binaryContentRepository.findId(id)
                .orElseThrow(()-> new NoSuchElementException("not found"));
    }

    @Override
    public List<BinaryContent> findAllByIdIn(Set<UUID> id) {
        return binaryContentRepository.findAll();
    }

    @Override
    public void delete(UUID id) {
        if(!binaryContentRepository.existsById(id)) {
            throw new NoSuchElementException("not found id " + id);
        }
        binaryContentRepository.deleteById(id);
    }
}

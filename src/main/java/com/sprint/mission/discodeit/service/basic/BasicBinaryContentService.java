package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.DTO.BinaryContent.CreateBinaryContentRequest;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicBinaryContentService implements BinaryContentService {
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public BinaryContent create(CreateBinaryContentRequest request) {
        BinaryContent binaryContent = new BinaryContent(request.fileName(), request.contentType(), request.data());
        return binaryContentRepository.save(binaryContent);
    }

    @Override
    public BinaryContent find(UUID id) {
        return binaryContentRepository.findId(id)
                .orElseThrow(()-> new NoSuchElementException("not found"));
    }

    @Override
    public List<BinaryContent> findAll(UUID id) {
        return binaryContentRepository.findAll();
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

    @Override
    public BinaryContent uploadFile(MultipartFile file) {
        String contentType = MediaTypeFactory.getMediaType(file.getOriginalFilename())
                .map(MediaType::toString)
                .orElse("application/octet-stream");

        try {
            byte[] fileBytes = file.getBytes();

            return this.create(new CreateBinaryContentRequest(
                    file.getOriginalFilename(),
                    contentType,
                    fileBytes
            ));
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패 - " + e.getMessage());
        }
    }

}

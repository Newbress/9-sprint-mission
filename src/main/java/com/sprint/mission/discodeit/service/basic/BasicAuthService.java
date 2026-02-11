package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.DTO.User.AuthDTO;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasicAuthService implements AuthService {
    private UserRepository userRepository;

    @Override
    public User login(AuthDTO dto) {
        Optional<User> userOptional = userRepository.findByUsername(dto.username());
        if(userOptional.isEmpty()) {
            throw new RuntimeException("유저이름이 없어요라");
        }
        User user = userOptional.get();
        if(!user.getPassword().equals(dto.password())){
            throw new RuntimeException("비밀번호 없어요라");
        }
        return user;
    }
}

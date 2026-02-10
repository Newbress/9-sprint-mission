package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.entity.DTO.User.AuthDTO;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @RequestMapping(value = "/api/users/login", method = RequestMethod.POST)
    public ResponseEntity<User> login(@RequestBody AuthDTO dto) {
        User userLogin = authService.login(dto);
        return ResponseEntity.ok(userLogin);
    }
}

package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.entity.DTO.User.UserCreateDTO;
import com.sprint.mission.discodeit.entity.DTO.User.UserFindDTO;
import com.sprint.mission.discodeit.entity.DTO.User.UserUpdateDTO;
import com.sprint.mission.discodeit.entity.DTO.UserStatus.UserStatusResponseDTO;
import com.sprint.mission.discodeit.entity.DTO.UserStatus.UserStatusUpdateDTO;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserStatusService userStatusService;

    @RequestMapping(value = "/api/users" , method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody UserCreateDTO dto){
        User user = userService.createDTO(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @RequestMapping(value = "/api/users/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<UserFindDTO> updateUser(@PathVariable UUID id, @RequestBody UserUpdateDTO dto){
        UserFindDTO user = userService.updateDTO(id, dto);
        return ResponseEntity.ok(user);
    }

    @RequestMapping(value = "/api/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id){
        userService.delete(id);
        return ResponseEntity.noContent().build();

    }

    @RequestMapping(value = "/api/users/findAll",    method = RequestMethod.GET)
    public ResponseEntity<List<UserFindDTO>> findUserAll(){
        List<UserFindDTO> users = userService.findAllDTO();
        return ResponseEntity.ok(users);
    }

    @RequestMapping(value = "/api/users/{id}/userStatus", method = RequestMethod.PATCH)
    public ResponseEntity<UserStatusResponseDTO> isOnline(@PathVariable UUID id, @RequestBody UserStatusUpdateDTO dto){
        UserStatusResponseDTO user = userStatusService.updateDTO(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}

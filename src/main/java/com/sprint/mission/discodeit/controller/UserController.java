package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.DTO.User.CreateUserRequest;
import com.sprint.mission.discodeit.entity.DTO.User.ResponseUser;
import com.sprint.mission.discodeit.entity.DTO.User.UpdateUserRequest;
import com.sprint.mission.discodeit.entity.DTO.UserStatus.UpdateUserStatusRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserStatusService userStatusService;
    private final BinaryContentService binaryContentService;

    @RequestMapping(value ="/api/users", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<User> create( @RequestPart("userInfo") CreateUserRequest createUserRequest,
                                            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        UUID profileImageId = null;

        if (!imageFile.isEmpty()) {
            BinaryContent binaryContent = binaryContentService.uploadFile(imageFile);

            profileImageId = binaryContent.getId();
        }

        User newUser = userService.create(createUserRequest, profileImageId);
        return ResponseEntity.ok(newUser);
    }

    @RequestMapping(value = "/api/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody UpdateUserRequest dto) {
        User user = userService.update(id, dto);
        return ResponseEntity.ok(user);
    }

    @RequestMapping(value = "/api/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id){
        userService.delete(id);
        return ResponseEntity.noContent().build();

    }

    @RequestMapping(value = "/api/users/findAll",    method = RequestMethod.GET)
    public ResponseEntity<List<ResponseUser>> findUserAll(){
        List<ResponseUser> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @RequestMapping(value = "/api/users/{userId}/userStatus", method = RequestMethod.PUT)
    public ResponseEntity<UserStatus> isOnline(@PathVariable UUID userId, @RequestBody UpdateUserStatusRequest dto){
        UserStatus user = userStatusService.updateByUserId(userId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}

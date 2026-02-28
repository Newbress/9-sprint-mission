package com.sprint.mission.discodeit.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "변경할 User 온라인 상태 정보")
public record UserUpdateRequest(
    String newUsername,
    String newEmail,
    String newPassword
) {

}

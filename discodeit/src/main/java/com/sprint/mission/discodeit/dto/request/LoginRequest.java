package com.sprint.mission.discodeit.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "로그인 정보")
public record LoginRequest(
    String username,
    String password
    //, UUID profileId
) {
}

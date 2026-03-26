package com.sprint.mission.discodeit.exception;

import static com.sprint.mission.discodeit.exception.ErrorCode.CHANNEL_NOT_FOUND;
import static com.sprint.mission.discodeit.exception.ErrorCode.PRIVATE_CHANNEL_UPDATE;
import static com.sprint.mission.discodeit.exception.ErrorCode.USER_NOT_FOUND;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.NoSuchElementException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception e) {
    log.error("예상치 못한 오류 발생: {}", e.getMessage(), e);
    ErrorResponse errorResponse = new ErrorResponse(e, INTERNAL_SERVER_ERROR.value());
    return ResponseEntity
        .status(INTERNAL_SERVER_ERROR)
        .body(errorResponse);
  }

  @ExceptionHandler(DiscodeitException.class)
  public ResponseEntity<ErrorResponse> handleDiscodeitException(DiscodeitException exception) {
    log.error("커스텀 예외 발생: code={}, message={}", exception.getErrorCode(), exception.getMessage(), exception);
    HttpStatus status = determineHttpStatus(exception);
    ErrorResponse response = new ErrorResponse(exception, status.value());
    return ResponseEntity
        .status(status)
        .body(response);
  }

  private HttpStatus determineHttpStatus(DiscodeitException exception) {
    ErrorCode errorCode = exception.getErrorCode();
    return switch (errorCode) {
      case USER_NOT_FOUND, CHANNEL_NOT_FOUND, MESSAGE_NOT_FOUND, BINARY_CONTENT_NOT_FOUND,
           READ_STATUS_NOT_FOUND, USER_STATUS_NOT_FOUND -> HttpStatus.NOT_FOUND;
      case DUPLICATE_USER, DUPLICATE_USERNAME, DUPLICATE_EMAIL -> HttpStatus.CONFLICT;
      case PRIVATE_CHANNEL_UPDATE, USER_STATUS_INVALID, MESSAGE_CONTENT_INVALID, BINARY_CONTENT_TYPE_INVALID-> HttpStatus.BAD_REQUEST;
      case INTERNAL_SERVER_ERROR -> INTERNAL_SERVER_ERROR;
    };
  }
}

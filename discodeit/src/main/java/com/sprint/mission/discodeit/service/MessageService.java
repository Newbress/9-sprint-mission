package com.sprint.mission.discodeit.service;


import com.sprint.mission.discodeit.entity.Message;
import java.util.List;
import java.util.UUID;

public interface MessageService {
    // 생성
    Message sendMsg(String channelName, String userName, String content);

    // 조회
    Message getMsg(UUID id);

    Message getUserMsg(String userMsg);

    Message getChannelMsg(String channelMsg);

    // 전체 조회
    List<Message> getall();
    // 수정
    Message editMsg(UUID id,String newContent);

    // 삭제
    boolean delMsg(UUID id);

}

package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageRepository {
    Message saveMsg(Message msg);

    // 조회
    Message findMsgId(String id);

    Message getUserMsg(String userMsg);

    Message getChannelMsg(String channelMsg);

    // 전체 조회
    List<Message> findAllMsgId();

    List<Message> getUserAll(String inputUser);

    List<Message> getChannelAll(String inputChannel);
    // 수정
    Message editMsg(UUID id, String newContent);

    // 삭제
    boolean delMsg(UUID id);
}

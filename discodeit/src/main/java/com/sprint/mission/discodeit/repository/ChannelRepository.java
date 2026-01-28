package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;
import java.util.List;
import java.util.UUID;

public interface ChannelRepository {
    Channel saveCh(Channel channel);


    // 조회
    Channel findCh(String channelName);

    // 전체조회
    List<Channel> findAll();
    // 수정
    Channel editCh(UUID id, String newChatroom);

    // 삭제
    boolean delCh(UUID id);
}

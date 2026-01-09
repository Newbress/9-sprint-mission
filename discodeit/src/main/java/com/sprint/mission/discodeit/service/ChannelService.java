package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;


public interface ChannelService {
    //생성
    Channel addCh(Channel ch);

    //조회
    Channel getCh(String Chname);

    //수정
    Channel editCh(String newChname, String Title, String newContent);

    //삭제
    boolean delCh(String Chname, String Title, String Content);
}

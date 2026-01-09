package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;

public interface MessageService {
    //생성
    Message addMsg(Message msg);

    //조회
    Message getMsg(String Content);

    //수정
    Message editMsg(String newContent);

    //삭제
    boolean delMsg(String Content);

}

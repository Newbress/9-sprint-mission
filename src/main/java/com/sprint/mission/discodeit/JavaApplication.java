package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.*;
import com.sprint.mission.discodeit.repository.file.*;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.basic.BasicChannelService;
import com.sprint.mission.discodeit.service.basic.BasicMessageService;
import com.sprint.mission.discodeit.service.basic.BasicUserService;

public class JavaApplication {
//    static User setupUser(UserService userService) {
//        User user = userService.CreateUserRequest("woody", "woody@codeit.com", "woody1234");
//        return user;
//    }
//
//    static Channel setupChannel(ChannelService channelService) {
//        Channel channel = channelService.create(ChannelType.PUBLIC, "공지", "공지 채널입니다.");
//        return channel;
//    }
//
//    static void messageCreateTest(MessageService messageService, Channel channel, User author) {
//        Message message = messageService.create("안녕하세요.", channel.getId(), author.getId());
//        System.out.println("메시지 생성: " + message.getId());
//    }

    public static void main(String[] args) {
        // 레포지토리 초기화
        UserRepository userRepository = new FileUserRepository();
        ChannelRepository channelRepository = new FileChannelRepository();
        MessageRepository messageRepository = new FileMessageRepository();
        FileBinaryContentRepository fileBinaryContentRepository = new FileBinaryContentRepository();
        UserStatusRepository userStatusRepository = new FileUserStatusRepository();
        ReadStatusRepository readStatusRepository = new FileReadStatusRepository();
        BinaryContentRepository binaryContentRepository = new FileBinaryContentRepository();

        // 서비스 초기화
        UserService userService = new BasicUserService(userRepository, userStatusRepository, fileBinaryContentRepository);
        ChannelService channelService = new BasicChannelService(channelRepository, messageRepository,readStatusRepository);
        MessageService messageService = new BasicMessageService(messageRepository, channelRepository, userRepository, binaryContentRepository);

//        // 셋업
//        User user = setupUser(userService);
//        Channel channel = setupChannel(channelService);
//        // 테스트
//        messageCreateTest(messageService, channel, user);
    }
}

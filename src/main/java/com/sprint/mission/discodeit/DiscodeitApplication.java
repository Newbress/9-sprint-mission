package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.DTO.BinaryContent.AttachedFilesDTO;
import com.sprint.mission.discodeit.entity.DTO.Channel.ChannelCreateDTO;
import com.sprint.mission.discodeit.entity.DTO.Channel.ChannelCreatePrivateDTO;
import com.sprint.mission.discodeit.entity.DTO.Channel.ChannelFindDTO;
import com.sprint.mission.discodeit.entity.DTO.Channel.ChannelUpdateDTO;
import com.sprint.mission.discodeit.entity.DTO.Message.MessageCreateDTO;
import com.sprint.mission.discodeit.entity.DTO.Message.MessageUpdateDTO;
import com.sprint.mission.discodeit.entity.DTO.User.UserCreateDTO;
import com.sprint.mission.discodeit.entity.DTO.User.UserFindDTO;
import com.sprint.mission.discodeit.entity.DTO.User.UserUpdateDTO;
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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.util.List;
import java.util.UUID;


@SpringBootApplication
public class DiscodeitApplication {

//	static User setupUser(UserService userService) {
//		User user = userService.create("woody", "woody@codeit.com", "woody1234");
//		return user;
//	}
//
//	static Channel setupChannel(ChannelService channelService) {
//		Channel channel = channelService.create(ChannelType.PUBLIC, "공지", "공지 채널입니다.");
//		return channel;
//	}
//
//	static void messageCreateTest(MessageService messageService, Channel channel, User author) {
//		Message message = messageService.create("안녕하세요.", channel.getId(), author.getId());
//		System.out.println("메시지 생성: " + message.getId());
//	}
//
//	static void userTest(UserService userService) {
//		System.out.println("\n========== [테스트 시작] ==========\n");
//
//		try {
//			//DTO 유저 생성
//			System.out.println("1. 유저 생성 (createDTO)");
//			// 테스트할 때마다 이메일이 겹치지 않게 랜덤값 추가
//			String randomStr = UUID.randomUUID().toString().substring(0, 5);
//			UserCreateDTO createDTO = new UserCreateDTO(
//					"user_" + randomStr,
//					"test_" + randomStr + "@codeit.com",
//					"1234",
//					null
//			);
//
//			User createdUser = userService.createDTO(createDTO);
//			System.out.println("   -> 생성 성공: " + createdUser.getUsername() + " (ID: " + createdUser.getId() + ")");
//
//			//전체 조회
//			System.out.println("\n2. 전체 조회 (findAllDTO)");
//			List<UserFindDTO> allUsers = userService.findAllDTO();
//			for (UserFindDTO u : allUsers) {
//				System.out.println("   - " + u.username() + " [Online: " + u.isOnline() + "]");
//			}
//
//			//단건 조회
//			System.out.println("\n3. 단건 조회 (findDTO)");
//			UserFindDTO foundUser = userService.findDTO(createdUser.getId());
//			System.out.println("   -> 조회 성공: " + foundUser.email());
//
//			//업데이트
//			System.out.println("\n4. 유저 수정 (updateDTO)");
//			UserUpdateDTO updateDTO = new UserUpdateDTO(
//					"update_" + randomStr,
//					"update_" + randomStr + "@codeit.com",
//					"5678",
//					null
//			);
//			UserFindDTO updatedUser = userService.updateDTO(createdUser.getId(), updateDTO);
//			System.out.println("   -> 수정 성공: " + updatedUser.username());
//
//			//삭제
//			System.out.println("\n5. 유저 삭제 (delete)");
//			userService.delete(createdUser.getId());
//			System.out.println("   -> 삭제 요청 완료");
//
//			// 삭제 확인
//			try {
//				userService.find(createdUser.getId());
//				System.out.println("   [실패] 삭제된 유저가 여전히 조회됩니다.");
//			} catch (Exception e) {
//				System.out.println("   [성공] 삭제된 유저 조회 시 예외 발생: " + e.getMessage());
//			}
//
//		} catch (Exception e) {
//			System.err.println("\n[테스트 중 치명적 오류 발생]");
//			e.printStackTrace();
//		}
//
//		System.out.println("\n========== [테스트 종료] ==========");
//	}
//
//	static void channelTest(ChannelService channelService) {
//		System.out.println("\n========== [테스트 시작] ==========\n");
//
//		try {
//			// 임의의 유저 ID 생성 (테스트 시나리오용)
//			UUID userA = UUID.randomUUID(); // Private 채널에 초대될 사람
//			UUID userB = UUID.randomUUID(); // Private 채널에 초대될 사람
//			UUID outsider = UUID.randomUUID(); // 초대받지 못한 사람
//
//			// 1. Public 채널 생성 (createDTO)
//			System.out.println("1. Public 채널 생성");
//			ChannelCreateDTO publicDto = new ChannelCreateDTO("자유게시판", "누구나 환영합니다");
//			Channel publicChannel = channelService.createDTO(publicDto);
//			System.out.println("   -> 생성 완료: [" + publicChannel.getType() + "] " + publicChannel.getName());
//
//
//			// 2. Private 채널 생성 (createPrivateDTO)
//			System.out.println("\n2. Private 채널 생성 (UserA, UserB 초대)");
//			// userA와 userB를 멤버로 추가
//			ChannelCreatePrivateDTO privateDto = new ChannelCreatePrivateDTO(List.of(userA, userB));
//			Channel privateChannel = channelService.createPrivateDTO(privateDto);
//			System.out.println("   -> 생성 완료: [" + privateChannel.getType() + "] ID: " + privateChannel.getId());
//
//
//			// 3. 채널 목록 조회 (findAllDTO) - 권한 테스트
//			System.out.println("\n3. 권한별 채널 조회 테스트");
//
//			// [UserA 시점] Public + Private 모두 보여야 함
//			List<ChannelFindDTO> listForMember = channelService.findAllDTO(userA);
//			System.out.println("   [UserA 조회] (기대값: 2개) -> 실제: " + listForMember.size() + "개");
//
//			// [Outsider 시점] Public만 보여야 함
//			List<ChannelFindDTO> listForOutsider = channelService.findAllDTO(outsider);
//			System.out.println("   [Outsider 조회] (기대값: 1개) -> 실제: " + listForOutsider.size() + "개");
//
//
//			// 4. 단건 조회 (findDTO) - 상세 정보
//			System.out.println("\n4. Private 채널 상세 조회 (참여자 목록 확인)");
//			ChannelFindDTO privateInfo = channelService.findDTO(privateChannel.getId());
//			if (privateInfo.userIds() != null) {
//				System.out.println("   -> 참여자 수: " + privateInfo.userIds().size() + "명 (UserA, UserB 포함)");
//			} else {
//				System.out.println("   -> [오류] 참여자 목록이 null입니다.");
//			}
//
//
//			// 5. 채널 수정 (updateDTO) - Public 성공 / Private 실패
//			System.out.println("\n5. 채널 수정 테스트");
//
//			// [Public 수정] 성공해야 함
//			ChannelUpdateDTO updateDto = new ChannelUpdateDTO("자유게시판(수정됨)", "설명 변경");
//			ChannelFindDTO updatedPublic = channelService.updateDTO(publicChannel.getId(), updateDto);
//			System.out.println("   -> Public 수정 성공: " + updatedPublic.name());
//
//			// [Private 수정] 예외 발생해야 함
//			System.out.println("   -> Private 수정 시도 (예외 발생 기대)");
//			try {
//				channelService.updateDTO(privateChannel.getId(), updateDto);
//				System.out.println("   [실패] Private 채널이 수정되었습니다.");
//			} catch (IllegalArgumentException e) {
//				System.out.println("   [성공] 예외 발생 확인: " + e.getMessage());
//			}
//
//			// 6. 삭제 (delete)
//			System.out.println("\n6. 채널 삭제");
//			channelService.delete(publicChannel.getId());
//			channelService.delete(privateChannel.getId());
//			System.out.println("   -> 삭제 요청 완료");
//
//			// 삭제 확인
//			try {
//				channelService.find(publicChannel.getId());
//				System.out.println("   [실패] 삭제된 채널이 조회됩니다.");
//			} catch (Exception e) {
//				System.out.println("   [성공] 조회 시 예외 발생 확인");
//			}
//
//		} catch (Exception e) {
//			System.err.println("\n[테스트 중 에러 발생]");
//			e.printStackTrace();
//		}
//
//		System.out.println("\n========== [테스트 종료] ==========");
//	}
//
//	static void MessageTest(UserService userService, ChannelService channelService, MessageService messageService) {
//		System.out.println("\n========== [테스트 시작] BasicMessageService 검증 ==========\n");
//
//		try {
//			// [사전 준비] 작성자(User)와 채널(Channel) 생성
//			System.out.println("0. 사전 데이터 준비 (유저 & 채널 생성)");
//			String randomStr = UUID.randomUUID().toString().substring(0, 5);
//
//			// 유저 생성
//			User author = userService.create("msgUser_" + randomStr, "msg_" + randomStr + "@test.com", "1234");
//			System.out.println("   -> 작성자 생성: " + author.getUsername());
//
//			// 채널 생성
//			Channel channel = channelService.create(ChannelType.PUBLIC, "메시지방_" + randomStr, "테스트용");
//			System.out.println("   -> 채널 생성: " + channel.getName());
//
//
//			// 메시지 생성 (createDTO) - 첨부파일 포함 테스트
//			System.out.println("\n1. 메시지 생성 (createDTO)");
//
//			// 더미 첨부파일 생성 (실제 파일 대신 바이트 배열 사용)
//			AttachedFilesDTO dummyFile = new AttachedFilesDTO(
//					"text/plain",
//					"hello".getBytes()
//			);
//
//			MessageCreateDTO createDto = new MessageCreateDTO(
//					"안녕하세요! 첫 메시지입니다.",
//					author.getId(),
//					channel.getId(),
//					List.of(dummyFile) // 첨부파일 1개 포함
//			);
//
//			Message createdMsg = messageService.createDTO(createDto);
//			System.out.println("   -> 생성 성공: ID=" + createdMsg.getId());
//			System.out.println("   -> 내용: " + createdMsg.getContent());
//
//
//			// 2. 채널별 메시지 조회 (findAllByChannelId)
//			System.out.println("\n2. 채널 내 메시지 조회 (findAllByChannelId)");
//			List<Message> messages = messageService.findAllByChannelId(channel.getId());
//			System.out.println("   -> 조회된 메시지 개수: " + messages.size() + "개");
//			messages.forEach(m -> System.out.println("      - " + m.getContent()));
//
//
//			// 메시지 수정 (updateDTO)
//			System.out.println("\n3. 메시지 수정 (updateDTO)");
//			MessageUpdateDTO updateDto = new MessageUpdateDTO(
//					"수정된 메시지입니다!!",
//					null // 첨부파일 추가 없음
//			);
//
//			Message updatedMsg = messageService.updateDTO(createdMsg.getId(), updateDto);
//			System.out.println("   -> 수정 성공: " + updatedMsg.getContent());
//
//			// 수정 확인 (다시 조회)
//			Message foundMsg = messageService.find(createdMsg.getId());
//			if ("수정된 메시지입니다!!".equals(foundMsg.getContent())) {
//				System.out.println("   [검증] DB 반영 확인됨.");
//			} else {
//				System.out.println("   [실패] DB 내용이 다릅니다: " + foundMsg.getContent());
//			}
//
//
//			//메시지 삭제 (delete)
//			System.out.println("\n4. 메시지 삭제 (delete)");
//			messageService.delete(createdMsg.getId());
//			System.out.println("   -> 삭제 요청 완료");
//
//			// 삭제 확인
//			try {
//				messageService.find(createdMsg.getId());
//				System.out.println("   [실패] 삭제된 메시지가 여전히 조회됩니다.");
//			} catch (Exception e) {
//				System.out.println("   [성공] 조회 시 예외 발생 확인: " + e.getMessage());
//			}
//
//		} catch (Exception e) {
//			System.err.println("\n[테스트 중 에러 발생]");
//			e.printStackTrace();
//		}
//
//		System.out.println("\n========== [테스트 종료] ==========");
//	}

	public static void main(String[] args) {
		//SpringApplication.run(DiscodeitApplication.class, args);
		ConfigurableApplicationContext context = SpringApplication.run(DiscodeitApplication.class, args);

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


		// 셋업
//		User user = setupUser(userService);
//		Channel channel = setupChannel(channelService);
//		// 테스트
//		messageCreateTest(messageService, channel, user);
//		//userTest(userService);
//		//channelTest(channelService);
//		MessageTest(userService,channelService, messageService);
	}
}

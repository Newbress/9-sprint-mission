import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;

import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;

import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import com.sprint.mission.discodeit.service.basic.basicChannelService;
import com.sprint.mission.discodeit.service.basic.basicMessageService;
import com.sprint.mission.discodeit.service.basic.basicUserService;
import com.sprint.mission.discodeit.service.file.FileChannelService;
import com.sprint.mission.discodeit.service.file.FileUserService;

import com.sprint.mission.discodeit.service.jcf.JCFChannelService;
import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class JavaApplication {
    public static void main(String[] args) {

        //UserService userService = new FileUserService();
        //ChannelService channelService = new FileChannelService();
        //ChannelService channelService = new JCFChannelService();
        //UserService userService = new JCFUserService();
        //MessageService messageService = new JCFMessageService(userService, channelService);

        UserRepository userRepository = new FileUserRepository();
        ChannelRepository channelRepository = new FileChannelRepository();
        MessageRepository messageRepository = new FileMessageRepository();
        
        UserService userService = new basicUserService(userRepository);
        ChannelService channelService = new basicChannelService(channelRepository);
        MessageService messageService = new basicMessageService(
                messageRepository,
                userRepository,
                channelRepository
        );


        boolean isRunning = true;
        while(isRunning) {
            System.out.println("=================");
            System.out.println("==== 메인 메뉴 ====");
            System.out.println("==== 1. 채널 ====");
            System.out.println("==== 2. 유저 ====");
            System.out.println("==== 3. 메세지 ====");

            Scanner sc = new Scanner(System.in);
            int MainMenu = sc.nextInt();
            sc.nextLine();

            if(MainMenu == 1) {
                // Channel
                System.out.println("=====================");
                System.out.println("==== 1. 채널 생성 ====");
                System.out.println("==== 2. 채널 조회 ====");
                System.out.println("=== 3. 채널 전체 조회 ===");
                System.out.println("==== 4. 채널 수정 ====");
                System.out.println("==== 5. 채널 삭제 ====");
                System.out.println("==== 6. 종료하기 ====");

                int ChMenu = sc.nextInt();
                sc.nextLine();
                switch (ChMenu)
                {
                    case 1:{
                        // 생성
                        System.out.println("채널 이름 입력하기");
                        String inputChannelName = sc.nextLine();

                        Channel newChannel = channelService.addCh(inputChannelName);
                        System.out.println(newChannel);
                        System.out.println("생성 시간" + newChannel.getCreatedAt());
                        break;
                    }

                    case 2:{
                        // 조회
                        System.out.println("채널 이름 입력하기");
                        String inputChannelName = sc.nextLine();
                        try {
                            Channel findChannelName = channelService.findCh(inputChannelName);
                            System.out.println("단건 조회: \n" + findChannelName);
                        }catch (IllegalArgumentException e) {
                            System.out.println("잘못 입력했습니다.");
                        }
                        break;
                    }

                    case 3:
                        //전체 조회
                        List<Channel> all = channelService.findAll();
                        for(Channel c : all) {
                            System.out.println(c);
                        }
                        break;

                    case 4: {
                        // 수정
                        System.out.println("수정할 채널 ID 넣기");
                        String inputUserId = sc.nextLine().trim();

                        try{
                            UUID uuid = UUID.fromString(inputUserId);

                            System.out.println("채널 수정하기");
                            String newChannelId = sc.nextLine();

                            Channel edit= channelService.editCh(uuid, newChannelId);
                            if(edit != null) {
                                System.out.println("수정 완료");
                                System.out.println(edit);
                                System.out.println("수정 시간" + edit.getUpdatedAt());
                            }else {
                                System.out.println("수정 실패");
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("잘못된 ID 형식입니다." + e.getMessage());
                            e.printStackTrace();
                        }
                        break;
                    }

                    case 5:
                        // 삭제
                        System.out.println("채널 ID 삭제하기");
                        String inputChannel = sc.nextLine();
                        try{
                            UUID uuid = UUID.fromString(inputChannel);
                            boolean isDeleted = channelService.delCh(uuid);
                            if(isDeleted) {
                                System.out.println("삭제 됐습니다");
                            } else {
                                System.out.println("삭제 실패");
                            }

                        }catch (IllegalArgumentException e) {
                            System.out.println("잘못된 ID형식입니다");
                        }
                        break;
                    case 6:
                        System.out.println("종료합니다.");
                        isRunning = false;
                        break;

                    default:
                        System.out.println("잘못 누르셨습니다 다시 눌러주세요");
                }
            }
            else if(MainMenu== 2) {
                // User
                System.out.println("=== 1. 유저 생성 ===");
                System.out.println("=== 2. 유저 조회 ===");
                System.out.println("=== 3. 전체 조회 ===");
                System.out.println("=== 4. 유저 수정 ===");
                System.out.println("=== 5. 유저 삭제 ===");
                System.out.println("=== 6. 종료하기 ===");

                int UserMenu = sc.nextInt();
                sc.nextLine();
                switch (UserMenu) {

                    case 1:{
                        // 생성
                        System.out.println("유저 이름 넣기");
                        String inputUsername = sc.nextLine();
                        System.out.println("이메일 입력하기");
                        String inputEmail = sc.nextLine();
                        System.out.println("전화번호 입력하기");
                        String inputPhone = sc.nextLine();

                        User newusers = userService.addUser(inputUsername, inputEmail, inputPhone);
                        System.out.println(newusers);
                        System.out.println("생성 시간" + newusers.getCreatedAt());
                        break;
                    }

                    case 2:{
                        // 이메일/전화번호를 입력해 조회하게 하기
                        System.out.println("1. 이메일로 유저 조회하기");
                        System.out.println("2. 전화번호로 유저 조회하기");
                        int inputFind = sc.nextInt();
                        sc.nextLine();
                        try{
                            if(inputFind == 1) {
                                System.out.println("이메일 입력: " );
                                String inputEmail = sc.nextLine();
                                User findEmailUser = userService.getUserEmail(inputEmail);
                                System.out.println("유저 이름 : " + findEmailUser);
                            } else if(inputFind == 2) {
                                System.out.println("전화번호 입력: ");
                                String inputPhone = sc.nextLine();
                                User findPhoneUser = userService.getUserPhone(inputPhone);
                                System.out.println("유저 이름 : " + findPhoneUser);
                            }
                        }catch (IllegalArgumentException e){
                            System.out.println("잘못 입력했습니다. 다시 입력해주세요");
                        }
                        break;
                    }
                    case 3:
                        // 전체 조회
                        List<User> all = userService.getall();
                        for (User u : all) {
                            System.out.println(u);
                        }
                        break;

                    case 4:{
                        // 이메일 또는 전화번호로 유저 정보 수정
                        System.out.println("1. 이메일로 유저 수정하기");
                        System.out.println("2. 전화번호로 유저 수정하기");
                        int inputEdit = sc.nextInt();
                        sc.nextLine();

                        try {
                            if(inputEdit == 1) {
                                System.out.println("이메일 입력: " );
                                String inputEmail = sc.nextLine();
                                User confirmUser = userService.getUserEmail(inputEmail);
                                System.out.println("수정 전: \n" + confirmUser );

                                System.out.println("이름 수정하기");
                                String newUsername = sc.nextLine();
                                System.out.println("이메일 수정하기");
                                String newEmail = sc.nextLine();
                                System.out.println("전화번호 수정하기");
                                String newPhone = sc.nextLine();

                                User findEmailUser = userService.editUser(confirmUser,newUsername, newEmail, newPhone);
                                System.out.println("유저 이름 : " + findEmailUser);
                                System.out.println("수정 시간" +findEmailUser.getUpdatedAt());

                            } else if(inputEdit == 2) {
                                System.out.println("전화번호 입력: " );
                                String inputPhone = sc.nextLine();
                                User confirmUser = userService.getUserPhone(inputPhone);
                                System.out.println("수정 전: \n" + confirmUser );

                                System.out.println("이름 수정하기");
                                String newUsername = sc.nextLine();
                                System.out.println("이메일 수정하기");
                                String newEmail = sc.nextLine();
                                System.out.println("전화번호 수정하기");
                                String newPhone = sc.nextLine();
                                User findPhoneUser = userService.editUser(confirmUser,newUsername, newEmail, newPhone);
                                System.out.println("유저 이름 : " + findPhoneUser);
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("잘못 입력했습니다. 다시 입력해주세요");
                        }break;
                    }

                    case 5:
                        // 삭제
                        System.out.println("삭제할 유저 ID 입력하기");
                        String InputID = sc.nextLine();
                        try{
                            UUID uuid = UUID.fromString(InputID);
                            boolean isDeleted = userService.delUser(uuid);
                            if(isDeleted) {
                                System.out.println("삭제 됐습니다");
                            }else {
                                System.out.println("삭제 실패: 해당 ID를 찾을 수 없습니다.");
                            }

                        }catch (IllegalArgumentException e) {
                            System.out.println("잘못된 ID 형식입니다.");
                        }
                        break;

                    case 6:
                        System.out.println("종료합니다.");
                        isRunning = false;
                        break;

                    default:
                        System.out.println("잘못 누르셨습니다 다시 눌러주세요");

                }
            }
            else if (MainMenu == 3) {
                // Message
                System.out.println("=====================");
                System.out.println("==== 1. 메세지 전달 ====");
                System.out.println("==== 2. 메세지 조회 ====");
                System.out.println("=== 3. 메세지 전체 조회 ===");
                System.out.println("==== 4. 메세지 수정 ====");
                System.out.println("==== 5. 메세지 삭제 ====");
                System.out.println("==== 6. 종료하기 ====");

                int MsgMenu = sc.nextInt();
                sc.nextLine();
                switch (MsgMenu)
                {
                    case 1:{
                        // 메세지 전달
                        // User 이름 입력
                        System.out.println("유저 이름 입력하기");
                        String userName = sc.nextLine();
                        User user = userService.findUserName(userName);
                        if(user == null) {
                            System.out.println("잘못 입력했습니다 다시 확인하세요.");
                            break;
                        }

                        // Ch ID 입력
                        System.out.println("채널 이름 입력하기");
                        String inputChannelName = sc.nextLine();
                        Channel channelName = channelService.findCh(inputChannelName);
                        if(channelName == null) {
                            System.out.println("잘못 입력했습니다 다시 확인하세요.");
                            break;
                        }

                        System.out.println("메세지 입력하기");
                        String inputMsg = sc.nextLine();

                        try{
                            Message sendMsg = messageService.sendMsg(
                                    channelName.findChannelName(), user.getUserName(), inputMsg);
                            sendMsg.setUserId(user.getId());
                            sendMsg.setChannelId(channelName.getId());
                            System.out.println("메세지 ID: " + sendMsg.getId());
                            System.out.println("생성 시간: " + sendMsg.getCreatedAt());


                        } catch(IllegalArgumentException e) {
                            System.out.println("오류 발생");
                        }
                        break;
                    }

                    case 2:{
                        System.out.println("메세지 조회하기");
                        String inputMsgId = sc.nextLine();
                        try{
                            Message findMsgId = messageService.findMsgId(inputMsgId);
                            System.out.printf("단건 조회: \n"+ findMsgId);
                        }catch (IllegalArgumentException e) {
                            System.out.println("잘못 입력했습니다.");
                        }break;
//                        // 유저/채널를 입력해 메세지 조회하기
//                        int inputFind = sc.nextInt();
//                        sc.nextLine();
//                        try{
//                            if(inputFind == 1) {
//                                System.out.println("유저 이름 입력: ");
//                                String inputUserName = sc.nextLine();
//                                Message userMsg = messageService.getUserMsg(inputUserName);
//                                System.out.println("유저 메세지 조회: \n" + userMsg);
//                            } else if(inputFind == 2) {
//                                System.out.println("채널 이름 입력: ");
//                                String inputChannelName = sc.nextLine();
//                                Message userMsg = messageService.getChannelMsg(inputChannelName);
//                                System.out.println("채널 메세지 조회: \n" + userMsg);
//                            }
//
//                        }catch (IllegalArgumentException e) {
//                            System.out.println("잘못 입력했습니다. 다시 입력하세요");
//                        }
//                        break;
                    }

                    case 3: {

                        List<Message> findAllMsg = messageService.findAllMstId();
                        for (Message msg : findAllMsg) {
                            System.out.println(msg);
                        }
                        break;


                        // 유저/채널를 입력해 전체 메세지 조회
//                        System.out.println("1. 유저로 전체 메세지 조회하기");
//                        System.out.println("2. 채널로 전체 메세지 조회하기");
//                        int inputFind  = sc.nextInt();
//                        sc.nextLine();
//                        try{
//                            if(inputFind == 1) {
//                                System.out.println("유저 이름 : ");
//                                String inputUserName = sc.nextLine();
//                                List<Message> userMsg= messageService.getUserAll(inputUserName);
//                                System.out.println("유저 전체 메세지 조회 : \n" + userMsg);
//                            } else if(inputFind == 2) {
//                                System.out.println("채널 이름 : ");
//                                String inputChannelName = sc.nextLine();
//                                List<Message> channelMsg = messageService.getChannelAll(inputChannelName);
//                                System.out.println("채널 전체 메세지 조회 : \n" + channelMsg);
//                            }
//                        } catch (IllegalArgumentException e) {
//                            System.out.println("잘못 입력했습니다. 다시 입력하세요라");
//                        }break;
                    }

                    case 4:{
                        // 수정
                        System.out.println("수정할 메세지 ID 넣기");
                        String inputMsg = sc.nextLine();

                        try {
                            UUID uuid = UUID.fromString(inputMsg);

                            System.out.println("메세지 수정하기");
                            String newContent = sc.nextLine();
                            Message edit = messageService.editMsg(uuid, newContent);
                            if(edit != null){
                                System.out.println("수정 완료");
                                System.out.println(edit);
                                System.out.println("수정 시간" +edit.getUpdatedAt());
                            }else {
                                System.out.println("수정 실패: 해당 ID를 찾을 수 없습니다.");
                            }
                        }catch (IllegalArgumentException e) {
                            System.out.println("잘못된 ID형식입니다.");
                        }
                        break;

                    }

                    case 5:{
                        // 삭제
                        System.out.println("삭제할 메세지 ID 입력하기");
                        String inputMsg = sc.nextLine();
                        try {
                            UUID uuid = UUID.fromString(inputMsg);
                            boolean isDeleted = messageService.delMsg(uuid);
                            if(isDeleted) {
                                System.out.println("삭제 됐습니다.");
                            }else {
                                System.out.println("삭제 실패: 해당 ID를 찾을 수 없습니다.");
                            }
                        }catch (IllegalArgumentException e) {
                            System.out.println("잘못된 ID 형식입니다.");
                        }
                        break;
                    }

                    case 6:
                        System.out.println("종료합니다.");
                        isRunning = false;
                        break;

                    default:
                        System.out.println("잘못 누르셨습니다 다시 눌러주세요");
                }
            }
        }
    }
}

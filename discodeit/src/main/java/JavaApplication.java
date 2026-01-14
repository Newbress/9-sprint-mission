import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.jcf.JCFChannelService;
import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class JavaApplication {
    public static void main(String[] args) {
        ChannelService channelService = new JCFChannelService();
        UserService userService = new JCFUserService();
        MessageService messageService = new JCFMessageService(userService, channelService);


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
                    case 1:
                        // 생성
                        System.out.println("채널 이름 입력하기");
                        String inputchannelname = sc.nextLine();
                        Channel newchannel = new Channel(inputchannelname);
                        channelService.addCh(newchannel);
                        System.out.println(newchannel);
                        System.out.println("생성 시간" + newchannel.getCreatedAt());
                        break;

                    case 2:{
                        // 조회
                        System.out.println("채널 ID 입력하기");
                        String inputCh = sc.nextLine().trim();
                        try{
                            java.util.UUID uuid = java.util.UUID.fromString(inputCh);
                            Channel findCh = channelService.getCh(uuid);
                            if(findCh != null) {
                                System.out.println("단건 조회: " + findCh);
                            } else {
                                System.out.println("조회 실패: 채널 ID를 다시 확인 바랍니다.");
                            }
                            System.out.println();
                        }catch (IllegalArgumentException e) {
                            System.out.println("잘못된 ID 형식입니다");
                        }
                        break;
                    }

                    case 3:
                        //전체 조회
                        List<Channel> all = channelService.getall();
                        for(Channel c : all) {
                            System.out.println(c);
                        }
                        break;

                    case 4:
                        // 수정
                        System.out.println("수정할 채널 ID 넣기");
                        String inputId = sc.nextLine();

                        System.out.println("채널 수정하기");
                        String newChatroom = sc.nextLine();

                        try{
                            java.util.UUID uuid = java.util.UUID.fromString(inputId);
                            Channel edit= channelService.editCh(uuid, newChatroom);

                            if(edit != null) {
                                System.out.println("수정 완료");
                                System.out.println(edit);
                                System.out.println("수정 시간" + edit.getUpdatedAt());
                            }else {
                                System.out.println("수정 실패: 해당 ID를 찾을 수 없습니다.");
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("잘못된 ID 형식입니다.");
                        }
                        break;

                    case 5:
                        // 삭제
                        System.out.println("채널 ID 삭제하기");
                        String inputCHannel = sc.nextLine();
                        try{
                            java.util.UUID uuid = java.util.UUID.fromString(inputCHannel);
                            boolean isDeleted = channelService.delCh(uuid);
                            if(isDeleted) {
                                System.out.println("삭제 됐습니다");
                            } else {
                                System.out.println("삭제 실패: 해당 ID를 찾을 수 없습니다");
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

                    case 1:
                        // 생성
                        System.out.println("유저 이름 넣기");
                        String inputUsername = sc.nextLine();
                        System.out.println("이메일 입력하기");
                        String inputEmail = sc.nextLine();
                        System.out.println("전화번호 입력하기");
                        String inputPhone = sc.nextLine();
                        User newusers = new User(inputUsername, inputEmail, inputPhone);
                        userService.addUser(newusers);
                        System.out.println(newusers);
                        System.out.println("생성 시간" + newusers.getCreatedAt());
                        break;

                    case 2:
                        // 조회
                        System.out.println("유저 ID 입력하기");
                        String inputID = sc.nextLine();
                        try {
                            java.util.UUID uuid = java.util.UUID.fromString(inputID);
                            User findUser = userService.getUser(uuid);
                            if(findUser != null){
                                System.out.println("단건 조회: "+ findUser);
                            } else {
                                System.out.println("조회 실패: 유저 ID를 다시 확인 바랍니다. ");
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("잘못된 ID 형식입니다.");
                        }
                        break;

                    case 3:
                        // 전체 조회
                        List<User> all = userService.getall();
                        for (User u : all) {
                            System.out.println(u);
                        }
                        break;

                    case 4:
                        // 수정
                        System.out.println("수정할 유저 ID 넣기");
                        String inputId = sc.nextLine();

                        System.out.println("이름 수정하기");
                        String newUsername = sc.nextLine();
                        System.out.println("이메일 수정하기");
                        String newEmail = sc.nextLine();
                        System.out.println("전화번호 수정하기");
                        String newPhone = sc.nextLine();

                        try{
                            java.util.UUID uuid = java.util.UUID.fromString(inputId);
                            User edit= userService.editUser(uuid, newUsername, newEmail, newPhone);

                            if(edit != null) {
                                System.out.println("수정 완료");
                                System.out.println(edit);
                                System.out.println("수정 시간" +edit.getUpdatedAt());
                            }else {
                                System.out.println("수정 실패: 해당 ID를 찾을 수 없습니다.");
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("잘못된 ID 형식입니다.");
                        }
                        break;

                    case 5:
                        // 삭제
                        System.out.println("삭제할 유저 ID 입력하기");
                        String InputID = sc.nextLine();
                        try{
                            java.util.UUID uuid = java.util.UUID.fromString(InputID);
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
                        // User ID 입력
                        System.out.println("작성자 User ID 입력하기");
                        String userId = sc.nextLine();
                        User user = userService.getUser(UUID.fromString(userId));

                        // Ch ID 입력
                        System.out.println("채널 ID 입력하기");
                        String chId = sc.nextLine();
                        Channel chatroom = channelService.getCh(UUID.fromString(chId));

                        System.out.println("메세지 입력하기");
                        String inputMsg = sc.nextLine();

                        try{
                            Message sendMsg = messageService.sendMsg(
                                    chatroom.getId(), user.getId(), inputMsg);
                            System.out.println("메세지 ID" + sendMsg.getId());
                            System.out.println("생성 시간" + sendMsg.getCreatedAt());


                        } catch(IllegalArgumentException e) {
                            System.out.println("오류 발생");
                        }
                        break;
                    }

                    case 2:{
                        // 조회
                        // Ch ID 입력
                        System.out.println("채널 ID 입력하기");
                        String chId = sc.nextLine().trim();
                        Channel chatroom = channelService.getCh(UUID.fromString(chId));
                        if(chatroom != null){
                            System.out.println("메세지 ID 입력하기");
                            String inputmsg = sc.nextLine().trim();
                            try{
                                java.util.UUID uuid = java.util.UUID.fromString(inputmsg);
                                Message findMsg = messageService.getMsg(uuid);
                                if(findMsg != null) {
                                    System.out.println("단건 조회: " + findMsg);
                                }else {
                                    System.out.println("조회 실패: 메세지 ID를 다시 확인 바랍니다.");
                                }

                            }catch (IllegalArgumentException e) {
                                System.out.println("잘못된 ID 형식입니다");
                            }
                        } else {
                            System.out.println("채널 ID를 잘못 입력했습니다.");
                        }
                        break;
                    }

                    case 3:
                        List<Message> all = messageService.getall();
                        for(Message m : all) {
                            System.out.println(m);
                        }
                        break;

                    case 4:
                        // 수정
                        System.out.println("수정할 메세지 ID 넣기");
                        String inputMSG = sc.nextLine();

                        System.out.println("메세지 수정하기");
                        String newContent = sc.nextLine();

                        try {
                            java.util.UUID uuid = java.util.UUID.fromString(inputMSG);
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

                    case 5:
                        // 삭제
                        System.out.println("삭제할 메세지 ID 입력하기");
                        String Inputmsg = sc.nextLine();
                        try {
                            java.util.UUID uuid = java.util.UUID.fromString(Inputmsg);
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

package com.example.bossi.controller.chat;

import com.example.bossi.dto.chatting.ChatMessage;
import com.example.bossi.entity.chatting.Chatting;
import com.example.bossi.service.chatting.ChattingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Slf4j
//@RestController
@RequiredArgsConstructor
@Tag(name = "ChattingController", description = "채팅 관련 api 입니다.")
//@RequestMapping("/api/v1/users")
@Controller
public class ChattingController {

    private final ChattingService chattingService;

    @MessageMapping("/firstChat") //여기로 전송되면 메서드 호출 -> WebSocketConfig prefixes 에서 적용한건 앞에 생략
    @SendTo("/topic/greetings")   //구독하고 있는 장소로 메시지 전송 (목적지)  -> WebSocketConfig Broker 에서 적용한건 앞에 붙어줘야됨
    public ChatMessage hello(ChatMessage chatMessage) {
        log.info("name: {}", chatMessage);
        String firstMessage = chatMessage.getMessage() +"님 어서오세요";
        return new ChatMessage(firstMessage, "d");
    }

    @MessageMapping("/{roomId}") //여기로 전송되면 메서드 호출 -> WebSocketConfig prefixes 에서 적용한건 앞에 생략
    @SendTo("/room/{roomId}")   //구독하고 있는 장소로 메시지 전송 (목적지)  -> WebSocketConfig Broker 에서 적용한건 앞에 붙어줘야됨
    public void chat(@PathVariable Long roomId, ChatMessage message, Authentication authentication) {

        log.info("authentication: {}", authentication.getAuthorities());
        //채팅 저장
        /*Chatting chat = chattingService.createChat(roomId, message.getSender(), message.getMessage());
        return ChatMessage.builder()
                .roomId(roomId)
                .sender(chat.getSender())
                .senderEmail(chat.getSenderEmail())
                .message(chat.getMessage());*/

    }
}

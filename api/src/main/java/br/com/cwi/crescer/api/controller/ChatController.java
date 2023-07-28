package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.domain.ChatMessage;
import br.com.cwi.crescer.api.domain.ChatNotification;
import br.com.cwi.crescer.api.service.chat.ChatMessageService;
import br.com.cwi.crescer.api.service.chat.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired private ChatMessageService chatMessageService;
    @Autowired private ChatRoomService chatRoomService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        var chatId = chatRoomService
                .getChatId(chatMessage.getSenderId(), chatMessage.getReceiverId(), true);
        chatMessage.setChatId(chatId.get());

        var chat2Id =chatRoomService.getChatId(chatMessage.getReceiverId(), chatMessage.getSenderId(), true);
        ChatMessage mirrorMessage = ChatMessage.builder().chatId(chat2Id.get()).date(chatMessage.getDate()).msgContent(chatMessage.getMsgContent())
                .senderName(chatMessage.getSenderName()).receiverName(chatMessage.getReceiverName()).receiverId(chatMessage.getReceiverId()).senderId(chatMessage.getSenderId())
                .status(chatMessage.getStatus()).build();


        ChatMessage saved = chatMessageService.save(chatMessage);
        chatMessageService.save(mirrorMessage);

        messagingTemplate.convertAndSendToUser(
                String.valueOf(chatMessage.getReceiverId()),"/queue/messages",
                new ChatNotification(
                        saved.getId(),
                        saved.getSenderId(),
                        saved.getSenderName()));


    }


    @GetMapping("/messages/{senderId}/{receiverId}/count")
    public ResponseEntity<Long> countNewMessages(
            @PathVariable Long senderId,
            @PathVariable Long receiverId) {

        return ResponseEntity
                .ok(chatMessageService.countNewMessages(senderId, receiverId));
    }

    @GetMapping("/messages/{senderId}/{receiverId}")
    public ResponseEntity<?> findChatMessages ( @PathVariable Long senderId,
                                                @PathVariable Long receiverId) {
        return ResponseEntity
                .ok(chatMessageService.findChatMessages(senderId, receiverId));
    }


    @GetMapping("/messages/{id}")
    public ResponseEntity<?> findMessage ( @PathVariable Long id) {
        return ResponseEntity
                .ok(chatMessageService.findById(id));
    }
}
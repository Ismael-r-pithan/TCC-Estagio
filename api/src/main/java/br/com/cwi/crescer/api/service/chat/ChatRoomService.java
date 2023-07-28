package br.com.cwi.crescer.api.service.chat;

import br.com.cwi.crescer.api.domain.ChatRoom;
import br.com.cwi.crescer.api.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public Optional<String> getChatId(Long senderId, Long receiverId, Boolean createIfNotExists){

        return chatRoomRepository.findBySenderIdAndReceiverId(senderId, receiverId)
                .map(ChatRoom::getChatId)
                .or(() -> {
                    if(!createIfNotExists){
                        return Optional.empty();
                    }
                    String chatId = String.format("%s_%s", senderId.toString(), receiverId.toString());

                    ChatRoom senderReceiver = ChatRoom.builder()
                            .chatId(chatId)
                            .senderId(senderId)
                            .receiverId(receiverId)
                            .build();

                    String mirrorChatId = String.format("%s_%s", receiverId.toString(), senderId.toString());

                    ChatRoom receiverSender = ChatRoom.builder()
                            .chatId(mirrorChatId)
                            .senderId(receiverId)
                            .receiverId(senderId)
                            .build();

                    chatRoomRepository.save(senderReceiver);
                    chatRoomRepository.save(receiverSender);

                    return Optional.of(chatId);
                });
    }
}

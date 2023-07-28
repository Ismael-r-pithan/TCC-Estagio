package br.com.cwi.crescer.api.service.chat;

import br.com.cwi.crescer.api.domain.ChatMessage;
import br.com.cwi.crescer.api.domain.enums.MessageStatus;
import br.com.cwi.crescer.api.repository.ChatMessageRepository;
import br.com.cwi.crescer.api.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.cwi.crescer.api.domain.enums.MessageStatus.*;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatRoomService chatRoomService;

    public ChatMessage save (ChatMessage chatMessage){
        chatMessage.setStatus(RECEIVED);
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }

    @Transactional
    public List<ChatMessage> findChatMessages(Long senderId, Long receiverId){
        Optional<String> chatId = chatRoomService.getChatId(senderId, receiverId, false);

        ArrayList<ChatMessage> msgs = chatId.map( id -> chatMessageRepository.findByChatIdOrderById(id)).orElse(new ArrayList<ChatMessage>());

        if(msgs.size() > 0){
            updateStatus(senderId, receiverId, DELIVERED);
        }
            return msgs;
    }

    public ChatMessage findById(Long id){
        return chatMessageRepository.findById(id).map(chatMessage -> {
            chatMessage.setStatus(DELIVERED);
            return chatMessageRepository.save(chatMessage);
        }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao buscar mensagem - Não existe"));
    }

    public void updateStatus(Long senderId, Long receiverId, MessageStatus messageStatus){
        chatMessageRepository.updateStatuses(senderId, receiverId, messageStatus);
    }


    public long countNewMessages(Long senderId, Long receiverId){
        return chatMessageRepository.countBySenderIdAndReceiverIdAndStatus(senderId, receiverId, RECEIVED);
    }



}

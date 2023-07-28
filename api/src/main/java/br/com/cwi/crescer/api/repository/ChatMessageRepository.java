package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.ChatMessage;
import br.com.cwi.crescer.api.domain.enums.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    ArrayList<ChatMessage> findByChatId(String chatId);

    long countBySenderIdAndReceiverIdAndStatus(Long senderId, Long receiverId, MessageStatus received);


    @Modifying
    @Query("UPDATE ChatMessage SET status = :status WHERE senderId = :senderId AND receiverId = :receiverId")
    void updateStatuses(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId, @Param("status") MessageStatus status);

    ArrayList<ChatMessage> findByChatIdOrderById(String id);
}

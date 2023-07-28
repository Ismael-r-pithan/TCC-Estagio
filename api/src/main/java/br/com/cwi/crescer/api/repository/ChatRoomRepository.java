package br.com.cwi.crescer.api.repository;


import br.com.cwi.crescer.api.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {

    Optional<ChatRoom> findBySenderIdAndReceiverId(Long senderId, Long receiverId);

}

package br.com.cwi.crescer.api.domain;

import br.com.cwi.crescer.api.domain.enums.MessageStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id") @ToString(of = "id")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chatId;

    private Long receiverId;

    private String receiverName;
    private Long senderId;

    private String senderName;

    private String msgContent;

    private Date date;

    @Enumerated(EnumType.STRING)
    private MessageStatus status;


}

package br.com.cwi.crescer.api.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user_quests")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @Builder
public class UserQuest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "quest_id")
    private Quest quest;

    private LocalDate completedAt;

}

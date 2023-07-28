package br.com.cwi.crescer.api.domain;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @Builder
@ToString(of = "id") @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Quest {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Integer rewards;
    private String recurring;
    private String key;
    private Long experience;

    @ManyToMany(mappedBy = "quests")
    private List<User> users;
}



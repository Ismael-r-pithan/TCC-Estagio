package br.com.cwi.crescer.api.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(of = "id")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "usuario")
public class User {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private int money;
    private String imageProfile;
    private String bannerUrl;
    @Column(columnDefinition = "bpchar")
    private String confirmToken;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean ativo;
    private Long experience;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Permission> permissions = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_quests",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "quest_id")
    )
    private List<Quest> quests = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "user_items",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items;

    public void buyItem(Item item) {
        this.items.add(item);
    }

}
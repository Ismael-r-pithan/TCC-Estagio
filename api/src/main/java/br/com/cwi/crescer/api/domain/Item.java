package br.com.cwi.crescer.api.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor @Builder
@EqualsAndHashCode(of = "id") @ToString(of = "id")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String type;

    private int price;

    private String imageUrl;

    @ManyToMany(mappedBy = "items")
    private List<User> users;
}


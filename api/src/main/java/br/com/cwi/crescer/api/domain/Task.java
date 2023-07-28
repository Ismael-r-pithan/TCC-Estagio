package br.com.cwi.crescer.api.domain;


import br.com.cwi.crescer.api.domain.enums.Category;
import br.com.cwi.crescer.api.domain.enums.Priority;
import br.com.cwi.crescer.api.domain.enums.Visibility;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(of = "id")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Task {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private LocalDate scheduledDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean recurring;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private Priority priority;

    private LocalDate finishedDate;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private User autor;

}
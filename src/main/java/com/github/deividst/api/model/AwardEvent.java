package com.github.deividst.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@Entity
@Table(name = "AWARD_EVENT")
@AllArgsConstructor
@NoArgsConstructor
public class AwardEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "EVENT_YEAR")
    private Integer year;

    @OneToMany(mappedBy = "awardEvent")
    private Set<Movie> movies;

}

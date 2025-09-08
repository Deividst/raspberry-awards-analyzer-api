package com.github.deividst.api.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@Table(name = "MOVIE")
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "EVENT_YEAR")
    private Integer year;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "STUDIO")
    private String studio;

    @Column(name = "PRODUCER")
    private String producer;

    @Column(name = "WINNER")
    private boolean winner;

}

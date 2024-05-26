package com.texoit.worstmovie.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MOVIE")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MOVIE_YEAR", nullable = false)
    private int year;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "WINNER", nullable = false)
    private boolean winner;

    @ManyToMany
    @JoinTable(
            name = "MOVIE_STUDIO",
            joinColumns = @JoinColumn(name = "MOVIE_ID"),
            inverseJoinColumns = @JoinColumn(name = "STUDIO_ID")
    )
    private List<StudioEntity> studios;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "MOVIE_PRODUCER",
            joinColumns = @JoinColumn(name = "MOVIE_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCER_ID")
    )
    private List<ProducerEntity> producers;

}

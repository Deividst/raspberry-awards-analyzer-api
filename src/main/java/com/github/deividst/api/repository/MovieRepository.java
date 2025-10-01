package com.github.deividst.api.repository;

import com.github.deividst.api.model.Movie;
import com.github.deividst.api.repository.projections.ProducerIntervalProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("""
            SELECT new com.github.deividst.api.repository.projections.ProducerIntervalProjection(
                m.producer,
                MIN(m.year),
                MAX(m.year),
                MAX(m.year) - MIN(m.year))
            FROM Movie m
            WHERE m.winner = true
            GROUP BY producer
            HAVING COUNT(*) > 1
            """)
    List<ProducerIntervalProjection> findProducerWithAwardInterval();
}

package com.github.deividst.api.repository;

import com.github.deividst.api.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("""
            SELECT m FROM Movie m
            WHERE (
                SELECT COUNT(m2.producer)
                FROM Movie m2
                WHERE m2.producer = m.producer AND m2.winner = true
            ) > 1 ORDER BY m.year ASC
            """)
    List<Movie> findWithMoreThanOneVictory();
}

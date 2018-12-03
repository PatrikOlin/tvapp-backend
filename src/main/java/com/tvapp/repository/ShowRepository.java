package com.tvapp.repository;

import com.tvapp.model.Show;
import org.hibernate.sql.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.From;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Integer> {

    Show findByTitle(String name);

    List<Show> findByTitleContaining(String name);

    @Query(value = "SELECT s.id, s.title, s.image_url, s.overview, s.status_of_show, s.next_air_date " +
            "FROM shows s INNER JOIN watchlist f ON f.show_id = s.id INNER JOIN user u ON f.user_id = u.user_id" +
            " WHERE u.user_id = ?", nativeQuery = true)
    List<Show> findAllShowsByUserId(int id);
}

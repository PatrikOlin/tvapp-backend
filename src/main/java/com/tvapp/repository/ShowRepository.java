package com.tvapp.repository;

import com.tvapp.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Integer> {

    Show findByTitle(String name);

    List<Show> findByTitleContaining(String name);
}

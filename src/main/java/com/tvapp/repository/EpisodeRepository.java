package com.tvapp.repository;

import com.tvapp.model.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Integer> {

    Episode removeEpisodeByFavoriteId(int favoriteId);

    List<Episode> findAllByFavoriteId(int favoriteId);
}

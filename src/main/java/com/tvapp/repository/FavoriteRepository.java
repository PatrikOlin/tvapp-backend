package com.tvapp.repository;

import com.tvapp.model.Favorite;
import com.tvapp.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

    List<Show> getAllByUserIdEquals (int id);

    Favorite getFavoriteByUserIdLikeAndShowIdLike (int user_id, int show_id);

    int countByShowId(int showId);
}

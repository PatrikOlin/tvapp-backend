package com.tvapp.repository;

import com.tvapp.model.WatchList;
import com.tvapp.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * @author Patrik Holmkvist & Patrik Olin
 * Datum: 2018-12-03
 * Kurs: Java EE
 * Labb: Projekt
 */
public interface WatchListRepository extends JpaRepository<WatchList, Integer> {

    List<Show> getAllByUserIdEquals (int id);

    WatchList getWatchListByUserIdLikeAndShowIdLike (int user_id, int show_id);

    int countByUserIdAndShowId(int userId, int showId);

    int countByShowId(int showId);
}

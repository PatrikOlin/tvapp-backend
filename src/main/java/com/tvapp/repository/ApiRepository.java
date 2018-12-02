package com.tvapp.repository;

import com.tvapp.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiRepository extends JpaRepository<Token, Integer> {

    Token findByName(String name);


}

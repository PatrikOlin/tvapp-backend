package com.tvapp.repository;

import com.tvapp.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * @author Patrik Holmkvist & Patrik Olin
 * Datum: 2018-12-03
 * Kurs: Java EE
 * Labb: Projekt
 */
@Repository
public interface ApiRepository extends JpaRepository<Token, Integer> {

    Token findByName(String name);


}

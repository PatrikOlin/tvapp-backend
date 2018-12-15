package com.tvapp.repository;

import com.tvapp.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * @author Patrik Holmkvist & Patrik Olin
 * Datum: 2018-12-03
 * Kurs: Java EE
 * Labb: Projekt
 */
@Repository
public interface UserRepository extends JpaRepository<UserDetails, Integer> {

    UserDetails findByEmail(String email);

}

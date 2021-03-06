package com.tvapp.utils.services;

import com.tvapp.model.UserDetails;
import com.tvapp.utils.exceptions.user.InvalidUserException;
import com.tvapp.utils.exceptions.user.UserNotFoundException;
import com.tvapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
/**
 * @author Patrik Holmkvist & Patrik Olin
 * Datum: 2018-12-03
 * Kurs: Java EE
 * Labb: Projekt
 */
@Component
public class DbUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByEmail(email);

        if(user == null) {
            throw new InvalidUserException();
        }

        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user"));

        return new User(user.getEmail(), user.getPassword(), authorities);
    }


}

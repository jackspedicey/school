package com.zakafikry.school.service;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import com.zakafikry.school.entity.Users;
import com.zakafikry.school.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> optUser = userRepository.findByUsername(username);
        if (optUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        User userDetails =  new User(
                optUser.get().getUsername(),
                optUser.get().getPassword(),
                Arrays.stream(optUser.get().getRole().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
        );
        System.out.println("loadUserByUsername: " + userDetails.toString());
        return userDetails;
    }

}
package com.ms.user_service.services;

import com.ms.user_service.entities.UserEntity;
import com.ms.user_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity getUserFromUsername(String username){
        return userRepository.findById(Long.valueOf(username))
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user=getUserFromUsername(username);
        return User.builder()
                .username(user.getId().toString())
                .password(user.getPassword())
//                .authorities(new SimpleGrantedAuthority(user.getRole()))
                .build();
    }
}
